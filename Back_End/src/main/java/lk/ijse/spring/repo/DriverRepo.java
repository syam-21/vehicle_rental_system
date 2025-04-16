package lk.ijse.spring.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.ijse.spring.entity.Driver;

public interface DriverRepo extends JpaRepository<Driver, Long> {
    boolean existsByEmail(String email);
    Driver findByEmail(String email);

}
