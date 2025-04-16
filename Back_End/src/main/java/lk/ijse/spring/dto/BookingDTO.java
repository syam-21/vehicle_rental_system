package lk.ijse.spring.dto;

import java.time.LocalDate;

public class BookingDTO {

    private Long vehicleId;
    private LocalDate pickupDate;
    private LocalDate returnDate;

    public BookingDTO() {}

    public BookingDTO(Long vehicleId, LocalDate pickupDate, LocalDate returnDate) {
        this.vehicleId = vehicleId;
        this.pickupDate = pickupDate;
        this.returnDate = returnDate;
    }

    // Getters and setters
    public Long getVehicleId() { return vehicleId; }
    public void setVehicleId(Long vehicleId) { this.vehicleId = vehicleId; }

    public LocalDate getPickupDate() { return pickupDate; }
    public void setPickupDate(LocalDate pickupDate) { this.pickupDate = pickupDate; }

    public LocalDate getReturnDate() { return returnDate; }
    public void setReturnDate(LocalDate returnDate) { this.returnDate = returnDate; }
}
