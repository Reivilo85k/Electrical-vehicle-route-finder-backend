package finalproject.evroutefinder.service;

import finalproject.evroutefinder.dto.VehicleDto;
import finalproject.evroutefinder.exceptions.VehicleNotFoundException;
import finalproject.evroutefinder.model.Vehicle;
import finalproject.evroutefinder.repository.VehicleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@AllArgsConstructor
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    private final AuthService authService;

    public List<VehicleDto> getUserVehicles(Long userId) {
        return vehicleRepository.findByAppUser_UserId(userId)
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    public List<VehicleDto> getDefaultVehicles(Boolean isDefault) {
        return vehicleRepository.findByIsDefault(isDefault)
                .stream()
                .map(this::mapToDto)
                .collect(toList());
    }

    @Transactional
    public VehicleDto save(VehicleDto vehicleDto){
        Vehicle vehicle = vehicleRepository.save(mapToVehicle(vehicleDto));
        vehicleDto.setId(vehicle.getId());
        return vehicleDto;
    }

    @Transactional(readOnly = true)
    public VehicleDto getVehicle(Long id) {
        Vehicle vehicle = vehicleRepository.findById(id)
                .orElseThrow(()->new VehicleNotFoundException("Vehicle not found with id " + id));
        return mapToDto(vehicle);
    }

    private VehicleDto mapToDto(Vehicle vehicle) {
        return VehicleDto.builder().id(vehicle.getId())
                .brand(vehicle.getBrand())
                .model(vehicle.getModel())
                .capacity(vehicle.getCapacity())
                .range(vehicle.getRange())
                .consumption(vehicle.getConsumption())
                .userId(vehicle.getAppUser().getUserId())
                .isDefault(vehicle.getIsDefault())
                .build();
    }

    private Vehicle mapToVehicle(VehicleDto vehicleDto) {
       return Vehicle.builder().brand(vehicleDto.getBrand())
               .model(vehicleDto.getModel())
               .capacity(vehicleDto.getCapacity())
               .range(vehicleDto.getRange())
               .consumption(vehicleDto.getConsumption())
               .appUser(authService.getCurrentAppUser())
               .isDefault(vehicleDto.getIsDefault())
               .build();

    }
}
