# 💉 Covid-19 Healthcare & Vaccination Management Platform

<p align="center">
  <img src="https://img.shields.io/badge/Language-Java%2017+-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white" alt="Java" />
  <img src="https://img.shields.io/badge/GUI-JavaFX-blue?style=for-the-badge" alt="JavaFX" />
  <img src="https://img.shields.io/badge/Database-PostgreSQL-4169E1?style=for-the-badge&logo=postgresql&logoColor=white" alt="PostgreSQL" />
  <img src="https://img.shields.io/badge/Reporting-iTextPDF-FF6F00?style=for-the-badge" alt="iTextPDF" />
  <img src="https://img.shields.io/badge/Build-Maven-C71A36?style=for-the-badge&logo=apachemaven&logoColor=white" alt="Maven" />
  <img src="https://img.shields.io/badge/License-MIT-green?style=for-the-badge" alt="License" />
</p>

---

## 📌 Overview / Loyiha haqida

### English
**Covid-19 Vaccination Management Platform** is an enterprise-grade desktop management system developed with **JavaFX** and backed by **PostgreSQL**. Designed for healthcare facilities and vaccination centers, it provides role-based access for healthcare administrators and citizens. Key capabilities include automated vaccination tracking, healthcare staff records, polyclinic administration, and real-time generation of official vaccination certificates via **iTextPDF**.

### O‘zbekcha
**Covid-19 Vaccination Management Platform** — bu sog'liqni saqlash muassasalari va poliklinikalar uchun mo'ljallangan, **JavaFX** va **PostgreSQL** ma'lumotlar bazasi asosida qurilgan to'liq desktop tizim. Tizim tibbiyot ma'murlari va fuqarolar uchun alohida kirish huquqlarini beradi. Asosiy imkoniyatlar: vaktsina dozalari hisobi, shifokorlar va xodimlar ro'yxati, poliklinikalar statistikasi hamda fuqarolar uchun **iTextPDF** yordamida rasmiy emlash sertifikatlarini avtomatik PDF formatida chiqarib berish.

---

## 🚀 Key Modules / Asosiy modullar

### 👨‍⚕️ Admin & Healthcare Operations
- **Admin Dashboard:** Overview of vaccination campaigns, supplies, and polyclinic activity.
- **Vaccine Registry:** Batch addition, expiration tracking, stock monitoring (`AdminDashboardVaccinesController`).
- **Injection Monitoring:** Real-time recording of administered doses per citizen (`AdminDashboardInjectionsController`).
- **Automated Certificate Generation:** Instant creation of signed vaccination certificates directly to PDF.

### 👤 Citizen / User Portal
- **User Dashboard:** Patient personal health profile and vaccine status verification (`UserDashboardController`).
- **Doctor & Staff Directory:** Information about certified medical staff on duty (`UserDashboardDoctorsController`).
- **Feedback & Rating System:** Citizens can rate vaccination experience and vaccine efficiency (`Rating`, `ratingvaccine`).

---

## 📁 Project Architecture / Arxitektura

```text
src/main/java/
├── Admin/                              # Healthcare Admin Controllers
│   ├── AdminDashboardController.java
│   ├── AdminDashboardInjectionsController.java
│   ├── AdminDashboardProfileController.java
│   ├── AdminDashboardVaccinesController.java
│   └── AdminLoginPageController.java
├── Classes/                            # Data Models & Entities
│   ├── Citizen.java
│   ├── Database.java                   # PostgreSQL Connection Manager
│   ├── Polyclinic_Admins.java
│   ├── Rating.java
│   ├── Staff.java
│   ├── VaccinationStatus.java
│   └── Vaccines.java
├── User/                               # Citizen Portal Controllers
│   ├── UserDashboardController.java
│   ├── UserDashboardDoctorsController.java
│   ├── UserDashboardProfileController.java
│   ├── UserDashboardVaccinesController.java
│   └── UserLoginPageController.java
└── HelloApplication.java              # JavaFX Entry Point
```

---

## 🛠️ Setup & Running

1. **Database Setup:**
   - Install **PostgreSQL**.
   - Create a database (e.g. `covid_db`) and configure credentials in `src/main/java/Classes/Database.java`.
2. **Build and Run with Maven:**
   ```bash
   git clone https://github.com/HumoyunSaydullayev/Covid19-Vaccination-Management-System.git
   cd Covid19-Vaccination-Management-System
   mvn clean javafx:run
   ```

---

## 👨‍💻 Author
**Humoyun Saydullayev**  
Master's Degree Project in Data Management - KIUT  
GitHub: [@HumoyunSaydullayev](https://github.com/HumoyunSaydullayev)
