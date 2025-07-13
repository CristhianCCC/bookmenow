package com.businessdomain.catalog.dto;

import com.businessdomain.user.model.User;
import com.businessdomain.user.model.enums.UserRole;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.xml.catalog.Catalog;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class CatalogDTO {


    private Long id;

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Description is required")
    private String description;

    @NotBlank (message = "Price for services is required")
    private Double price;

    @NotBlank (message = "Duration is required")
    private Double durationMinutes;

    @NotBlank (message = "Category is required")
    private String category;

    @NotBlank (message = "Availability is required")
    private Date availableDays;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    /**
     * calling user-provider attributes to be used on the service
     */

    private String providerName;

    private UserRole role;

    private String providerEmail;

    private String providerAdress;

    public CatalogDTO () {}

    public CatalogDTO(Date availableDays, String category, LocalDateTime createdAt, String description,
                      Double durationMinutes, Long id, String name, Double price, String providerName,
                      LocalDateTime updatedAt, UserRole role, String providerEmail, String providerAdress) {
        this.availableDays = availableDays;
        this.category = category;
        this.createdAt = createdAt;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.id = id;
        this.name = name;
        this.price = price;
        this.providerName = providerName;
        this.updatedAt = updatedAt;
        this.role = role;
        this.providerEmail = providerEmail;
        this.providerAdress = providerAdress;
    }

    public Date getAvailableDays() {
        return availableDays;
    }

    public void setAvailableDays(Date availableDays) {
        this.availableDays = availableDays;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(Double durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getProviderName() {
        return providerName;
    }

    public void setProviderName(String providerName) {
        this.providerName = providerName;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public String getProviderEmail() {
        return providerEmail;
    }

    public void setProviderEmail(String providerEmail) {
        this.providerEmail = providerEmail;
    }

    public String getProviderAdress() {
        return providerAdress;
    }

    public void setProviderAdress(String providerAdress) {
        this.providerAdress = providerAdress;
    }

}
