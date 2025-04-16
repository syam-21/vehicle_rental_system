package lk.ijse.spring.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DriverDTO {

    private Long id;
    private String name;
    private String phone;
    private String email;
    private String password;
    private String licenseNumber;
    private String nidNumber;
    private String address;
    private String gender; // ✅ added

    private String profilePhotoUrl;
    private String licensePhotoUrl;
    private String nidPhotoUrl;

    private boolean available;
    private int tripCount;
    private double rating;

    public DriverDTO() {
    }

    public DriverDTO(Long id, String name, String phone, String email, String password,
                     String licenseNumber, String nidNumber, String address, String gender,
                     String profilePhotoUrl, String licensePhotoUrl, String nidPhotoUrl,
                     boolean available, int tripCount, double rating) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.licenseNumber = licenseNumber;
        this.nidNumber = nidNumber;
        this.address = address;
        this.gender = gender; // ✅ added
        this.profilePhotoUrl = profilePhotoUrl;
        this.licensePhotoUrl = licensePhotoUrl;
        this.nidPhotoUrl = nidPhotoUrl;
        this.available = available;
        this.tripCount = tripCount;
        this.rating = rating;
    }

    // ✅ Getters and Setters

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

    public String getNidNumber() {
        return nidNumber;
    }

    public void setNidNumber(String nidNumber) {
        this.nidNumber = nidNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getProfilePhotoUrl() {
        return profilePhotoUrl;
    }

    public void setProfilePhotoUrl(String profilePhotoUrl) {
        this.profilePhotoUrl = profilePhotoUrl;
    }

    public String getLicensePhotoUrl() {
        return licensePhotoUrl;
    }

    public void setLicensePhotoUrl(String licensePhotoUrl) {
        this.licensePhotoUrl = licensePhotoUrl;
    }

    public String getNidPhotoUrl() {
        return nidPhotoUrl;
    }

    public void setNidPhotoUrl(String nidPhotoUrl) {
        this.nidPhotoUrl = nidPhotoUrl;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public int getTripCount() {
        return tripCount;
    }

    public void setTripCount(int tripCount) {
        this.tripCount = tripCount;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }
}
