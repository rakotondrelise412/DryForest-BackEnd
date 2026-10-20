package org.ong.dryforest.controller;

import lombok.RequiredArgsConstructor;
import org.ong.dryforest.dto.site.SiteMobileDTO;
import org.ong.dryforest.dto.site.SiteWebDTO;
import org.ong.dryforest.service.siteService.SiteService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sites")
@RequiredArgsConstructor
public class SiteController {

    private final SiteService siteService;

    @GetMapping
    public ResponseEntity<Page<SiteWebDTO>> getAllSites(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size
        );

        return ResponseEntity.ok(
                siteService.findAllSites(pageable)
        );
    }

    @GetMapping("/mobile")
    public ResponseEntity<Page<SiteMobileDTO>> getAllSitesMobile(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        return ResponseEntity.ok(
                siteService.findAllSitesMobile(pageable)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<SiteWebDTO> getSiteById(
            @PathVariable int id) {
        return ResponseEntity.ok(siteService.findSiteById(id));
    }

    @GetMapping("/mobile/{id}")
    public ResponseEntity<SiteMobileDTO> getSiteByIdMobile(
            @PathVariable int id) {
        return ResponseEntity.ok(
                siteService.findSiteByIdMobile(id)
        );
    }

    @PostMapping
    public ResponseEntity<SiteWebDTO> createSite(
            @RequestBody SiteWebDTO dto) {
        SiteWebDTO createdSite = siteService.createSite(dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(createdSite);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SiteWebDTO> updateSite(
            @PathVariable int id,
            @RequestBody SiteWebDTO dto) {
        return ResponseEntity.ok(
                siteService.updateSite(id, dto)
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(
            @PathVariable int id) {
        siteService.deleteSite(id);
        return ResponseEntity.noContent().build();
    }
}

