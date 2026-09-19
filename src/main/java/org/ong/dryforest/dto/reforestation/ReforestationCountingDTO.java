package org.ong.dryforest.dto.reforestation;

import lombok.Data;

@Data
public class ReforestationCountingDTO {
    private Integer id_type_zone;
    private String type_zone_name;
    private Integer total_quantity;

    public ReforestationCountingDTO(){};
    public ReforestationCountingDTO(Integer id_type_zone, String type_zone_name, Integer total_quantity){
        this.id_type_zone = id_type_zone;
        this.type_zone_name = type_zone_name;
        this.total_quantity = total_quantity;
    }
}
