package lk.ijse.spring.dto;

public class AdminDTO {
    private String adminId;
    private String name;
    private String contact;
    private String email;
    private String password;

    public AdminDTO() {}

    public AdminDTO(String adminId, String name, String contact, String email, String password) {
        this.adminId = adminId;
        this.name = name;
        this.contact = contact;
        this.email = email;
        this.password = password;
    }

    public String getAdminId() { return adminId; }
    public void setAdminId(String adminId) { this.adminId = adminId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getContact() { return contact; }
    public void setContact(String contact) { this.contact = contact; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}
