package com.businessdomain.catalog.model;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import java.time.LocalDateTime;
import com.businessdomain.user.model.User;
import java.util.Date;


@Entity
@Table(name = "catalog")
public class Catalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String providerEmail;

    private String name;

    private String description;

    private Double price;

    private Double durationMinutes;

    private String category;

    private Date availableDays;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    public Catalog() {}

    public Catalog(Date availableDays, String category, LocalDateTime createdAt, String description, Double durationMinutes, Long id, String name, Double price, String providerEmail, LocalDateTime updatedAt) {
        this.availableDays = availableDays;
        this.category = category;
        this.createdAt = createdAt;
        this.description = description;
        this.durationMinutes = durationMinutes;
        this.id = id;
        this.name = name;
        this.price = price;
        this.providerEmail = providerEmail;
        this.updatedAt = updatedAt;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
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

    public String getProviderEmail() {
        return providerEmail;
    }

    public void setProviderEmail(String providerEmail) {
        this.providerEmail = providerEmail;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
}
