package com.API.API.model;

import jakarta.persistence.*;

@Entity
@Table(name = "project_type")
public class ProjectType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer projectTypeId;

    @Column(nullable = false)
    private String typeName;

    // Getters and Setters
    public Integer getProjectTypeId() {
        return projectTypeId;
    }

    public void setProjectTypeId(Integer projectTypeId) {
        this.projectTypeId = projectTypeId;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }
}
