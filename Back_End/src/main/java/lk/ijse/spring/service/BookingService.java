package lk.ijse.spring.service;

import java.util.List;

import lk.ijse.spring.dto.BookingRequestDTO;
import lk.ijse.spring.dto.BookingSummaryDTO;
import lk.ijse.spring.entity.Booking;

public interface BookingService {
    void createBooking(BookingRequestDTO dto);
    void cancelBooking(Long bookingId);
    List<Booking> getBookingsByUser(Long userId);
BookingSummaryDTO getDriverBookingSummary(Long driverId);

    // ✅ Add these two methods
    List<Booking> getBookingsByDriver(Long driverId);
    void updateBookingStatus(Long bookingId, Long driverId, String status);  // confirm or reject
}
