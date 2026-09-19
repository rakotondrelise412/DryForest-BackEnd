package org.ong.dryforest.entity;

import jakarta.persistence.Table;

import java.time.LocalDateTime;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "blacklistedToken")
public class BlacklistedToken {
    @Id
    @Column(name = "id_blacklist")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "token", nullable = false, unique = true)
    private String token;

    @Column(name = "blacklistedAt", nullable = false)
    private LocalDateTime blacklistedAt;
}
