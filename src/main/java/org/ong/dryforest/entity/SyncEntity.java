package org.ong.dryforest.entity;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

import jakarta.persistence.Column;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.PrePersist;
import jakarta.persistence.MappedSuperclass;

@Getter
@Setter
@MappedSuperclass
public abstract class SyncEntity {

    @Column(name = "created_at", nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    protected LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    protected boolean isDeleted = false;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
            updatedAt = LocalDateTime.now();
        }
    }

    @PreUpdate
    protected void onUpdate() {
        if (updatedAt == null) updatedAt = LocalDateTime.now();
    }
}
