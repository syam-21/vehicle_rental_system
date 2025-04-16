package lk.ijse.spring.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lk.ijse.spring.dto.AdminDTO;
import lk.ijse.spring.entity.Admin;
import lk.ijse.spring.repo.AdminRepo;
import lk.ijse.spring.service.AdminService;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminRepo adminRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public AdminDTO login(String email, String password) {
        Admin admin = adminRepo.findByEmail(email);
        if (admin == null) {
            throw new RuntimeException("Admin not found");
        }

        // ✅ Plain text password check (NOT BCrypt)
        if (!admin.getPassword().equals(password)) {
            throw new RuntimeException("Incorrect password");
        }

        return modelMapper.map(admin, AdminDTO.class);
    }
}
