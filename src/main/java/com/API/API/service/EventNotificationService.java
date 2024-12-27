package com.API.API.service;

import com.API.API.dto.UpdateNotificationRequest;
import com.API.API.model.Customer;
import com.API.API.model.Event;
import com.API.API.model.EventNotification;
import com.API.API.repository.EventNotificationRepository;
import com.API.API.repository.EventRepository;
import com.API.API.repository.EventUserRepository;
import jakarta.mail.MessagingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class EventNotificationService {

    @Autowired
    private EventNotificationRepository notificationRepository;

    @Autowired
    private EventUserRepository eventUserRepository;

    @Autowired
    private EmailService emailService;

    @Autowired
    private EventRepository eventRepository;

    // Lấy tất cả thông báo, tự động tạo thông báo cho các EventUserID chưa có
    public List<EventNotification> getAllNotificationsWithEventUserIds() {
        List<Integer> allEventUserIds = eventUserRepository.findAllEventUserIds();
        List<Integer> existingNotificationEventUserIds = notificationRepository.findAllEventUserIdsWithNotifications();
        List<Integer> missingEventUserIds = allEventUserIds.stream()
                .filter(id -> !existingNotificationEventUserIds.contains(id))
                .toList();

        for (Integer eventUserId : missingEventUserIds) {
            EventNotification newNotification = new EventNotification();
            newNotification.setEventUser(eventUserRepository.findById(eventUserId).orElseThrow());
            newNotification.setMethod(EventNotification.Method.Email);
            newNotification.setStatus(EventNotification.Status.Pending);
            newNotification.setMessage(null);
            newNotification.setSentAt(null);
            notificationRepository.save(newNotification);
        }

        return notificationRepository.findAll();
    }

    // Gửi thông báo hỗ trợ nhiều tệp đính kèm
    public EventNotification sendNotification(Integer notificationId, UpdateNotificationRequest request, List<String> attachmentPaths) throws MessagingException {
        EventNotification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification không tồn tại"));

        // Kiểm tra khách hàng
        Customer customer = notification.getEventUser().getCustomer();
        if (customer == null || customer.getEmail() == null || customer.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Khách hàng hoặc email không tồn tại.");
        }

        // Lấy thông tin sự kiện
        Event event = notification.getEventUser().getEvent();
        String eventTypeName = event.getEventType().getEventTypeName();
        String eventDescription = event.getDescription() != null ? event.getDescription() : "Không có mô tả";
        String eventDate = event.getEventDate() != null ? event.getEventDate().toString() : "Không xác định";

        // Lấy email khách hàng
        String toEmail = customer.getEmail();

        // Cập nhật nội dung thông báo
        notification.setMessage(request.getMessage());
        notification.setSentAt(request.getSentAt());
        notification.setStatus(EventNotification.Status.Success);

        // Tạo nội dung email
        String subject = "Thông báo sự kiện: " + eventTypeName;
        String body = String.format("""
       <!DOCTYPE html>
                           <html lang="vi">
                           <head>
                               <meta charset="UTF-8">
                               <meta name="viewport" content="width=device-width, initial-scale=1.0">
                               <title>Thông Báo Sự Kiện</title>
                               <style>
                                   body {
                                       font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
                                       background-color: #f4f4f4;
                                       margin: 0;
                                       padding: 0;
                                       color: #333;
                                       white-space: normal; /* Đảm bảo văn bản không bị cắt */
                                   }
                                   .email-container {
                                       background-color: #ffffff;
                                       border-radius: 8px;
                                       box-shadow: 0 4px 12px rgba(0, 0, 0, 0.1);
                                       margin-top: 20px;
                                       max-width: 600px; /* Giới hạn chiều rộng container */
                                       margin-left: auto;
                                       margin-right: auto;
                                   }
                                   .header {
                                       background-color: #007BFF;
                                       color: white;
                                       padding: 30px;
                                       text-align: center;
                                       border-top-left-radius: 8px;
                                       border-top-right-radius: 8px;
                                   }
                                   .content {
                                       padding: 20px;
                                   }
                                   .footer {
                                       background-color: #f1f1f1;
                                       text-align: center;
                                       padding: 15px;
                                       font-size: 14px;
                                       color: #777;
                                   }
                               </style>
                           </head>
                           <body>
                               <div class="container email-container">
                                   <div class="header">
                                       <h1>Thông Báo Sự Kiện</h1>
                                   </div>
                                   <div class="content">
                                       <h2>Kính gửi Ông/Bà %s,</h2>
                                       <p>Chúng tôi xin thông báo về sự kiện sắp diễn ra:</p>
                                       <ul>
                                           <li><strong>Tên sự kiện:</strong> %s</li>
                                           <li><strong>Mô tả:</strong> %s</li>
                                           <li><strong>Ngày diễn ra:</strong> %s</li>
                                           <li><strong>Địa điểm:</strong> PAX SKY, 123 Nguyễn Đình Chiểu, Phường 6, Quận 3, Hồ Chí Minh</li>
                                       </ul>
                                       <p>Thông báo từ hệ thống: <strong>%s</strong></p>
                                   </div>
                                   <div class="footer">
                                       <img src="https://saca.com.vn/vnt_upload/partner/47_ztt.png" alt="Logo Hòa Bình ">
                                       <p>Cảm ơn bạn đã quan tâm! <br> Công ty Hòa Bình - Điện thoại: 123-456-7890</p>
                                   </div>
                               </div>
                           </body>
                           </html>
    """, customer.getName(), eventTypeName, eventDescription, eventDate, request.getMessage());

        // Gửi email với các tệp đính kèm
        emailService.sendEmail(toEmail, subject, body, attachmentPaths);

        // Tự động cập nhật ngày nhắc nhở nếu cần
        if (event.getReminderDate() == null) {
            event.setReminderDate(LocalDate.now().plusDays(7)); // Đặt nhắc nhở 7 ngày sau
            eventRepository.save(event); // Lưu sự kiện đã cập nhật
        }

        // Lưu thông báo đã cập nhật
        return notificationRepository.save(notification);
    }

    // Lấy thông báo theo ID
    public EventNotification getNotificationById(Integer notificationId) {
        return notificationRepository.findById(notificationId)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found with ID: " + notificationId));
    }

    // Xóa thông báo
    public void deleteNotification(Integer notificationId) {
        notificationRepository.deleteById(notificationId);
    }
}
