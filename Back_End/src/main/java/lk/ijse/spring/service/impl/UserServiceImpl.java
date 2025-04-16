package lk.ijse.spring.service.impl;

import lk.ijse.spring.dto.UserDTO;
import lk.ijse.spring.entity.User;
import lk.ijse.spring.repo.UserRepo;
import lk.ijse.spring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepo userRepo;

    @Override
    public User updateUserProfile(Long id, UserDTO updatedDTO) {
        Optional<User> existing = userRepo.findById(id);
        if (existing.isEmpty()) throw new RuntimeException("User not found");

        User user = existing.get();
        user.setPhone(updatedDTO.getPhone());
        user.setAddress(updatedDTO.getAddress());
        user.setGender(updatedDTO.getGender());
        user.setBloodGroup(updatedDTO.getBloodGroup());
        user.setDateOfBirth(updatedDTO.getDateOfBirth());

        return userRepo.save(user);
    }
}
