package lk.ijse.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.ijse.spring.entity.Admin;

public interface AdminRepo extends JpaRepository<Admin, String> {
    Admin findByEmail(String email);
}
