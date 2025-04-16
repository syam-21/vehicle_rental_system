package lk.ijse.spring.dto;


public class BookingSummaryDTO {
    private int tripCount;
    private double totalEarnings;

    public BookingSummaryDTO() {}

    public BookingSummaryDTO(int tripCount, double totalEarnings) {
        this.tripCount = tripCount;
        this.totalEarnings = totalEarnings;
    }

    public int getTripCount() {
        return tripCount;
    }

    public void setTripCount(int tripCount) {
        this.tripCount = tripCount;
    }

    public double getTotalEarnings() {
        return totalEarnings;
    }

    public void setTotalEarnings(double totalEarnings) {
        this.totalEarnings = totalEarnings;
    }
}
