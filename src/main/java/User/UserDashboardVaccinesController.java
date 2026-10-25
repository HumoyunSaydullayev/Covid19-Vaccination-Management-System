package User;

import Classes.Database;
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

public class UserDashboardVaccinesController implements Initializable {
    @FXML
    public TableView<Vaccines> Vaccine;
    @FXML
    public TableColumn<Vaccines, String> name;
    @FXML
    public TableColumn<Vaccines, Integer> Available_quantity;
    @FXML
    public TableColumn<Vaccines, Integer> Number_of_receptions;
    @FXML
    public TableColumn<Vaccines, String> About;
    @FXML
    public TableColumn<Vaccines, Integer> Reception_range;
    @FXML
    private TextField keywordTextField;
    int polyclinic_id;
    public void setId(int id) {
        this.polyclinic_id = id;
    }
    private ObservableList<Vaccines> vaccineList = FXCollections.observableArrayList();

    public void upload_data(){
        // Database obyekti orqali ma'lumotlarni olish
        Database db = new Database(); // Ma'lumotlarni yuklash

        // Ma'lumotlarni ArrayList dan ObservableList ga qo'shish
        ArrayList<Vaccines> vaccineData = db.getVaccinesByPolyclinic(polyclinic_id);

        for (Vaccines item : vaccineData) {
            vaccineList.add(new Vaccines(
                    item.getId(),
                    item.getName(),
                    item.getAvailable_quantity(),
                    item.getNumber_of_receptions(),
                    item.getAbout(),
                    item.getReception_range()
            ));
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        // Jadvalning ustunlariga qiymatlarni bog'lash
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Available_quantity.setCellValueFactory(new PropertyValueFactory<>("Available_quantity"));
        Number_of_receptions.setCellValueFactory(new PropertyValueFactory<>("Number_of_receptions"));
        About.setCellValueFactory(new PropertyValueFactory<>("About"));
        Reception_range.setCellValueFactory(new PropertyValueFactory<>("Reception_range"));

        // Ma'lumotlarni TableView bilan bog'lash
        Vaccine.setItems(vaccineList);

        // Jadvalni filtrlash
        FilteredList<Vaccines> filteredData = new FilteredList<>(vaccineList, b -> true);
        keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(vaccine -> {
                // Filtr matni bo'sh yoki null bo'lsa, barcha ma'lumotlarni qaytaradi
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                // Qidiruv uchun shartlar
                if (vaccine.getName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (String.valueOf(vaccine.getAvailable_quantity()).contains(searchKeyword)) {
                    return true;
                } else if (String.valueOf(vaccine.getNumber_of_receptions()).contains(searchKeyword)) {
                    return true;
                } else // Hech bir shart mos kelmasa
                    if (String.valueOf(vaccine.getAbout()).toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else return String.valueOf(vaccine.getReception_range()).contains(searchKeyword);
            });
        });

        // Filtrlangan ma'lumotlarni saralash
        SortedList<Vaccines> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Vaccine.comparatorProperty());

        // Jadval ko'rinishiga filtrlangan va tartiblangan ma'lumotlarni qo'llash
        Vaccine.setItems(sortedData);

    }

    public void RefreshTable() {
        // Ma'lumotlarni tozalash
        vaccineList.clear();
        upload_data();

        // Jadvalning ustunlariga qiymatlarni bog'lash
        name.setCellValueFactory(new PropertyValueFactory<>("name"));
        Available_quantity.setCellValueFactory(new PropertyValueFactory<>("Available_quantity"));
        Number_of_receptions.setCellValueFactory(new PropertyValueFactory<>("Number_of_receptions"));
        About.setCellValueFactory(new PropertyValueFactory<>("About"));
        Reception_range.setCellValueFactory(new PropertyValueFactory<>("Reception_range"));

        // Ma'lumotlarni TableView bilan bog'lash
        Vaccine.setItems(vaccineList);

        // Jadvalni filtrlash
        FilteredList<Vaccines> filteredData = new FilteredList<>(vaccineList, b -> true);
        keywordTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(vaccine -> {
                // Filtr matni bo'sh yoki null bo'lsa, barcha ma'lumotlarni qaytaradi
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }

                String searchKeyword = newValue.toLowerCase();

                // Qidiruv uchun shartlar
                if (vaccine.getName().toLowerCase().contains(searchKeyword)) {
                    return true;
                } else if (String.valueOf(vaccine.getAvailable_quantity()).contains(searchKeyword)) {
                    return true;
                } else if (String.valueOf(vaccine.getNumber_of_receptions()).contains(searchKeyword)) {
                    return true;
                } else // Hech bir shart mos kelmasa
                    if (String.valueOf(vaccine.getAbout()).toLowerCase().contains(searchKeyword)) {
                        return true;
                    } else return String.valueOf(vaccine.getReception_range()).contains(searchKeyword);
            });
        });

        // Filtrlangan ma'lumotlarni saralash
        SortedList<Vaccines> sortedData = new SortedList<>(filteredData);
        sortedData.comparatorProperty().bind(Vaccine.comparatorProperty());

        // Jadval ko'rinishiga filtrlangan va tartiblangan ma'lumotlarni qo'llash
        Vaccine.setItems(sortedData);
    }


}
