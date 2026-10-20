package org.ong.dryforest.service.zone;

import java.time.LocalDateTime;
import java.util.List;

import org.ong.dryforest.entity.TypeZone;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface TypeZoneService {

    TypeZone findById(int id_type_zone);

    List<TypeZone> findAll();

    Page<TypeZone> findAll(Pageable pageable);

    TypeZone createTypeZone(TypeZone typeZone);

    TypeZone updateTypeZone(TypeZone typeZone);

    void deleteTypeZone(TypeZone typeZone);

    List<TypeZone> findAllTypesUpdatedSince(LocalDateTime last_sync);
}