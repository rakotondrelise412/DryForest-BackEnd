package org.ong.dryforest.repository;

import org.ong.dryforest.entity.Gender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GenderRepository extends JpaRepository
        <Gender, Integer>{
}
