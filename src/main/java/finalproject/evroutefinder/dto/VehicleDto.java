package finalproject.evroutefinder.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

    private Long id;
    private String brand;
    private String model;
    private int capacity;
    private int range;
    private float consumption;
    private Long userId;
    private Boolean isDefault;
}
