package org.ong.dryforest.service.biomass;

public interface PlantBiomassService {

    double calculateDryAGB(
            double diameterCm,
            double heightM,
            double woodDensity
    );

    double calculateCarbon(double dryAGB);

    double calculateCarbonForPlantation(
            double diameter,
            double height,
            double density
    );
}