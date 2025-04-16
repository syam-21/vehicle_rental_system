package lk.ijse.spring.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lk.ijse.spring.entity.Booking;
import lk.ijse.spring.entity.Vehicle;
import lk.ijse.spring.util.BookingStatus;

public interface BookingRepo extends JpaRepository<Booking, Long> {
    List<Booking> findAllByUserId(Long userId);
    List<Booking> findAllByDriverId(Long driverId);
    List<Booking> findByVehicleAndStatus(Vehicle vehicle, BookingStatus status);

    // ✅ NEW: For trip count and earnings
    List<Booking> findAllByDriverIdAndStatus(Long driverId, BookingStatus status);
}
