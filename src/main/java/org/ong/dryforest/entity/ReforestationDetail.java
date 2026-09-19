package org.ong.dryforest.entity;

import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Table(name = "reforestation_detail")
public class ReforestationDetail extends SyncEntity{
    @Id
    @Column(name="id_reforestation_detail")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "uuid", columnDefinition = "uuid")
    private UUID uuid;

    @Column(name = "quantity")
    private double quantity;

    @Column(name = "is_synced")
    private boolean is_synced;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_reforestation", referencedColumnName = "id_reforestation")
    private Reforestation reforestation;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_species", referencedColumnName = "id_species")
    private Species species;
}
