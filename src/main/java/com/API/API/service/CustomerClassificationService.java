package com.API.API.service;

import com.API.API.model.CustomerClassification;
import com.API.API.repository.CustomerClassificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerClassificationService {

    @Autowired
    private CustomerClassificationRepository classificationRepository;

    // Lấy tất cả phân loại khách hàng
    public List<CustomerClassification> getAllClassifications() {
        return classificationRepository.findAll();
    }

    // Lấy phân loại khách hàng theo ID
    public Optional<CustomerClassification> getClassificationById(Integer id) {
        return classificationRepository.findById(id);
    }

    // Thêm mới phân loại khách hàng
    public CustomerClassification createClassification(CustomerClassification classification) {
        return classificationRepository.save(classification);
    }

    // Cập nhật phân loại khách hàng
    public CustomerClassification updateClassification(Integer id, CustomerClassification updatedClassification) {
        return classificationRepository.findById(id)
                .map(classification -> {
                    classification.setClassificationName(updatedClassification.getClassificationName());
                    return classificationRepository.save(classification);
                })
                .orElseThrow(() -> new RuntimeException("Classification not found"));
    }

    // Xóa phân loại khách hàng
    public void deleteClassification(Integer id) {
        classificationRepository.deleteById(id);
    }
}
