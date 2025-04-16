package lk.ijse.spring.entity;

import java.util.List;

import jakarta.persistence.CascadeType; // ✅ YOU MISSED THIS LINE BEFORE
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "driver")
public class Driver {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;
    private String phone;
    private String email;
    private String password;
    private String licenseNumber;
    private String licenseImagePath;
    private String nidNumber;
    private String nidImagePath;
    private String address;
    private String photoPath;
    private String gender;
    private boolean available = true;
    private int completedTrips = 0;
    private double rating = 0;

    // ✅ Cascade delete all vehicles when driver is deleted
    @OneToMany(mappedBy = "driver", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vehicle> vehicles;

    public Driver() {}

    // --- Getters & Setters ---

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

    public String getLicenseNumber() {
        return licenseNumber;
    }
    public void setLicenseNumber(String licenseNumber) {
        this.licenseNumber = licenseNumber;
    }

    public String getLicenseImagePath() {
        return licenseImagePath;
    }
    public void setLicenseImagePath(String licenseImagePath) {
        this.licenseImagePath = licenseImagePath;
    }

    public String getNidNumber() {
        return nidNumber;
    }
    public void setNidNumber(String nidNumber) {
        this.nidNumber = nidNumber;
    }

    public String getNidImagePath() {
        return nidImagePath;
    }
    public void setNidImagePath(String nidImagePath) {
        this.nidImagePath = nidImagePath;
    }

    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhotoPath() {
        return photoPath;
    }
    public void setPhotoPath(String photoPath) {
        this.photoPath = photoPath;
    }

    public boolean isAvailable() {
        return available;
    }
    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getCompletedTrips() {
        return completedTrips;
    }
    public void setCompletedTrips(int completedTrips) {
        this.completedTrips = completedTrips;
    }

    public double getRating() {
        return rating;
    }
    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getGender() {
        return gender;
    }
    public void setGender(String gender) {
        this.gender = gender;
    }
}
