
package lk.ijse.spring.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lk.ijse.spring.dto.BookingRequestDTO;
import lk.ijse.spring.dto.BookingSummaryDTO;
import lk.ijse.spring.entity.Booking;
import lk.ijse.spring.entity.Driver;
import lk.ijse.spring.repo.BookingRepo;
import lk.ijse.spring.repo.DriverRepo;
import lk.ijse.spring.repo.UserRepo;
import lk.ijse.spring.repo.VehicleRepo;
import lk.ijse.spring.service.BookingService;
import lk.ijse.spring.util.BookingStatus;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private VehicleRepo vehicleRepo;

    @Autowired
    private DriverRepo driverRepo;

    @Override
    public void createBooking(BookingRequestDTO dto) {
        try {
            Booking booking = new Booking();

            booking.setPickupDate(LocalDate.parse(dto.getPickupDate()));
            booking.setReturnDate(LocalDate.parse(dto.getReturnDate()));

            booking.setPickupLocation(
                dto.getPickup().getDivision() + ", " +
                dto.getPickup().getDistrict() + ", " +
                dto.getPickup().getUpazila() + ", " +
                dto.getPickup().getAddress()
            );

            booking.setDropLocation(
                dto.getDrop().getDivision() + ", " +
                dto.getDrop().getDistrict() + ", " +
                dto.getDrop().getUpazila() + ", " +
                dto.getDrop().getAddress()
            );

            booking.setUser(userRepo.findById(dto.getUserId()).orElseThrow(() -> new RuntimeException("User not found")));
            booking.setVehicle(vehicleRepo.findById(dto.getVehicleId()).orElseThrow(() -> new RuntimeException("Vehicle not found")));
            booking.setStatus(BookingStatus.PENDING);

            long days = booking.getReturnDate().toEpochDay() - booking.getPickupDate().toEpochDay();
            if (days <= 0) days = 1;
            double fare = booking.getVehicle().getPricePerDay() * days;
            booking.setFare(fare);

            bookingRepo.save(booking);
            System.out.println("✅ Booking saved successfully");
        } catch (Exception e) {
            System.out.println("❌ Booking save failed: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = bookingRepo.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

        if (booking.getStatus() != BookingStatus.PENDING) {
            throw new RuntimeException("Only pending bookings can be cancelled");
        }

        booking.setStatus(BookingStatus.REJECTED);
        bookingRepo.save(booking);
    }

    @Override
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepo.findAllByUserId(userId);
    }

    @Override
    public List<Booking> getBookingsByDriver(Long driverId) {
        return bookingRepo.findAllByDriverId(driverId);
    }

    @Override
    public void updateBookingStatus(Long bookingId, Long driverId, String status) {
        Booking booking = bookingRepo.findById(bookingId)
            .orElseThrow(() -> new RuntimeException("Booking not found"));

        Driver driver = driverRepo.findById(driverId)
            .orElseThrow(() -> new RuntimeException("Driver not found"));

        if (!status.equals("CONFIRMED") && !status.equals("REJECTED")) {
            throw new IllegalArgumentException("Invalid status");
        }

        booking.setStatus(BookingStatus.valueOf(status));
        booking.setDriver(driver);
        bookingRepo.save(booking);
    }

    @Override
    public BookingSummaryDTO getDriverBookingSummary(Long driverId) {
        List<Booking> bookings = bookingRepo.findAllByDriverIdAndStatus(driverId, BookingStatus.COMPLETED);

        int tripCount = bookings.size();
        double totalEarned = bookings.stream()
                .mapToDouble(Booking::getFare)
                .sum();

        return new BookingSummaryDTO(tripCount, totalEarned);
    }
}
