package User;

import Classes.Citizen;
import com.example.covid_19_system.HelloApplication;
import com.example.covid_19_system.StaticsController;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

public class UserDashboardController implements Initializable {
    @FXML
    public ImageView admin_menu;
    private boolean bool=true;
    @FXML
    private Button user_my_profile;
    @FXML
    private Button user_vacsines;
    @FXML
    private Button user_staff;
    @FXML
    private Button user_statistics;
    @FXML
    private ImageView user_image;
    @FXML
    private Pane line_profile;
    @FXML
    private Pane line_vacsines;
    @FXML
    private Pane line_injections;
    @FXML
    private Pane line_statistics;
    @FXML
    public Label citizen_name;
    @FXML
    public Pane main_page;
    private Citizen citizen;
    public StackPane ContentArea;

    public void setCitizen(Citizen citizen) {
        this.citizen = citizen;
        citizen_name.setText(citizen.getFirstName()+" "+citizen.getLastName());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        main_page.setOnMouseClicked(e->{
            Parent root = null;
            try {
                root = FXMLLoader.load(Objects.requireNonNull(HelloApplication.class.getResource("hello-view.fxml")));
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            Stage window = (Stage) main_page.getScene().getWindow();
            window.setTitle("Covid-19 vaccination system");
            window.setResizable(false);
            Scene secne=new Scene(Objects.requireNonNull(root), 1550, 800);
            secne.getStylesheets().add(String.valueOf(getClass().getResource("Style.css")));
            window.setScene(secne);
        });
        checkVisible(0);
    }


    public void UserMenu(MouseEvent mouseEvent) {
        new Timer().schedule(
                new TimerTask() {
                    @Override
                    public void run() {
                        admin_menu.setDisable(false);
                    }
                }, 1600
        );

        if (bool) {
            animationOn(user_my_profile,1);
            animationOn(user_vacsines,1.2);
            animationOn(user_staff,1.4);
            animationOn(user_statistics,1.6);
            bool = false;
            admin_menu.setDisable(true);
            Image image=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/menu.png")));
            admin_menu.setImage(image);
            checkVisible(0);
        } else {
            animationOff(user_my_profile,1);
            animationOff(user_vacsines,1.2);
            animationOff(user_staff,1.4);
            animationOff(user_statistics,1.6);
            bool = true;
            admin_menu.setDisable(true);
            Image image=new Image(Objects.requireNonNull(getClass().getResourceAsStream("/images/close.png")));
            admin_menu.setImage(image);
            checkVisible(0);
        }
    }

    public void MyProfile(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(HelloApplication.class.getResource("User/UserDashboardProfile.fxml"));
        Parent root = loader.load();
        UserDashboardProfileController ctrl = loader.getController();
        ctrl.setCitizen(citizen);
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
        checkVisible(1);
    }

    public void Vacsines(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(HelloApplication.class.getResource("User/UserDashboardVaccines.fxml"));
        Parent root = loader.load();
        UserDashboardVaccinesController ctrl = loader.getController();
        ctrl.setId(citizen.getPolyclinicId());
        ctrl.upload_data();
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
        checkVisible(2);

    }

    public void user_staff(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(HelloApplication.class.getResource("User/UserDashboardDoctors.fxml"));
        Parent root = loader.load();
        UserDashboardDoctorsController ctrl = loader.getController();
        ctrl.setId(citizen.getPolyclinicId());
        ctrl.upload_data();
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
        checkVisible(3);
    }

    public void statistics(ActionEvent actionEvent) throws IOException {
        FXMLLoader loader=new FXMLLoader(HelloApplication.class.getResource("statics.fxml"));
        Parent root = loader.load();
        StaticsController ctrl = loader.getController();
        ctrl.setPolyclinicId(citizen.getPolyclinicId());
        ctrl.uploadData();
        ctrl.updatePieChart(ctrl.levelSelector.getValue());
        ContentArea.getChildren().removeAll();
        ContentArea.getChildren().setAll(root);
        checkVisible(4);
    }



    public void animationOn(Button button, double a){
        FadeTransition fade = new FadeTransition(Duration.seconds(a), button);
        fade.setFromValue(1);
        fade.setToValue(0.2);
        TranslateTransition widthtranslate = new TranslateTransition(Duration.seconds(a), button);
        widthtranslate.setNode(button);
        widthtranslate.setByX(-358);
        ParallelTransition p = new ParallelTransition(fade, widthtranslate);
        p.play();
    }
    public void animationOff(Button button, double a){
        FadeTransition fade = new FadeTransition(Duration.seconds(a), button);
        fade.setFromValue(0.2);
        fade.setToValue(1);
        TranslateTransition widthtranslate = new TranslateTransition(Duration.seconds(a), button);
        widthtranslate.setNode(button);
        widthtranslate.setByX(358);
        ParallelTransition p = new ParallelTransition(fade, widthtranslate);
        p.play();
    }
    private void checkVisible(int a) {
        // Barcha buttonlardan "change_admin_button" sinfini olib tashlash
        user_my_profile.getStyleClass().remove("change_admin_button");
        user_vacsines.getStyleClass().remove("change_admin_button");
        user_staff.getStyleClass().remove("change_admin_button");
        user_statistics.getStyleClass().remove("change_admin_button");

        // Har bir buttonga asosiy "admin_button" sinfini o'rnatish
        user_my_profile.getStyleClass().add("admin_button");
        user_vacsines.getStyleClass().add("admin_button");
        user_staff.getStyleClass().add("admin_button");
        user_statistics.getStyleClass().add("admin_button");

        // Keyin tegishli buttonga "change_admin_button" sinfini qo'shamiz va animatsiyani o‘ynatamiz
        switch (a) {
            case 1:
                line_profile.setVisible(true);
                line_vacsines.setVisible(false);
                line_injections.setVisible(false);
                line_statistics.setVisible(false);
                user_my_profile.getStyleClass().add("change_admin_button");
                FadeTransition fade = new FadeTransition(Duration.seconds(0.7), line_profile);
                fade.setFromValue(0.2);
                fade.setToValue(1);
                fade.play();
                break;
            case 2:
                line_profile.setVisible(false);
                line_vacsines.setVisible(true);
                line_injections.setVisible(false);
                line_statistics.setVisible(false);
                user_vacsines.getStyleClass().add("change_admin_button");
                FadeTransition fade2 = new FadeTransition(Duration.seconds(0.7), line_vacsines);
                fade2.setFromValue(0.2);
                fade2.setToValue(1);
                fade2.play();
                break;
            case 3:
                line_profile.setVisible(false);
                line_vacsines.setVisible(false);
                line_injections.setVisible(true);
                line_statistics.setVisible(false);
                user_staff.getStyleClass().add("change_admin_button");
                FadeTransition fade3 = new FadeTransition(Duration.seconds(0.7), line_injections);
                fade3.setFromValue(0.2);
                fade3.setToValue(1);
                fade3.play();
                break;
            case 4:
                line_profile.setVisible(false);
                line_vacsines.setVisible(false);
                line_injections.setVisible(false);
                line_statistics.setVisible(true);
                user_statistics.getStyleClass().add("change_admin_button");
                FadeTransition fade4 = new FadeTransition(Duration.seconds(0.7), line_statistics);
                fade4.setFromValue(0.2);
                fade4.setToValue(1);
                fade4.play();
                break;
            case 0:
                line_profile.setVisible(false);
                line_vacsines.setVisible(false);
                line_injections.setVisible(false);
                line_statistics.setVisible(false);
                break;

        }

    }
}
