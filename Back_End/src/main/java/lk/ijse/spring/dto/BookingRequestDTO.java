package lk.ijse.spring.dto;

public class BookingRequestDTO {

    private Long userId;
    private Long vehicleId;
    private Long driverId;
    private String pickupDate;
    private String returnDate;

    private Long bookingId;
    private String status;

    private LocationDTO pickup;
    private LocationDTO drop;

    // --- Getters and Setters ---

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(Long vehicleId) {
        this.vehicleId = vehicleId;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(String pickupDate) {
        this.pickupDate = pickupDate;
    }

    public String getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }

    public Long getBookingId() {
        return bookingId;
    }

    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocationDTO getPickup() {
        return pickup;
    }

    public void setPickup(LocationDTO pickup) {
        this.pickup = pickup;
    }

    public LocationDTO getDrop() {
        return drop;
    }

    public void setDrop(LocationDTO drop) {
        this.drop = drop;
    }

    // --- Inner DTO Class ---
    public static class LocationDTO {
        private String division;
        private String district;
        private String upazila;
        private String address;

        public String getDivision() {
            return division;
        }

        public void setDivision(String division) {
            this.division = division;
        }

        public String getDistrict() {
            return district;
        }

        public void setDistrict(String district) {
            this.district = district;
        }

        public String getUpazila() {
            return upazila;
        }

        public void setUpazila(String upazila) {
            this.upazila = upazila;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }
    }
}
