package lk.ijse.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.ijse.spring.entity.Vehicle;

public interface VehicleRepo extends JpaRepository<Vehicle, Long> {
    List<Vehicle> findByDriverId(Long driverId);          // ✅ needed for driver vehicle list
    List<Vehicle> findByApprovedFalse();                  // ✅ for admin pending approval list
    List<Vehicle> findByApprovedTrue();                   // ✅ for user-visible vehicles
}
