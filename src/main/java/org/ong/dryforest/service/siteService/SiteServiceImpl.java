
package org.ong.dryforest.service.siteService;

import lombok.RequiredArgsConstructor;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;
import org.ong.dryforest.dto.site.SiteMobileDTO;
import org.ong.dryforest.dto.site.SiteWebDTO;
import org.ong.dryforest.entity.Site;
import org.ong.dryforest.mapper.SiteMapper;
import org.ong.dryforest.repository.SiteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SiteServiceImpl implements SiteService {

    private final SiteRepository siteRepository;

    private final GeometryFactory geometryFactory =
            new GeometryFactory(new PrecisionModel(), 4326);

    @Override
    @Transactional(readOnly = true)
    public Page<SiteWebDTO> findAllSites(Pageable pageable) {
        return siteRepository.findAll(pageable)
                .map(SiteMapper::toWebDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public SiteWebDTO findSiteById(int id) {
        return SiteMapper.toWebDTO(getSiteOrThrow(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<SiteMobileDTO> findAllSitesMobile(Pageable pageable) {
        return siteRepository.findAll(pageable)
                .map(SiteMapper::toMobileDTO);
    }

    @Override
    @Transactional(readOnly = true)
    public SiteMobileDTO findSiteByIdMobile(int id) {
        return SiteMapper.toMobileDTO(getSiteOrThrow(id));
    }

    @Override
    public SiteWebDTO createSite(SiteWebDTO dto) {
        Site site = new Site();

        site.setName(dto.getName());
        site.setLocation(
                createPoint(dto.getLongitude(), dto.getLatitude())
        );

        return SiteMapper.toWebDTO(siteRepository.save(site));
    }

    @Override
    public SiteWebDTO updateSite(int id, SiteWebDTO dto) {
        Site site = getSiteOrThrow(id);

        site.setName(dto.getName());
        site.setLocation(
                createPoint(dto.getLongitude(), dto.getLatitude())
        );

        return SiteMapper.toWebDTO(siteRepository.save(site));
    }

    @Override
    public void deleteSite(int id) {
        Site site = getSiteOrThrow(id);

        if (site.getMembers() != null
                && !site.getMembers().isEmpty()) {
            throw new IllegalStateException(
                    "Impossible de supprimer ce site : "
                            + "des personnes y sont associées."
            );
        }

        siteRepository.delete(site);
    }

    private Site getSiteOrThrow(int id) {
        return siteRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Site '" + id + "' introuvable"
                        )
                );
    }

    private Point createPoint(double longitude, double latitude) {
        Point point = geometryFactory.createPoint(
                new Coordinate(longitude, latitude)
        );

        point.setSRID(4326);
        return point;
    }
}
