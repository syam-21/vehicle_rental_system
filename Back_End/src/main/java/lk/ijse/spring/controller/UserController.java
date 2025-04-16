package lk.ijse.spring.controller;

import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lk.ijse.spring.dto.UserDTO;
import lk.ijse.spring.entity.User;
import lk.ijse.spring.repo.UserRepo;
import lk.ijse.spring.service.UserService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/users")
public class UserController {



    @Autowired
    private UserRepo userRepo;

    @Autowired
    private UserService userService;

    // ✅ GET user profile by ID
    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable Long id) {
        return userRepo.findById(id)
            .map(user -> {
                UserDTO dto = new UserDTO();
                dto.setId(user.getId());
                dto.setName(user.getName());
                dto.setEmail(user.getEmail());
                dto.setPhone(user.getPhone());
                dto.setAddress(user.getAddress());
                dto.setGender(user.getGender());
                dto.setBloodGroup(user.getBloodGroup());
                dto.setDateOfBirth(user.getDateOfBirth());
                dto.setRole(user.getRole().toString());
                return ResponseEntity.ok(dto);
            })
            .orElse(ResponseEntity.notFound().build());
    }
    

    // ✅ UPDATE user profile
    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody UserDTO updatedDTO) {
        try {
            User updatedUser = userService.updateUserProfile(id, updatedDTO);
            return ResponseEntity.ok(updatedUser);
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Failed to update profile: " + e.getMessage());
        }
    }
   @PostMapping("/{id}/upload")
public ResponseEntity<String> uploadProfilePicture(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
    try {
        // Ensure uploads directory exists
        File uploadDir = new File("uploads");
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // Convert to PNG and save
        BufferedImage image = ImageIO.read(file.getInputStream());
        if (image == null) {
            return ResponseEntity.badRequest().body("Invalid image file");
        }

        File outputFile = new File("uploads/profile-" + id + ".png");
        ImageIO.write(image, "png", outputFile);

        return ResponseEntity.ok("Uploaded");
    } catch (Exception e) {
        e.printStackTrace();
        return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
    }
}


}
