
package org.ong.dryforest.mapper;

import org.ong.dryforest.dto.site.SiteMobileDTO;
import org.ong.dryforest.dto.site.SiteWebDTO;
import org.ong.dryforest.entity.Site;

import java.util.List;

public class SiteMapper {

    private SiteMapper() {
    }

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

        if (site.getLocation() != null) {
            siteDTO.setLatitude(site.getLocation().getY());
            siteDTO.setLongitude(site.getLocation().getX());
        }

        return siteDTO;
    }

    public static List<SiteMobileDTO> toDTOList(List<Site> sites) {
        return sites.stream()
                .map(SiteMapper::toMobileDTO)
                .toList();
    }
}
