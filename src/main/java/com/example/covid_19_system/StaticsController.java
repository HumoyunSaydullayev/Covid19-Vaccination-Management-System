package com.example.covid_19_system;

import Classes.Database;
import Classes.Rating;
import Classes.VaccinationStatus;
import Classes.ratingvaccine;
import javafx.animation.FadeTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.ResourceBundle;

public class StaticsController implements Initializable {
    @FXML
    public TableView<ratingvaccine> vaccinerating;
    @FXML
    private TableColumn<ratingvaccine, String> vaccine_name;
    @FXML
    private TableColumn<ratingvaccine, Integer> number_used;
    @FXML
    private TableView<Classes.Rating> RatingTable;
    @FXML
    private TableColumn<Rating, String> Polyclinic_name;
    @FXML
    private TableColumn<Rating, Integer> recover_of_patients;
    @FXML
    private TableColumn<Rating, Integer> recovering_of_patients;

    @FXML
    public PieChart statisticchart;
    private int polyclinicId;

    public void setPolyclinicId(int polyclinicId) {
        this.polyclinicId = polyclinicId;
    }

    public int getPolyclinicId() {
        return polyclinicId;
    }

    @FXML
    public ComboBox<String> levelSelector;

    private ObservableList<Rating> ratingList = FXCollections.observableArrayList();
    private Database db = new Database(); // Database obyekt

    // Method to load data from the database (Rating jadvali uchun)
    public void uploadData() {
        ArrayList<Rating> dbData = db.getRating();
        ratingList.clear();
        ratingList.addAll(dbData);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // TableView ustunlarini bog‘lash
        Polyclinic_name.setCellValueFactory(new PropertyValueFactory<>("Polyclinic_name"));
        recover_of_patients.setCellValueFactory(new PropertyValueFactory<>("recover_of_patients"));
        recovering_of_patients.setCellValueFactory(new PropertyValueFactory<>("recovering_of_patients"));

        // Jadval ma'lumotlari
        uploadData();
        RatingTable.setItems(ratingList);

        // Filtering
        FilteredList<Rating> filteredData = new FilteredList<>(ratingList, b -> true);

        // Custom comparator to sort primarily by 'recover_of_patients' and secondarily by 'recovering_of_patients'
        // Custom comparator to sort primarily by 'recover_of_patients' and secondarily by 'recovering_of_patients'
        Comparator<Rating> byRecovery = Comparator
                .comparingInt(Rating::getRecover_of_patients).reversed() // Birinchi ustunni kamayish tartibida saralash
                .thenComparingInt(Rating::getRecovering_of_patients).reversed(); // Ikkinchi ustunni ham kamayish tartibida saralash



        // Sorted data
        SortedList<Rating> sortedData = new SortedList<>(filteredData, byRecovery);
        sortedData.comparatorProperty().bind(RatingTable.comparatorProperty());
        RatingTable.setItems(sortedData);

        // ComboBox ga qiymatlar qo‘shish
        levelSelector.getItems().addAll("Poliklinika", "Tuman", "Viloyat");
        levelSelector.setValue("Poliklinika"); // Default tanlov

        // ComboBox o'zgarsa pie chart ni yangilash
        levelSelector.valueProperty().addListener((obs, oldVal, newVal) -> updatePieChart(newVal));

        // Dastlab pie chartni yangi daraja bo‘yicha yuklash
        updatePieChart(levelSelector.getValue());
    }

    public void updatePieChart(String level) {
//        int polyclinicId = 1;
        VaccinationStatus vs = db.getVaccinationStatusByPolyclinic(getPolyclinicId());

        if (vs == null) {
            statisticchart.setData(FXCollections.observableArrayList());
            statisticchart.setTitle("Ma'lumot topilmadi");
            return;
        }

        int emlanganCount;
        int emlanayotganCount;
        int emlanmaganCount;
        String titleText;

        switch (level) {
            case "Poliklinika":
                emlanganCount = vs.getPolyclinicEmlangan();
                emlanayotganCount = vs.getPolyclinicEmlanayotgan();
                emlanmaganCount = vs.getPolyclinicEmlanmagan();
                titleText = "Poliklinika (" + vs.getPolyclinicName() + ") darajasida statistikasi";
                break;
            case "Tuman":
                emlanganCount = vs.getDistrictEmlangan();
                emlanayotganCount = vs.getDistrictEmlanayotgan();
                emlanmaganCount = vs.getDistrictEmlanmagan();
                titleText = "Tuman (" + vs.getDistrictName() + ") darajasida statistikasi";
                break;
            case "Viloyat":
                emlanganCount = vs.getRegionEmlangan();
                emlanayotganCount = vs.getRegionEmlanayotgan();
                emlanmaganCount = vs.getRegionEmlanmagan();
                titleText = "Viloyat (" + vs.getRegionName() + ") darajasida statistikasi";
                break;
            default:
                emlanganCount = 0;
                emlanayotganCount = 0;
                emlanmaganCount = 0;
                titleText = "Ma'lumot topilmadi";
        }

        ObservableList<PieChart.Data> pieData = FXCollections.observableArrayList();
        if (emlanganCount > 0) {
            pieData.add(new PieChart.Data("Emlangan", emlanganCount));
        }
        if (emlanayotganCount > 0) {
            pieData.add(new PieChart.Data("Emlanayotgan", emlanayotganCount));
        }
        if (emlanmaganCount > 0) {
            pieData.add(new PieChart.Data("Emlanmagan", emlanmaganCount));
        }

        statisticchart.setData(pieData);
        statisticchart.setAnimated(true);
        statisticchart.setLabelsVisible(true);
        statisticchart.setLegendVisible(true);

        statisticchart.setTitle(titleText);

        int total = emlanganCount + emlanayotganCount + emlanmaganCount;
        double emlanganPercent = total == 0 ? 0 : (double) emlanganCount / total * 100;
        double emlanayotganPercent = total == 0 ? 0 : (double) emlanayotganCount / total * 100;
        double emlanmaganPercent = total == 0 ? 0 : (double) emlanmaganCount / total * 100;

        for (PieChart.Data data : statisticchart.getData()) {
            double value = data.getPieValue();
            double percentage = total == 0 ? 0 : (value / total) * 100;
            String formattedPercentage = String.format("%.0f%%", percentage);
            data.nameProperty().set(data.getName() + ": " + (int)value + " (" + formattedPercentage + ")");

            data.getNode().addEventHandler(MouseEvent.MOUSE_CLICKED, e -> {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Statistika");
                alert.setHeaderText("Grafik bo'limi ma'lumotlari");
                alert.setContentText(
                        "Umumiy: " + total + "\n" +
                                "Emlangan: " + emlanganCount + " (" + String.format("%.0f%%", emlanganPercent) + ")\n" +
                                "Emlanayotgan: " + emlanayotganCount + " (" + String.format("%.0f%%", emlanayotganPercent) + ")\n" +
                                "Emlanmagan: " + emlanmaganCount + " (" + String.format("%.0f%%", emlanmaganPercent) + ")"
                );
                alert.showAndWait();
            });
        }

        // Animatsiya
        FadeTransition ft = new FadeTransition(Duration.millis(800), statisticchart);
        ft.setFromValue(0);
        ft.setToValue(1);
        ft.play();
    }
}
