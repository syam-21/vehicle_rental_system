package lk.ijse.spring.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import lk.ijse.spring.dto.VehicleDTO;
import lk.ijse.spring.entity.Vehicle;
import lk.ijse.spring.repo.VehicleRepo;
import lk.ijse.spring.service.VehicleService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/vehicles")
public class VehicleController {

    @Autowired
    private VehicleService vehicleService;

    @Autowired
    private VehicleRepo vehicleRepo; // ✅ Fix: Add this for DB access

    // ✅ USER PANEL - Get approved vehicles
    @GetMapping("/user/approved")
    public ResponseEntity<?> getApprovedVehicles() {
        try {
            List<VehicleDTO> list = vehicleService.getAllVehicles();
            return ResponseEntity.ok(list);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("❌ Error fetching vehicles: " + e.getMessage());
        }
    }
    @GetMapping("/image/{fileName}")
    public ResponseEntity<byte[]> getVehicleImage(@PathVariable String fileName) {
        try {
            File file = new File("uploads/vehicles/" + fileName);
            byte[] image = Files.readAllBytes(file.toPath());
    
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
    
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    // ✅ DRIVER PANEL - Get all vehicles of a driver
    @GetMapping("/driver/{driverId}")
    public List<VehicleDTO> getVehiclesByDriver(@PathVariable Long driverId) {
        return vehicleService.getVehiclesByDriver(driverId);
    }

    // ✅ DRIVER PANEL - Add vehicle (with image)
    @PostMapping("/add")
    public ResponseEntity<?> addVehicle(
            @RequestPart("vehicle") String vehicleJson,
            @RequestPart("image") MultipartFile image) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            VehicleDTO dto = mapper.readValue(vehicleJson, VehicleDTO.class);
            VehicleDTO saved = vehicleService.addVehicle(dto);

            String baseDir = new File(System.getProperty("user.dir")).getAbsolutePath();
            String folder = baseDir + File.separator + "uploads" + File.separator + "vehicles" + File.separator;
            new File(folder).mkdirs();

            String ext = StringUtils.getFilenameExtension(image.getOriginalFilename());
            String filename = "vehicle-" + saved.getId() + "." + (ext != null ? ext : "jpg");
            image.transferTo(new File(folder + filename));

            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("❌ Failed to add vehicle: " + e.getMessage());
        }
    }

    // ✅ ADMIN PANEL - Get pending vehicles
    @GetMapping("/pending")
    public List<VehicleDTO> getPendingVehicles() {
        return vehicleService.getPendingVehicles();
    }

    // ✅ ADMIN PANEL - Approve vehicle
    @PutMapping("/approve/{id}")
    public ResponseEntity<?> approveVehicle(@PathVariable Long id) {
        try {
            vehicleService.approveVehicle(id);
            return ResponseEntity.ok("✅ Vehicle approved");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Approval failed: " + e.getMessage());
        }
    }

    // ✅ ADMIN PANEL - Reject (delete) vehicle
    @DeleteMapping("/reject/{id}")
    public ResponseEntity<?> rejectVehicle(@PathVariable Long id) {
        try {
            vehicleService.rejectVehicle(id);
            return ResponseEntity.ok("❌ Vehicle rejected");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("❌ Rejection failed: " + e.getMessage());
        }
    }
    
    // ✅ DRIVER PANEL - Upload image for existing vehicle (.jpg)
    @PostMapping("/{vehicleId}/upload")
    public ResponseEntity<?> uploadVehicleImage(@PathVariable Long vehicleId,
                                                @RequestParam("file") MultipartFile file) {
        try {
            Vehicle vehicle = vehicleRepo.findById(vehicleId)
                    .orElseThrow(() -> new RuntimeException("Vehicle not found"));

            String uploadDir = "uploads/vehicles/";
            File dir = new File(uploadDir);
            if (!dir.exists()) dir.mkdirs();

            String fileName = "vehicle-" + vehicleId + ".jpg";
            Path filePath = Paths.get(uploadDir, fileName);
            Files.write(filePath, file.getBytes());

            vehicle.setImagePath(fileName);
            vehicleRepo.save(vehicle);

            return ResponseEntity.ok(Collections.singletonMap("message", "Image uploaded successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Collections.singletonMap("error", "Failed to upload image"));
        }
    }

    @GetMapping("/image/by-id/{vehicleId}")
    public ResponseEntity<byte[]> getVehicleImageById(@PathVariable Long vehicleId) {
        try {
            String filePath = "uploads/vehicles/vehicle-" + vehicleId + ".jpg";
            File file = new File(filePath);
            if (!file.exists()) {
                return ResponseEntity.notFound().build();
            }
    
            byte[] image = Files.readAllBytes(file.toPath());
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(image, headers, HttpStatus.OK);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
