package Admin;

import Classes.Database;
import com.example.covid_19_system.HelloApplication;
import Classes.Polyclinic_Admins;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class AdminLoginPageController implements Initializable {
    @FXML
    public TextField username;
    @FXML
    public PasswordField password;
    @FXML
    public Label error_username;
    @FXML
    public Label error_password;
    @FXML
    public HBox user_hbox;
    @FXML
    public HBox password_hbox;
    @FXML
    public Button login;
    @FXML
    public Pane main_page;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        username.setText("");
        password.setText("");
        error_username.setText("");
        error_password.setText("");
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
    }

    public void Clear(ActionEvent actionEvent) {
        username.setText("");
        password.setText("");
        error_username.setText("");
        error_password.setText("");
    }

    public void Login(ActionEvent actionEvent) throws IOException {

        boolean bool = true;
        if (username.getText().isEmpty() && password.getText().isEmpty()) {
            showErrorAnimation(user_hbox);
            showErrorAnimation(password_hbox);

            error_username.setTextFill(Paint.valueOf("#FA2C56"));
            error_password.setTextFill(Paint.valueOf("#FA2C56"));
            error_username.setText("*Username is empyt !!!");
            error_password.setText("*Password is empyt !!!");
            bool = false;
        } else if (username.getText().isEmpty()) {
            showErrorAnimation(user_hbox);

            error_password.setText("");
            error_username.setTextFill(Paint.valueOf("#FA2C56"));
            error_username.setText("*Username is empyt !!! !!!");
            bool = false;
        } else if (password.getText().isEmpty()) {
            showErrorAnimation(password_hbox);

            error_username.setText("");
            error_password.setTextFill(Paint.valueOf("#FA2C56"));
            error_password.setText("*Password is empyt !!!");
            bool = false;
        }
        if (bool) {
            String enteredUsername = username.getText();
            String enteredPassword = password.getText();

            Database db = new Database();
            boolean isValidUsername = db.validateAdminUsername(enteredUsername);
            boolean isValidPassword = db.validateAdminPassword(enteredUsername, enteredPassword);

            if (!isValidUsername) {
                error_username.setTextFill(Paint.valueOf("#FA2C56"));
                error_username.setText("*Invalid username!");
            } else if (!isValidPassword) {
                error_password.setTextFill(Paint.valueOf("#FA2C56"));
                error_password.setText("*Invalid password!");
            } else {
                Polyclinic_Admins admin = db.getAdminData(enteredUsername, enteredPassword);

                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("Admin/AdminDashboard.fxml"));
                Parent root = loader.load();
                AdminDashboardController ctrl = loader.getController();
                ctrl.setadmin(admin);
                Stage newStage = (Stage) login.getScene().getWindow();
                newStage.setScene(new Scene(root, 1550, 800));
                newStage.setTitle("Admin");
                newStage.show();
            }


        }
    }

    private void showErrorAnimation(Node node) {
        TranslateTransition transition = new TranslateTransition(Duration.millis(50), node);
        transition.setFromX(0);
        transition.setByX(20); // Ongga siljish
        transition.setCycleCount(4); // 2 marta chapga va 2 marta o'ngga
        transition.setAutoReverse(true);
        transition.play();
    }
}
