package lk.ijse.spring.controller;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lk.ijse.spring.dto.BookingRequestDTO;
import lk.ijse.spring.dto.BookingSummaryDTO;
import lk.ijse.spring.entity.Booking;
import lk.ijse.spring.repo.BookingRepo;
import lk.ijse.spring.service.BookingService;

@CrossOrigin
@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private BookingRepo bookingRepo;

    // ✅ Create a new booking
@PostMapping("/book")
public ResponseEntity<?> bookVehicle(@RequestBody BookingRequestDTO dto) {
    try {
        bookingService.createBooking(dto);
        Map<String, String> response = new HashMap<>();
        response.put("message", "Booking successful");
        return ResponseEntity.ok(response); // ✅ Return valid JSON
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(Collections.singletonMap("error", "Booking failed: " + e.getMessage()));
    }
}

    

    // ✅ Get all bookings of a user
    @GetMapping("/user/{userId}")
    public List<Booking> getBookingsByUser(@PathVariable Long userId) {
        return bookingService.getBookingsByUser(userId);
    }
    

    // ✅ Cancel a booking by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<?> cancelBooking(@PathVariable Long id) {
        try {
            bookingService.cancelBooking(id);
            return ResponseEntity.ok("Booking cancelled successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to cancel booking: " + e.getMessage());
        }
    }

    // ✅ Get bookings assigned to a driver
    @GetMapping("/driver/{driverId}")
    public ResponseEntity<List<Booking>> getBookingsByDriver(@PathVariable Long driverId) {
        List<Booking> bookings = bookingService.getBookingsByDriver(driverId);
        return ResponseEntity.ok(bookings);
    }

    // ✅ Assign driver to a booking & update status (Confirm/Reject)
    @PostMapping("/assign")
    public ResponseEntity<?> assignBooking(@RequestBody BookingRequestDTO dto) {
        try {
            bookingService.updateBookingStatus(dto.getBookingId(), dto.getDriverId(), dto.getStatus());
            return ResponseEntity.ok("Booking updated successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update booking: " + e.getMessage());
        }
    }

    // ✅ Get driver booking summary (total, completed, etc)
    @GetMapping("/driver/{driverId}/summary")
    public BookingSummaryDTO getDriverSummary(@PathVariable Long driverId) {
        return bookingService.getDriverBookingSummary(driverId);
    }

    @PostMapping("/update-status")
public ResponseEntity<?> updateBookingStatus(@RequestBody BookingRequestDTO dto) {
    bookingService.updateBookingStatus(dto.getBookingId(), dto.getDriverId(), dto.getStatus());
    return ResponseEntity.ok("Booking " + dto.getStatus().toLowerCase());
}

}
