package Classes;

import java.util.Date;

public class Citizen {
    private int citizenId;
    private String firstName;
    private String lastName;
    private Date birthDate;
    private String passportNumber;
    private int polyclinicId;
    private String vaccineName;
    private int timesVaccinated;
    private int remainingDoses;
    private Date lastVaccinationDate;
    private Date nextVaccinationDate;
    private boolean vaccinationStatus;

    // Constructor
    public Citizen(int citizenId, String firstName, String lastName, Date birthDate, String passportNumber,
                   int polyclinicId, String vaccineName, int timesVaccinated, int remainingDoses,
                   Date lastVaccinationDate, Date nextVaccinationDate, boolean vaccinationStatus) {
        this.citizenId = citizenId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.birthDate = birthDate;
        this.passportNumber = passportNumber;
        this.polyclinicId = polyclinicId;
        this.vaccineName = vaccineName;
        this.timesVaccinated = timesVaccinated;
        this.remainingDoses = remainingDoses;
        this.lastVaccinationDate = lastVaccinationDate;
        this.nextVaccinationDate = nextVaccinationDate;
        this.vaccinationStatus = vaccinationStatus;
    }

    public int getCitizenId() {
        return citizenId;
    }

    public void setCitizenId(int citizenId) {
        this.citizenId = citizenId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassportNumber() {
        return passportNumber;
    }

    public void setPassportNumber(String passportNumber) {
        this.passportNumber = passportNumber;
    }

    public int getPolyclinicId() {
        return polyclinicId;
    }

    public void setPolyclinicId(int polyclinicId) {
        this.polyclinicId = polyclinicId;
    }

    public String getVaccineName() {
        return vaccineName;
    }

    public void setVaccineName(String vaccineName) {
        this.vaccineName = vaccineName;
    }

    public int getTimesVaccinated() {
        return timesVaccinated;
    }

    public void setTimesVaccinated(int timesVaccinated) {
        this.timesVaccinated = timesVaccinated;
    }

    public int getRemainingDoses() {
        return remainingDoses;
    }

    public void setRemainingDoses(int remainingDoses) {
        this.remainingDoses = remainingDoses;
    }

    public Date getLastVaccinationDate() {
        return lastVaccinationDate;
    }

    public void setLastVaccinationDate(Date lastVaccinationDate) {
        this.lastVaccinationDate = lastVaccinationDate;
    }

    public Date getNextVaccinationDate() {
        return nextVaccinationDate;
    }

    public void setNextVaccinationDate(Date nextVaccinationDate) {
        this.nextVaccinationDate = nextVaccinationDate;
    }

    public boolean isVaccinationStatus() {
        return vaccinationStatus;
    }

    public void setVaccinationStatus(boolean vaccinationStatus) {
        this.vaccinationStatus = vaccinationStatus;
    }
// Getters and setters (if needed)
}
