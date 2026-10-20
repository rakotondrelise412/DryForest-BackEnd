
package org.ong.dryforest.service.siteService;

import org.ong.dryforest.dto.site.SiteMobileDTO;
import org.ong.dryforest.dto.site.SiteWebDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SiteService {

    Page<SiteWebDTO> findAllSites(Pageable pageable);

    SiteWebDTO findSiteById(int id);

    Page<SiteMobileDTO> findAllSitesMobile(Pageable pageable);

    SiteMobileDTO findSiteByIdMobile(int id);

    SiteWebDTO createSite(SiteWebDTO dto);

    SiteWebDTO updateSite(int id, SiteWebDTO dto);

    void deleteSite(int id);
}
