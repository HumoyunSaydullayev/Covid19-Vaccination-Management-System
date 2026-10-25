package Classes;

public class Staff {
    private int staffId;
    private String name;
    private String firstName;
    private String lastName;
    private String Specialization;
    private int rating;

    public Staff(int staffId, String firstName, String lastName, String specialization, int rating) {
        this.staffId = staffId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.Specialization = specialization;
        this.rating = rating;
        setName(); // Initialize 'name' property
    }

    public void setName() {
        this.name = firstName + " " + lastName;
    }

    public String getName() {
        return name;
    }

    // Getters and Setters for other properties
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) {
        this.firstName = firstName;
        setName();
    }

    public String getLastName() { return lastName; }
    public void setLastName(String lastName) {
        this.lastName = lastName;
        setName();
    }

    public String getSpecialization() { return Specialization; }
    public void setSpecialization(String specialization) {
        this.Specialization = specialization;
    }

    public int getRating() { return rating; }
    public void setRating(int rating) { this.rating = rating; }

    public int getStaffId() { return staffId; }
    public void setStaffId(int staffId) { this.staffId = staffId; }
}
