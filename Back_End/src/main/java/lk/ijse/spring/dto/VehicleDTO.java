package lk.ijse.spring.dto;

public class VehicleDTO {
    private Long id;
    private String brand;
    private String model;
    private String type;
    private double pricePerDay;
    private boolean approved;
    private Long driverId;
    private String imagePath; // ✅ This is the missing field
    private DriverDTO driver;
    public VehicleDTO() {}

    public VehicleDTO(Long id, String brand, String model, String type, double pricePerDay, String imagePath, Long driverId, DriverDTO driver) {
        this.id = id;
        this.brand = brand;
        this.model = model;
        this.type = type;
        this.pricePerDay = pricePerDay;
        this.imagePath = imagePath;
        this.driverId = driverId;
        this.driver = driver;
    }
    

    // ✅ Getters and setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public boolean isApproved() {
        return approved;
    }

    public void setApproved(boolean approved) {
        this.approved = approved;
    }

    public Long getDriverId() {
        return driverId;
    }

    public void setDriverId(Long driverId) {
        this.driverId = driverId;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
    public DriverDTO getDriver() {
        return driver;
    }
    
    public void setDriver(DriverDTO driver) {
        this.driver = driver;
    }
    
}
