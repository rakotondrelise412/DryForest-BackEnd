package org.ong.dryforest.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "person")
public class Person extends SyncEntity {
    @Id
    @Column(name = "id_person")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "last_name")
    private String last_name;

    @Column(name = "first_name")
    private String first_name;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_gender", referencedColumnName = "id_gender")
    private Gender gender;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phone_number;

    @Column(name = "address")
    private String address;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_role", referencedColumnName = "id_role")
    private Role role;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_site", referencedColumnName = "id_site", nullable = true)
    private Site site;
}
