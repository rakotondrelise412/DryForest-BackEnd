package org.ong.dryforest.service.biomass;

import org.springframework.stereotype.Service;

@Service
public class PlantBiomassServiceImpl implements PlantBiomassService {

    private static final double CARBON_RATIO = 0.47;
    private static final double AGB_COEFFICIENT = 0.0673;
    private static final double AGB_EXPONENT = 0.976;

    @Override
    public double calculateDryAGB(
            double diameterCm,
            double heightM,
            double woodDensity
    ) {

        if (diameterCm <= 0 ||
                heightM <= 0 ||
                woodDensity <= 0) {

            throw new IllegalArgumentException(
                    "Le diamètre, la hauteur et la densité doivent être positifs."
            );
        }

        return AGB_COEFFICIENT *
                Math.pow(
                        woodDensity
                                * diameterCm
                                * diameterCm
                                * heightM,
                        AGB_EXPONENT
                );
    }

    @Override
    public double calculateCarbon(double dryAGB) {

        if (dryAGB < 0) {
            throw new IllegalArgumentException(
                    "La biomasse ne peut pas être négative."
            );
        }

        return CARBON_RATIO * dryAGB;
    }

    @Override
    public double calculateCarbonForPlantation(
            double diameter,
            double height,
            double density
    ) {

        double biomass =
                calculateDryAGB(
                        diameter,
                        height,
                        density
                );

        return calculateCarbon(biomass);
    }
}