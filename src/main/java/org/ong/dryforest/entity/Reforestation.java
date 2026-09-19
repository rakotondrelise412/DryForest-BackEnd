package org.ong.dryforest.entity;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name="reforestation")
public class Reforestation extends SyncEntity{
    @Id
    @Column(name="id_reforestation")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid", columnDefinition = "uuid")
    private UUID uuid;

    @Column(name = "date_reforestation")
    private LocalDate date_reforestation;

    @Column(name = "quantity")
    private int quantity;

    @Column(name = "is_synced")
    private boolean is_synced;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_zone", referencedColumnName = "id_zone")
    private Zone zone;

    @OneToMany(mappedBy = "reforestation", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ReforestationDetail> reforestationDetail = new ArrayList<>();
}
