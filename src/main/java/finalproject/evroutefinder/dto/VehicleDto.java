package finalproject.evroutefinder.dto;

import finalproject.evroutefinder.model.AppUser;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehicleDto {

    private Long id;
    private String brand;
    private String model;
    private int capacity; // battery, kWh
    private int range; // km per loading
    private float consumption; // km per 1 kWh (range/capacity)
}
