package User;

import Classes.Citizen;
import Classes.Database;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserDashboardProfileController implements Initializable {
    @FXML
    public Label passport;
    public Label fish;
    public Label birth_date;
    public Label vaccinationstatus;
    public Label vaccinetype;
    public Label Number_of_vaccination;
    public Label Last_vaccination;
    public Citizen citizen;

    public Button Certificate;
    Database db = new Database();

    /**
     * Fuqaroni o'rnatish va UI elementlarini yangilash.
     *
     * @param citizen Fuqaroni obyekti
     */
    public void setCitizen(Citizen citizen){
        this.citizen = citizen;
        fish.setText(citizen.getFirstName() + " " + citizen.getLastName());
        passport.setText(citizen.getPassportNumber());
        birth_date.setText(String.valueOf(citizen.getBirthDate()));

        if (citizen.isVaccinationStatus()){
            vaccinationstatus.setText("Siz to'liq emlangan ekansiz.");
            Certificate.setVisible(true); // Tugmani ko'rsatish
        } else {
            vaccinationstatus.setText("Siz hali to'liq emlanmagansiz.");
            Certificate.setVisible(false); // Tugmani yashirish
        }

        // Agar sizda vaksinaga oid boshqa ma'lumotlar bo'lsa, ularni ham shu yerda sozlang
        vaccinetype.setText(citizen.getVaccineName());
        Number_of_vaccination.setText(String.valueOf(citizen.getTimesVaccinated()));
        Last_vaccination.setText(String.valueOf(citizen.getLastVaccinationDate()));
        // Misol uchun
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Dastlab tugmani yashirish
        Certificate.setVisible(false);
    }

    /**
     * Sertifikatni yaratish va Downloads papkasiga saqlash.
     *
     * @param actionEvent ActionEvent
     */
    public void Certificate(ActionEvent actionEvent) throws FileNotFoundException, DocumentException {
        if (citizen == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xato");
            alert.setHeaderText("Fuqaroni tanlanmagan");
            alert.setContentText("Iltimos, sertifikatni yaratish uchun fuqaroni tanlang.");
            alert.showAndWait();
            return;
        }

        try {
            // Foydalanuvchining uy katalogini olish
            String userHome = System.getProperty("user.home");
            String downloadsPath = userHome + System.getProperty("file.separator") + "Downloads" + System.getProperty("file.separator");
            String fileName = downloadsPath + citizen.getPassportNumber() + ".pdf";

            // PDF hujjatini yaratish
            Document document = new Document(PageSize.A4, 50, 50, 50, 50);
            PdfWriter.getInstance(document, new FileOutputStream(fileName));
            document.open();

            // Logotip qo'shish (resurslardan yuklash)
            try {
                // Resurslardan logotipni yuklash
                Image logo = Image.getInstance(Objects.requireNonNull(getClass().getResource("/images/injection.png")));
                logo.scaleToFit(100, 100);
                logo.setAlignment(Element.ALIGN_CENTER);
                document.add(logo);
            } catch (Exception e) {
                // Logotip topilmasa, xatolik chiqarmaslik uchun
                e.printStackTrace();
            }

            // Sertifikat sarlavhasi
            Font titleFont = new Font(Font.FontFamily.TIMES_ROMAN, 18, Font.BOLD);
            Paragraph title = new Paragraph("Emlanganlik To'g'risidagi Sertifikat", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            title.setSpacingAfter(20);
            document.add(title);

            // Sertifikat ma'lumotlari uchun jadval
            PdfPTable table = new PdfPTable(2);
            table.setWidthPercentage(80);
            table.setSpacingBefore(10f);
            table.setSpacingAfter(10f);
            table.setHorizontalAlignment(Element.ALIGN_CENTER);

            // Jadval ustunlari kengligini belgilash
            float[] columnWidths = {2f, 5f};
            table.setWidths(columnWidths);

            // Qator 1: Passport Seriyasi
            Font labelFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            PdfPCell labelCell1 = new PdfPCell(new Phrase("Passport Seriyasi:", labelFont));
            labelCell1.setBorder(Rectangle.NO_BORDER);
            labelCell1.setPadding(5);
            table.addCell(labelCell1);

            PdfPCell valueCell1 = new PdfPCell(new Phrase(citizen.getPassportNumber()));
            valueCell1.setBorder(Rectangle.NO_BORDER);
            valueCell1.setPadding(5);
            table.addCell(valueCell1);

            // Qator 2: To'liq Ismi
            PdfPCell labelCell2 = new PdfPCell(new Phrase("To'liq Ismi:", labelFont));
            labelCell2.setBorder(Rectangle.NO_BORDER);
            labelCell2.setPadding(5);
            table.addCell(labelCell2);

            PdfPCell valueCell2 = new PdfPCell(new Phrase(citizen.getFirstName() + " " + citizen.getLastName()));
            valueCell2.setBorder(Rectangle.NO_BORDER);
            valueCell2.setPadding(5);
            table.addCell(valueCell2);

            // Qator 3: Tug'ulgan Sanasi
            PdfPCell labelCell3 = new PdfPCell(new Phrase("Tug'ulgan Sanasi:", labelFont));
            labelCell3.setBorder(Rectangle.NO_BORDER);
            labelCell3.setPadding(5);
            table.addCell(labelCell3);

            PdfPCell valueCell3 = new PdfPCell(new Phrase(String.valueOf(citizen.getBirthDate())));
            valueCell3.setBorder(Rectangle.NO_BORDER);
            valueCell3.setPadding(5);
            table.addCell(valueCell3);

            // Qator 4: Qabul Qilgan Vaksina Nomi
            PdfPCell labelCell4 = new PdfPCell(new Phrase("Qabul Qilgan Vaksina Nomi:", labelFont));
            labelCell4.setBorder(Rectangle.NO_BORDER);
            labelCell4.setPadding(5);
            table.addCell(labelCell4);

            PdfPCell valueCell4 = new PdfPCell(new Phrase(vaccinetype.getText()));
            valueCell4.setBorder(Rectangle.NO_BORDER);
            valueCell4.setPadding(5);
            table.addCell(valueCell4);

            document.add(table);

            // Sertifikat matni
            Font bodyFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.NORMAL);
            Paragraph body = new Paragraph("To'liq emlanganligingiz uchun O'zbekiston Respublikasi sog'liqni saqlash vazirligi nomidan minnatdorchilik bildiramiz.", bodyFont);
            body.setAlignment(Element.ALIGN_CENTER);
            body.setSpacingBefore(20);
            document.add(body);

            // Sertifikatning pastki qismi
            Font footerFont = new Font(Font.FontFamily.TIMES_ROMAN, 12, Font.BOLD);
            Paragraph footer = new Paragraph("______________________\nSog'liqni saqlash Vazirligi Vakili", footerFont);
            footer.setAlignment(Element.ALIGN_RIGHT);
            footer.setSpacingBefore(50);
            document.add(footer);

            document.close();

            // Foydalanuvchiga muvaffaqiyat haqida bildirishnoma ko'rsatish
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Muvaffaqiyat");
            alert.setHeaderText("Sertifikat yaratildi");
            alert.setContentText("Sizning emlanganlik sertifikatingiz " + downloadsPath + "papkasiga saqlandi.");
            alert.showAndWait();
        }
        catch (Exception e){
            e.printStackTrace();
            // Xatolik yuz bersa, foydalanuvchiga bildirishnoma ko'rsatish
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Xato");
            alert.setHeaderText("Sertifikat yaratishda xatolik");
            alert.setContentText("Sertifikatni yaratishda xatolik yuz berdi. Iltimos, qayta urinib ko'ring.");
            alert.showAndWait();
        }
    }
}
