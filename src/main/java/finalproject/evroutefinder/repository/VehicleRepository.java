package finalproject.evroutefinder.repository;


import finalproject.evroutefinder.model.AppUser;
import finalproject.evroutefinder.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, Long> {

    List<Vehicle> findByAppUser_UserId(Long userId);
    List<Vehicle> findByIsDefault (Boolean isDefault);
}
