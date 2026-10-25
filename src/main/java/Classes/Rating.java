package Classes;

public class Rating {
    public int number;
    public String Polyclinic_name;
    public int recover_of_patients;
    public int recovering_of_patients;

    public Rating(String polyclinic_name, int recover_of_patients, int recovering_of_patients) {

        Polyclinic_name = polyclinic_name;
        this.recover_of_patients = recover_of_patients;
        this.recovering_of_patients = recovering_of_patients;
    }

    public String getPolyclinic_name() {
        return Polyclinic_name;
    }

    public void setPolyclinic_name(String polyclinic_name) {
        Polyclinic_name = polyclinic_name;
    }

    public int getRecover_of_patients() {
        return recover_of_patients;
    }

    public void setRecover_of_patients(int recover_of_patients) {
        this.recover_of_patients = recover_of_patients;
    }

    public int getRecovering_of_patients() {
        return recovering_of_patients;
    }

    public void setRecovering_of_patients(int recovering_of_patients) {
        this.recovering_of_patients = recovering_of_patients;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }
}
