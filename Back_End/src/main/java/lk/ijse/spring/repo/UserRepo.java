package lk.ijse.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.ijse.spring.entity.User;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
