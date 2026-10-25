package User;

import Classes.Database;
import Classes.Staff;
import Classes.Vaccines;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class UserDashboardDoctorsController implements Initializable {
    @FXML
    public TableView<Staff> staffs;
    @FXML
    public TableColumn<Staff, String> firstname;
    @FXML
    public TableColumn<Staff, String> lastname;
    @FXML
    public TableColumn<Staff, String> Specialization;
    @FXML
    public TableColumn<Staff, Integer> rating;
    @FXML
    private TextField keywordTextField;
    int polyclinic_id;
    public void setId(int id) {
        this.polyclinic_id = id;
    }

    private ObservableList<Staff> stafflist = FXCollections.observableArrayList();

    public void upload_data(){
        // Database obyekti orqali ma'lumotlarni olish
        Database db = new Database(); // Ma'lumotlarni yuklash

        // Ma'lumotlarni ArrayList dan ObservableList ga qo'shish
        ArrayList<Staff> doctor = db.getStaffDetailsWithRatingByPolyclinic(polyclinic_id);
        stafflist.clear();

        // Populate the ObservableList with fetched data
        stafflist.addAll(doctor);
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        firstname.setCellValueFactory(new PropertyValueFactory<>("firstName"));
        lastname.setCellValueFactory(new PropertyValueFactory<>("lastName"));
        Specialization.setCellValueFactory(new PropertyValueFactory<>("Specialization"));
        rating.setCellValueFactory(new PropertyValueFactory<>("rating"));

        staffs.setItems(stafflist);


        // Jadvalni filtrlash
        FilteredList<Staff> filteredData = new FilteredList<>(stafflist, b -> true);
        keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(vaccine -> {
                // Filtr matni bo'sh yoki null bo'lsa, barcha ma'lumotlarni qaytaradi
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                // Qidiruv uchun shartlar
                if (vaccine.getFirstName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (String.valueOf(vaccine.getSpecialization()).contains(searchKeyword)) {
                    return true;
                } else return String.valueOf(vaccine.getRating()).contains(searchKeyword);
            });
        });

        // Filtrlangan ma'lumotlarni saralash
        SortedList<Staff> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(staffs.comparatorProperty());

        // Jadval ko'rinishiga filtrlangan va tartiblangan ma'lumotlarni qo'llash
        staffs.setItems(sortedData);
    }


}
