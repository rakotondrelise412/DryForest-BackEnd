package org.ong.dryforest.dto.reforestation;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReforestationCountingDTO {
    private Integer id_type_zone;
    private String type_zone_name;
    private Integer total_quantity;

}
