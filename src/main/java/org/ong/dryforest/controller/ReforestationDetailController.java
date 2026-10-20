
package org.ong.dryforest.controller;

import java.util.UUID;

import org.ong.dryforest.dto.reforestation.ReforestationDetailDTO;
import org.ong.dryforest.entity.ReforestationDetail;
import org.ong.dryforest.mapper.ReforestationDetailMapper;

import org.ong.dryforest.service.reforestationDetail.ReforestationDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reforestation-detail")
public class ReforestationDetailController {

    @Autowired
    private ReforestationDetailService detailService;

    @Autowired
    private ReforestationDetailMapper detailMapper;

    @GetMapping
    public ResponseEntity<Page<ReforestationDetailDTO>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        Page<ReforestationDetailDTO> result =
                detailService.findAll(pageable)
                        .map(detailMapper::toDTO);

        return ResponseEntity.ok(result);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ReforestationDetailDTO> getById(
            @PathVariable int id) {

        ReforestationDetail detail =
                detailService.findById(id);

        return ResponseEntity.ok(
                detailMapper.toDTO(detail));
    }


    @GetMapping("/uuid/{uuid}")
    public ResponseEntity<ReforestationDetailDTO> getByUuid(
            @PathVariable UUID uuid) {

        ReforestationDetail detail =
                detailService.findByUuid(uuid);

        return ResponseEntity.ok(
                detailMapper.toDTO(detail));
    }


    @PostMapping
    public ResponseEntity<ReforestationDetailDTO> create(
            @RequestBody ReforestationDetailDTO dto) {

        ReforestationDetail created =
                detailService.createReforestationDetail(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(detailMapper.toDTO(created));
    }


    @PutMapping("/{id}")
    public ResponseEntity<ReforestationDetailDTO> update(
            @PathVariable int id,
            @RequestBody ReforestationDetailDTO dto) {

        ReforestationDetail updated =
                detailService.updateReforestationDetail(id, dto);

        return ResponseEntity.ok(
                detailMapper.toDTO(updated));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable int id) {

        detailService.deleteReforestationDetail(id);

        return ResponseEntity.noContent().build();
    }
}
