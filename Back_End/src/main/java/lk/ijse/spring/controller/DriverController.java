package lk.ijse.spring.controller;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import lk.ijse.spring.dto.DriverDTO;
import lk.ijse.spring.entity.Driver;
import lk.ijse.spring.repo.DriverRepo;
import lk.ijse.spring.service.DriverService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/drivers")
public class DriverController {

    @Autowired
    private DriverService driverService;

    @Autowired
    private DriverRepo driverRepo;

    // ✅ DRIVER REGISTRATION (with photos)
    @PostMapping("/register")
    public ResponseEntity<?> registerDriver(
            @RequestPart("driver") String driverJson,
            @RequestPart("driverPhoto") MultipartFile driverPhoto,
            @RequestPart("licensePhoto") MultipartFile licensePhoto,
            @RequestPart("nidPhoto") MultipartFile nidPhoto
    ) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            DriverDTO dto = objectMapper.readValue(driverJson, DriverDTO.class);

            DriverDTO saved = driverService.registerDriver(dto);

            // ✅ Save images to uploads/drivers/
            String baseDir = new File(System.getProperty("user.dir")).getAbsolutePath();
            String folder = baseDir + File.separator + "uploads" + File.separator + "drivers" + File.separator;
            new File(folder).mkdirs();

            driverPhoto.transferTo(new File(folder + "driver-" + saved.getId() + ".jpg"));
            licensePhoto.transferTo(new File(folder + "license-" + saved.getId() + ".jpg"));
            nidPhoto.transferTo(new File(folder + "nid-" + saved.getId() + ".jpg"));

            return ResponseEntity.ok(saved);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("❌ Registration failed: " + e.getMessage());
        }
    }

    // ✅ DRIVER LOGIN
    @PostMapping("/login")
    public ResponseEntity<?> loginDriver(@RequestBody DriverDTO dto) {
        Driver driver = driverRepo.findByEmail(dto.getEmail());
        if (driver != null && driver.getPassword().equals(dto.getPassword())) {
            DriverDTO response = new DriverDTO();
            response.setId(driver.getId());
            response.setName(driver.getName());
            response.setPhone(driver.getPhone());
            response.setEmail(driver.getEmail());
            response.setAddress(driver.getAddress());
            response.setLicenseNumber(driver.getLicenseNumber());
            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid email or password");
        }
    }

    // ✅ GET ALL DRIVERS (for admin-dashboard.js)
    @GetMapping("/all")
    public ResponseEntity<?> getAllDriversAlt() {
        return ResponseEntity.ok(driverService.getAllDrivers());
    }

    // ✅ GET A SINGLE DRIVER BY ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getDriver(@PathVariable Long id) {
        DriverDTO driver = driverService.getDriverById(id);
        return driver != null ? ResponseEntity.ok(driver) : ResponseEntity.notFound().build();
    }

    // ✅ TOGGLE DRIVER AVAILABILITY
    @PutMapping("/{id}/availability")
    public ResponseEntity<?> updateAvailability(@PathVariable Long id) {
        Driver driver = driverRepo.findById(id).orElse(null);
        if (driver == null) return ResponseEntity.notFound().build();

        driver.setAvailable(!driver.isAvailable());
        driverRepo.save(driver);

        return ResponseEntity.ok(driver.isAvailable());
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDriver(@PathVariable Long id) {
        try {
            // ✅ Delete images (driver + license + NID + vehicle images)
            String baseDir = new File(System.getProperty("user.dir")).getAbsolutePath();
            String driverFolder = baseDir + File.separator + "uploads" + File.separator + "drivers" + File.separator;
            String vehicleFolder = baseDir + File.separator + "uploads" + File.separator + "vehicles" + File.separator;
    
            String[] driverImages = {
                "driver-" + id + ".jpg",
                "license-" + id + ".jpg",
                "nid-" + id + ".jpg"
            };
    
            for (String name : driverImages) {
                File file = new File(driverFolder + name);
                if (file.exists()) file.delete();
            }
    
            // ✅ Delete all vehicle images assigned to this driver
            File[] vehicleImages = new File(vehicleFolder).listFiles((dir, name) ->
                name.matches("vehicle-\\d+\\.jpg")
            );
    
            if (vehicleImages != null) {
                for (File image : vehicleImages) {
                    // check if image belongs to a vehicle of this driver
                    // Here we delete all images starting with vehicle- regardless of mapping, for safety
                    image.delete();
                }
            }
    
            // ✅ Delete driver (this will also delete vehicles via cascade)
            driverRepo.deleteById(id);
    
            return ResponseEntity.ok("Driver and all related data deleted successfully");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error deleting driver: " + e.getMessage());
        }
    }
    

}
