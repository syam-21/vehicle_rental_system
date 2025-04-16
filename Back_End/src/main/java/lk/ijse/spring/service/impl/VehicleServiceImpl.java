package lk.ijse.spring.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lk.ijse.spring.dto.DriverDTO;
import lk.ijse.spring.dto.VehicleDTO;
import lk.ijse.spring.entity.Booking;
import lk.ijse.spring.entity.Driver;
import lk.ijse.spring.entity.Vehicle;
import lk.ijse.spring.repo.BookingRepo;
import lk.ijse.spring.repo.DriverRepo;
import lk.ijse.spring.repo.VehicleRepo;
import lk.ijse.spring.service.VehicleService;
import lk.ijse.spring.util.BookingStatus;

@Service
public class VehicleServiceImpl implements VehicleService {

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<VehicleDTO> getAllVehicles() {
        return vehicleRepo.findAll().stream()
                .filter(vehicle -> vehicle.isApproved() &&
                        bookingRepo.findByVehicleAndStatus(vehicle, BookingStatus.PENDING).isEmpty())
                .map(vehicle -> {
                    VehicleDTO dto = modelMapper.map(vehicle, VehicleDTO.class);
                    dto.setImagePath(vehicle.getImagePath());
                    return dto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<VehicleDTO> getVehiclesByDriver(Long driverId) {
        return vehicleRepo.findByDriverId(driverId).stream()
        .map(vehicle -> {
            VehicleDTO dto = modelMapper.map(vehicle, VehicleDTO.class);
        
            // ✅ This line is not working correctly. Set it manually for now:
            dto.setImagePath("vehicle-" + vehicle.getId() + ".jpg");
        
            // Also send driver info
            if (vehicle.getDriver() != null) {
                dto.setDriver(modelMapper.map(vehicle.getDriver(), DriverDTO.class));
            }
        
            return dto;
                }).collect(Collectors.toList());
    }
    @Override
    public void updateVehicleImagePath(Long vehicleId, String imagePath) {
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
            .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setImagePath(imagePath);
        vehicleRepo.save(vehicle);
    }
    
    @Override
    public VehicleDTO addVehicle(VehicleDTO dto) {
        Vehicle vehicle = modelMapper.map(dto, Vehicle.class);
        Driver driver = driverRepo.findById(dto.getDriverId())
                .orElseThrow(() -> new RuntimeException("Driver not found"));
        vehicle.setDriver(driver);
        vehicle.setApproved(false);
        return modelMapper.map(vehicleRepo.save(vehicle), VehicleDTO.class);
    }

    @Override
    public List<VehicleDTO> getPendingVehicles() {
        return vehicleRepo.findByApprovedFalse().stream()
                .map(vehicle -> modelMapper.map(vehicle, VehicleDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public void approveVehicle(Long vehicleId) {
        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehicle not found"));
        vehicle.setApproved(true);
        vehicleRepo.save(vehicle);
    }

    @Override
    public void rejectVehicle(Long vehicleId) {
        List<Booking> bookings = bookingRepo.findAll();
        for (Booking b : bookings) {
            if (b.getVehicle() != null && b.getVehicle().getId().equals(vehicleId)) {
                bookingRepo.delete(b);
            }
        }
        vehicleRepo.deleteById(vehicleId);
    }
}
