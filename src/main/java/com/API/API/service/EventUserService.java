package com.API.API.service;

import com.API.API.model.Customer;
import com.API.API.model.Event;
import com.API.API.model.EventUser;
import com.API.API.model.User;
import com.API.API.repository.CustomerRepository;
import com.API.API.repository.EventRepository;
import com.API.API.repository.EventUserRepository;
import com.API.API.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class EventUserService {

    @Autowired
    private EventUserRepository eventUserRepository;

    @Autowired
    private EventRepository eventRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomerRepository customerRepository;

    public EventUser assignUserAndCustomerToEvent(Integer eventId, Integer userId, Integer customerId) {
        // Lấy thông tin sự kiện
        Event event = eventRepository.findById(eventId)
                .orElseThrow(() -> new RuntimeException("Event not found with ID: " + eventId));

        // Lấy thông tin người dùng
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        // Lấy thông tin khách hàng (nếu có)
        Customer customer = customerId != null
                ? customerRepository.findById(customerId)
                .orElseThrow(() -> new RuntimeException("Customer not found with ID: " + customerId))
                : null;

        // Tạo mới EventUser
        EventUser eventUser = new EventUser();
        eventUser.setEvent(event);
        eventUser.setUser(user);
        eventUser.setCustomer(customer);

        return eventUserRepository.save(eventUser);
    }

    public List<EventUser> getEventUsersByEventId(Integer eventId) {
        return eventUserRepository.findByEventId(eventId);
    }

    public List<Customer> getAvailableCustomersForEvent(Integer eventId) {
        // Lấy danh sách customerId đã tham gia sự kiện
        List<Integer> participatedCustomerIds = eventUserRepository.findCustomerIdsByEventId(eventId);

        // Nếu không có khách hàng nào đã tham gia, trả về toàn bộ khách hàng
        if (participatedCustomerIds == null || participatedCustomerIds.isEmpty()) {
            return customerRepository.findAll();
        }

        // Trả về danh sách khách hàng chưa tham gia sự kiện
        return customerRepository.findByCustomerIdNotIn(participatedCustomerIds);
    }
}
