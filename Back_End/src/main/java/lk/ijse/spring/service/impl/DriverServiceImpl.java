package lk.ijse.spring.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lk.ijse.spring.dto.DriverDTO;
import lk.ijse.spring.entity.Driver;
import lk.ijse.spring.repo.DriverRepo;
import lk.ijse.spring.service.DriverService;

@Service
public class DriverServiceImpl implements DriverService {

    @Autowired
    private DriverRepo driverRepo;

    @Override
    public DriverDTO registerDriver(DriverDTO dto) {
        Driver driver = new Driver();
        driver.setName(dto.getName());
        driver.setPhone(dto.getPhone());
        driver.setEmail(dto.getEmail());
        driver.setPassword(dto.getPassword());
        driver.setLicenseNumber(dto.getLicenseNumber());
        driver.setNidNumber(dto.getNidNumber());
        driver.setAddress(dto.getAddress());
        driver.setGender(dto.getGender()); // ✅ set gender

        // ✅ set image file names
        driver.setPhotoPath("driver-" + dto.getId() + ".jpg");
        driver.setLicenseImagePath("license-" + dto.getId() + ".jpg");
        driver.setNidImagePath("nid-" + dto.getId() + ".jpg");

        Driver saved = driverRepo.save(driver);
        dto.setId(saved.getId());
        return dto;
    }

    @Override
    public DriverDTO getDriverById(Long id) {
        return driverRepo.findById(id).map(driver -> {
            DriverDTO dto = new DriverDTO();
            dto.setId(driver.getId());
            dto.setName(driver.getName());
            dto.setPhone(driver.getPhone());
            dto.setEmail(driver.getEmail());
            dto.setPassword(driver.getPassword());
            dto.setLicenseNumber(driver.getLicenseNumber());
            dto.setNidNumber(driver.getNidNumber());
            dto.setAddress(driver.getAddress());
            dto.setGender(driver.getGender()); // ✅ gender

            dto.setProfilePhotoUrl(driver.getPhotoPath());
            dto.setLicensePhotoUrl(driver.getLicenseImagePath());
            dto.setNidPhotoUrl(driver.getNidImagePath());

            dto.setAvailable(driver.isAvailable());
            dto.setTripCount(driver.getCompletedTrips());
            dto.setRating(driver.getRating());

            return dto;
        }).orElse(null);
    }

    @Override
    public List<DriverDTO> getAllDrivers() {
        return driverRepo.findAll().stream().map(driver -> {
            DriverDTO dto = new DriverDTO();
            dto.setId(driver.getId());
            dto.setName(driver.getName());
            dto.setPhone(driver.getPhone());
            dto.setEmail(driver.getEmail());
            dto.setPassword(driver.getPassword());
            dto.setLicenseNumber(driver.getLicenseNumber());
            dto.setNidNumber(driver.getNidNumber());
            dto.setAddress(driver.getAddress());
            dto.setGender(driver.getGender()); // ✅ gender

            dto.setProfilePhotoUrl(driver.getPhotoPath());
            dto.setLicensePhotoUrl(driver.getLicenseImagePath());
            dto.setNidPhotoUrl(driver.getNidImagePath());

            dto.setAvailable(driver.isAvailable());
            dto.setTripCount(driver.getCompletedTrips());
            dto.setRating(driver.getRating());

            return dto;
        }).collect(Collectors.toList());
    }
}
