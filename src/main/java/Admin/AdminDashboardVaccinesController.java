package Admin;

import Classes.Database;
import Classes.Vaccines;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class AdminDashboardVaccinesController implements Initializable {
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
    @FXML
    private TextField new_name;
    @FXML
    private TextField new_available_quantity;
    boolean add=false,edit=false;
    int polyclinic_id;
    int id;

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
        new_name.setEditable(false);
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



    public void Update(ActionEvent actionEvent) {
        Vaccines selected = Vaccine.getSelectionModel().getSelectedItem();
        if (selected == null) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("No data selected");
            alert.setContentText("Please select a vaccine to update.");
            alert.showAndWait();
        } else {
            id = selected.getId();
            new_name.setText(selected.getName());
            new_available_quantity.setText(String.valueOf(selected.getAvailable_quantity()));
            add = false;
            edit = true;

            // Since name should not be editable during update, ensure it's non-editable
            new_name.setEditable(false);
        }
    }


    public void Insert(ActionEvent actionEvent) {
        String vaccineName = new_name.getText().trim();
        String availableQuantityStr = new_available_quantity.getText().trim();

        if (vaccineName.isEmpty() || availableQuantityStr.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Data not entered");
            alert.setContentText("Please fill in all fields.");
            alert.showAndWait();
            return;
        }

        int availableQuantity;
        try {
            availableQuantity = Integer.parseInt(availableQuantityStr);
            if (availableQuantity < 0) throw new NumberFormatException();
        } catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Input");
            alert.setHeaderText("Invalid Quantity");
            alert.setContentText("Please enter a valid non-negative integer for available quantity.");
            alert.showAndWait();
            return;
        }

        Database db = new Database();
        Vaccines existingVaccine = db.getVaccineByNameAndPolyclinic(vaccineName, polyclinic_id);

        if (existingVaccine != null) {
            // Vaccine exists, update quantity
            boolean success = db.updateVaccineQuantity(existingVaccine.getId(), availableQuantity);
            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Vaccine Updated");
                alert.setContentText("Existing vaccine quantity has been updated successfully.");
                alert.showAndWait();
                RefreshTable();
                new_name.setText("");
                new_available_quantity.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Update Failed");
                alert.setContentText("Failed to update vaccine quantity.");
                alert.showAndWait();
            }
        } else {
            // Vaccine does not exist, add new
            boolean success = db.addVaccineToPolyclinic(vaccineName, polyclinic_id, availableQuantity);
            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Vaccine Added");
                alert.setContentText("New vaccine has been added successfully.");
                alert.showAndWait();
                RefreshTable();
                new_name.setText("");
                new_available_quantity.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Addition Failed");
                alert.setContentText("Failed to add new vaccine. Ensure the vaccine exists in the Vaccines table.");
                alert.showAndWait();
            }
        }
    }


    public void Save(ActionEvent actionEvent) throws IOException, ClassNotFoundException {
        if (add) {
            // Handle Insert
            String vaccineName = new_name.getText().trim();
            String availableQuantityStr = new_available_quantity.getText().trim();

            if (vaccineName.isEmpty() || availableQuantityStr.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Data not entered");
                alert.setContentText("Please fill in all fields.");
                alert.showAndWait();
                return;
            }

            int availableQuantity;
            try {
                availableQuantity = Integer.parseInt(availableQuantityStr);
                if (availableQuantity < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Invalid Quantity");
                alert.setContentText("Please enter a valid non-negative integer for available quantity.");
                alert.showAndWait();
                return;
            }

            Database db = new Database();
            Vaccines existingVaccine = db.getVaccineByNameAndPolyclinic(vaccineName, polyclinic_id);

            if (existingVaccine != null) {
                // Vaccine exists, update quantity
                boolean success = db.updateVaccineQuantity(existingVaccine.getId(), availableQuantity);
                if (success) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Vaccine Updated");
                    alert.setContentText("Existing vaccine quantity has been updated successfully.");
                    alert.showAndWait();
                    RefreshTable();
                    new_name.setText("");
                    new_available_quantity.setText("");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Update Failed");
                    alert.setContentText("Failed to update vaccine quantity.");
                    alert.showAndWait();
                }
            } else {
                // Vaccine does not exist, add new
                boolean success = db.addVaccineToPolyclinic(vaccineName, polyclinic_id, availableQuantity);
                if (success) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Success");
                    alert.setHeaderText("Vaccine Added");
                    alert.setContentText("New vaccine has been added successfully.");
                    alert.showAndWait();
                    RefreshTable();
                    new_name.setText("");
                    new_available_quantity.setText("");
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Addition Failed");
                    alert.setContentText("Failed to add new vaccine. Ensure the vaccine exists in the Vaccines table.");
                    alert.showAndWait();
                }
            }
            add = false; // Reset flag after operation
        } else if (edit) {
            // Handle Update
            String availableQuantityStr = new_available_quantity.getText().trim();

            if (availableQuantityStr.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Warning");
                alert.setHeaderText("Data not entered");
                alert.setContentText("Please fill in the available quantity field.");
                alert.showAndWait();
                return;
            }

            int newAvailableQuantity;
            try {
                newAvailableQuantity = Integer.parseInt(availableQuantityStr);
                if (newAvailableQuantity < 0) throw new NumberFormatException();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid Input");
                alert.setHeaderText("Invalid Quantity");
                alert.setContentText("Please enter a valid non-negative integer for available quantity.");
                alert.showAndWait();
                return;
            }

            Database db = new Database();
            // Since we're updating, we assume the vaccine exists
            boolean success = db.updateVaccineQuantity(id, newAvailableQuantity - db.getVaccinesByPolyclinic(polyclinic_id).stream()
                    .filter(v -> v.getId() == id)
                    .findFirst()
                    .orElse(new Vaccines()).getAvailable_quantity());

            if (success) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Success");
                alert.setHeaderText("Vaccine Updated");
                alert.setContentText("Vaccine quantity has been updated successfully.");
                alert.showAndWait();
                RefreshTable();
                new_name.setText("");
                new_available_quantity.setText("");
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Update Failed");
                alert.setContentText("Failed to update vaccine quantity.");
                alert.showAndWait();
            }
            edit = false; // Reset flag after operation
        }
    }

    public void Clear(ActionEvent actionEvent) {
        new_name.setText("");
        new_available_quantity.setText("");
    }




}
