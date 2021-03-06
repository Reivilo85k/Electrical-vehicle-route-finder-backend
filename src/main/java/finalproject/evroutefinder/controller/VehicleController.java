package finalproject.evroutefinder.controller;

import finalproject.evroutefinder.dto.VehicleDto;
import finalproject.evroutefinder.service.VehicleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
@AllArgsConstructor
public class VehicleController {

    private final VehicleService vehicleService;

    @GetMapping
    public List<VehicleDto> getDefaultVehicles(){
        return vehicleService.getDefaultVehicles(true);
    }

    @GetMapping("/{id}")
    public VehicleDto getVehicle(@PathVariable Long id) {
        return vehicleService.getVehicle(id);
    }

    @GetMapping("/user/{userId}")
    public List<VehicleDto> getUserVehicles(@PathVariable Long userId) {
        return vehicleService.getUserVehicles(userId);
    }


    @PostMapping
    public VehicleDto createVehicle(@RequestBody @Valid VehicleDto vehicleDto){
      return vehicleService.save(vehicleDto);
    }


}
