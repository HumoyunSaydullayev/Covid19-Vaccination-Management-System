package User;

import Admin.AdminDashboardController;
import Classes.Citizen;
import Classes.Database;
import com.example.covid_19_system.HelloApplication;
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
import java.util.Random;
import java.util.ResourceBundle;

public class UserLoginPageController implements Initializable {
    @FXML
    public TextField passport;
    @FXML
    public PasswordField captcha; // endi bu password emas, captcha kod kiritish uchun foydalaniladi
    @FXML
    public Label error_passport;
    @FXML
    public Label error_captcha;
    @FXML
    public HBox passport_hbox;
    @FXML
    public HBox jshir_hbox;
    @FXML
    public Button login;
    @FXML
    public Pane main_page;
    @FXML
    public Label code; // Captcha kodi ko'rsatish uchun label

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        passport.setText("");
        captcha.setText("");
        error_passport.setText("");
        error_captcha.setText("");
        Genaration(); // Dastlabki yuklashda captcha kodni generatsiya qilish

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
        code.setOnMouseClicked(e->{
            code.setText("");
            Genaration();
        });
    }

    public void Clear(ActionEvent actionEvent) {
        passport.setText("");
        captcha.setText("");
        error_passport.setText("");
        error_captcha.setText("");
        Genaration(); // Tozalanganda ham yangi captcha generatsiya qilamiz
    }

    public void Login(ActionEvent actionEvent) throws IOException {

        boolean bool = true;
        if (passport.getText().isEmpty() && captcha.getText().isEmpty()) {
            showErrorAnimation(passport_hbox);
            showErrorAnimation(jshir_hbox);

            error_passport.setTextFill(Paint.valueOf("#FA2C56"));
            error_captcha.setTextFill(Paint.valueOf("#FA2C56"));
            error_passport.setText("*Passport seria and number is empty!!!");
            error_captcha.setText("*Captcha is empty!!!");
            bool = false;
        } else if (passport.getText().isEmpty()) {
            showErrorAnimation(passport_hbox);

            error_captcha.setText("");
            error_passport.setTextFill(Paint.valueOf("#FA2C56"));
            error_passport.setText("*Passport seria and number is empty!!!");
            bool = false;
        } else if (captcha.getText().isEmpty()) {
            showErrorAnimation(jshir_hbox);

            error_passport.setText("");
            error_captcha.setTextFill(Paint.valueOf("#FA2C56"));
            error_captcha.setText("*Captcha is empty!!!");
            bool = false;
        }

        if (bool) {
            String enteredPassport = passport.getText();
            String enteredCaptcha = captcha.getText();

            Database db = new Database();
            boolean isValidUsername = db.validateCitizenPassport(enteredPassport);

            if (!isValidUsername) {
                error_passport.setTextFill(Paint.valueOf("#FA2C56"));
                error_passport.setText("*Invalid passport!");
            } else if (!enteredCaptcha.equals(code.getText())) {
                // Captcha noto'g'ri kiritildi
                error_captcha.setTextFill(Paint.valueOf("#FA2C56"));
                error_captcha.setText("*Invalid captcha!");
                Genaration(); // Yangi captcha generatsiya qilamiz
            } else {
                Citizen data = db.getCitizenData(enteredPassport);

                FXMLLoader loader = new FXMLLoader(HelloApplication.class.getResource("User/UserDashboard.fxml"));
                Parent root = loader.load();
                UserDashboardController ctrl = loader.getController();
                ctrl.setCitizen(data);
                Stage newStage = (Stage) login.getScene().getWindow();
                newStage.setScene(new Scene(root, 1550, 800));
                newStage.setTitle("User");
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

    public void Genaration(){
        StringBuilder soz= new StringBuilder();
        String[] harflar={"A","B","C","D","E","F","G","I","H","J","K","L","Z","X","C","V","N","M",
                "Q","W","R","T","Y","U","O","P",
                "a","b","c","d","e","f","g","h","i","j","k","l","z","x","c","v","n","m","q","w","r","t","y","u","o","p"};
        Random rand = new Random();
        for (int i = 0; i < 4; i++) {
            int son = rand.nextInt(harflar.length);
            soz.append(harflar[son]);
        }
        code.setText(soz.toString());
    }
}
