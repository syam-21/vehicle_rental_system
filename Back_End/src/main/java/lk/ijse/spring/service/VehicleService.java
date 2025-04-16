package lk.ijse.spring.service;

import java.util.List;

import lk.ijse.spring.dto.VehicleDTO;

public interface VehicleService {

    // Get all approved vehicles for users
    List<VehicleDTO> getAllVehicles();

    // Get driver's own vehicles
    List<VehicleDTO> getVehiclesByDriver(Long driverId);

    // ✅ Add new vehicle from driver panel
    VehicleDTO addVehicle(VehicleDTO dto);

    // Get pending approval vehicles (admin panel)
    List<VehicleDTO> getPendingVehicles();

    // Admin approve
    void approveVehicle(Long vehicleId);

    // Admin reject/delete
    void rejectVehicle(Long vehicleId);

    void updateVehicleImagePath(Long vehicleId, String imagePath);

}
