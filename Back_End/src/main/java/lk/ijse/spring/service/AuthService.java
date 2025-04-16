package lk.ijse.spring.service;

import lk.ijse.spring.dto.UserDTO;

public interface AuthService {
    void register(UserDTO dto);
    UserDTO login(String email, String password); // ✅ This is the method to implement
}
