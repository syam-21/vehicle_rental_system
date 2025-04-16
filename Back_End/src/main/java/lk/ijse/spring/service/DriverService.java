package lk.ijse.spring.service;
import java.util.List;

import lk.ijse.spring.dto.DriverDTO;

public interface DriverService {
    DriverDTO registerDriver(DriverDTO dto);
    DriverDTO getDriverById(Long id);
    List<DriverDTO> getAllDrivers();

}
