package Classes;

public class Polyclinic_Admins {
    private int polyclinicAdminId; // ID for admin (Primary Key)
    private String username;       // Username for login
    private String password;       // Password for login
    private String name;           // Admin's first name
    private String surname;        // Admin's last name
    private String contactInfo;    // Admin's contact information
    private int polyclinicId;      // Related polyclinic ID (Foreign Key)

    // Constructor
    public Polyclinic_Admins(int polyclinicAdminId, String username, String password, String name, String surname, String contactInfo, int polyclinicId) {
        this.polyclinicAdminId = polyclinicAdminId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.contactInfo = contactInfo;
        this.polyclinicId = polyclinicId;
    }

    // Getters and Setters
    public int getPolyclinicAdminId() {
        return polyclinicAdminId;
    }

    public void setPolyclinicAdminId(int polyclinicAdminId) {
        this.polyclinicAdminId = polyclinicAdminId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public int getPolyclinicId() {
        return polyclinicId;
    }

    public void setPolyclinicId(int polyclinicId) {
        this.polyclinicId = polyclinicId;
    }
}
