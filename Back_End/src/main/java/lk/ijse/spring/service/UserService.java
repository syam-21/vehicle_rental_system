package lk.ijse.spring.service;

import lk.ijse.spring.dto.UserDTO;
import lk.ijse.spring.entity.User;

public interface UserService {
    User updateUserProfile(Long id, UserDTO updatedDTO);
}
