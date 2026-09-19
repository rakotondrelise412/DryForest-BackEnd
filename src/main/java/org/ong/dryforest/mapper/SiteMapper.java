package org.ong.dryforest.mapper;

import org.ong.dryforest.entity.Site;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.ong.dryforest.dto.site.SiteMobileDTO;
import org.ong.dryforest.dto.site.SiteWebDTO;

public class SiteMapper {
    
    public static SiteMobileDTO toMobileDTO(Site site) {
        SiteMobileDTO siteDTO = new SiteMobileDTO();

        siteDTO.setId(site.getId());
        siteDTO.setName(site.getName());

        return siteDTO;
    }

    public static SiteWebDTO toWebDTO(Site site) {
        SiteWebDTO siteDTO = new SiteWebDTO();

        siteDTO.setId(site.getId());
        siteDTO.setName(site.getName());
        siteDTO.setLatitude(site.getLocation().getY());
        siteDTO.setLongitude(site.getLocation().getX());

        return siteDTO;
    }

    public static List<SiteMobileDTO> toDTOList(List<Site> sites) {
        List<SiteMobileDTO> sitesDTO = new ArrayList<>();

        sitesDTO = sites.stream()
                   .map(SiteMapper::toMobileDTO)
                   .collect(Collectors.toList());

        return sitesDTO;
    }

}