package Classes;


public class Vaccines {
    public int id;
    public String name;
    public Integer Available_quantity;
    public Integer Number_of_receptions;
    public String About;
    public Integer Reception_range;


    public Vaccines(int id,String name, Integer available_quantity, Integer number_of_receptions, String about, Integer reception_range) {
        this.id = id;
        this.name = name;
        Available_quantity = available_quantity;
        Number_of_receptions = number_of_receptions;
        About = about;
        Reception_range = reception_range;
    }

    public Vaccines() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAvailable_quantity() {
        return Available_quantity;
    }

    public void setAvailable_quantity(Integer available_quantity) {
        Available_quantity = available_quantity;
    }

    public Integer getNumber_of_receptions() {
        return Number_of_receptions;
    }

    public void setNumber_of_receptions(Integer number_of_receptions) {
        Number_of_receptions = number_of_receptions;
    }

    public String getAbout() {
        return About;
    }

    public void setAbout(String about) {
        About = about;
    }

    public Integer getReception_range() {
        return Reception_range;
    }

    public void setReception_range(Integer reception_range) {
        Reception_range = reception_range;
    }
}
