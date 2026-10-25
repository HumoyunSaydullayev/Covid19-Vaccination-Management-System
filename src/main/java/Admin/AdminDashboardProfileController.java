package Admin;

import Classes.Database;
import Classes.Polyclinic_Admins;
import javafx.animation.FadeTransition;
import javafx.animation.ParallelTransition;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.util.Duration;

import javax.swing.*;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminDashboardProfileController implements Initializable {
    @FXML
    public Label fish;
    @FXML
    public Label contact;
    @FXML
    public Label username;
    @FXML
    public Label password;
    @FXML
    public Button change_password;
    @FXML
    public Pane change_pane;
    @FXML
    public TextField current_password;
    @FXML
    public TextField new_password;
    @FXML
    public TextField repeat_new_password;
    @FXML
    public Button save_password;
    @FXML
    public Label error;
    public Polyclinic_Admins admin;
    Database db= new Database();
    public void setadmin(Polyclinic_Admins admin){
        this.admin = admin;
        fish.setText(admin.getName()+" "+admin.getSurname());
        contact.setText(admin.getContactInfo());
        username.setText(admin.getUsername());
        password.setText(admin.getPassword());
    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
    public void cancel(ActionEvent actionEvent) {

        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), change_pane);
        fade.setFromValue(1);
        fade.setToValue(0.5);

        TranslateTransition widthtranslate = new TranslateTransition(Duration.seconds(0.5), change_pane);
        widthtranslate.setByY(300);
        current_password.setText(admin.getPassword());
        ParallelTransition p = new ParallelTransition(fade, widthtranslate);
        p.play();
        change_pane.setVisible(false);
    }
    public void clear(ActionEvent actionEvent) {
        new_password.setText("");
        repeat_new_password.setText("");
        error.setText("");
    }
    public void refresh(String enteredUsername, String enteredPassword){
        Polyclinic_Admins admin = db.getAdminData(enteredUsername, enteredPassword);
        fish.setText(admin.getName()+" "+admin.getSurname());
        contact.setText(admin.getContactInfo());
        username.setText(admin.getUsername());
        password.setText(admin.getPassword());
    }

    public void change_password(ActionEvent actionEvent) {
        change_pane.setVisible(true);
        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), change_pane);
        fade.setFromValue(0.5);
        fade.setToValue(1);

        TranslateTransition widthtranslate = new TranslateTransition(Duration.seconds(0.5), change_pane);
        widthtranslate.setByY(-300);
        current_password.setText(admin.getPassword());
        ParallelTransition p = new ParallelTransition(fade, widthtranslate);
        p.play();
    }
    public void save_password(ActionEvent actionEvent) {
        if (new_password.getText().isEmpty()) {
            error.setText("*New password field is empty!");
        } else if (repeat_new_password.getText().isEmpty()) {
            error.setText("*Repeat new password field is empty!");
        } else if (new_password.getText().length() < 8 || repeat_new_password.getText().length() < 8) {
            error.setText("*New password must be at least 8 characters long!");
        } else if (!new_password.getText().equals(repeat_new_password.getText())) {
            error.setText("*The new password and repeat new password fields must be the same!");
        } else if (new_password.getText().equals(admin.getPassword())) {
            error.setText("*The new password must not be the same as the current password!");
        }else{

            boolean isUpdated = db.changeAdminPassword(1, new_password.getText());
            if (isUpdated) {
                change_pane.setVisible(false);
                FadeTransition fade = new FadeTransition(Duration.seconds(0.5), change_pane);
                fade.setFromValue(0.5);
                fade.setToValue(1);

                TranslateTransition widthtranslate = new TranslateTransition(Duration.seconds(0.5), change_pane);
                widthtranslate.setByY(300);
                current_password.setText(admin.getPassword());
                ParallelTransition p = new ParallelTransition(fade, widthtranslate);
                p.play();
                // Parol muvaffaqiyatli yangilangan xabarni ko‘rsatish
                JOptionPane.showMessageDialog(null,
                        "Password successfully updated!",
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE);
                new_password.setText("");
                repeat_new_password.setText("");
                refresh(admin.getUsername(),new_password.getText());
            } else {
                FadeTransition fade = new FadeTransition(Duration.seconds(0.5), change_pane);
                fade.setFromValue(0.5);
                fade.setToValue(1);

                TranslateTransition widthtranslate = new TranslateTransition(Duration.seconds(0.5), change_pane);
                widthtranslate.setByY(300);
                current_password.setText(admin.getPassword());
                ParallelTransition p = new ParallelTransition(fade, widthtranslate);
                p.play();
                // Parol yangilash muvaffaqiyatsiz bo‘lgan xabarni ko‘rsatish
                JOptionPane.showMessageDialog(null,
                        "Failed to update password. Please try again.",
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }

        }
    }

}
