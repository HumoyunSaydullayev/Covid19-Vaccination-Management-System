package Classes;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    private Connection connection;

    // Constructor to establish database connection
    public Database() {
        try {
            String url = "jdbc:postgresql://localhost:5432/Covid"; // Replace with your database URL
            String username = "postgres"; // Replace with your DB username
            String password = "humoyun_1504"; // Replace with your DB password
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





    // Validate Admin Username
    public boolean validateAdminUsername(String username) {
        try {
            String query = "SELECT Polyclinic_Admins_validate_username(?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean(1); // True yoki False qaytaradi
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Parolni foydalanuvchi nomi asosida tekshirish
    public boolean validateAdminPassword(String username, String password) {
        try {
            String query = "SELECT Polyclinic_Admins_validate_password(?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                return resultSet.getBoolean(1); // True yoki False qaytaradi
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    // Get Admin Data
    public Polyclinic_Admins getAdminData(String username, String plainPassword) {
        if (!validateAdminPassword(username, plainPassword)) {
            return null;
        }

        try {
            String query = "SELECT * FROM get_Polyclinic_Admins_data(?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, plainPassword); // If get_Polyclinic_Admins_data expects plain password, else modify accordingly

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                // Map ResultSet to Polyclinic_Admins object
                return new Polyclinic_Admins(
                        resultSet.getInt("out_polyclinic_admin_id"),
                        resultSet.getString("out_username"),
                        resultSet.getString("out_password"),
                        resultSet.getString("out_name"),
                        resultSet.getString("out_surname"),
                        resultSet.getString("out_contact_info"),
                        resultSet.getInt("out_polyclinic_id")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // If no data found
    }

    // Change Admin Password
    public boolean changeAdminPassword(int adminId, String newPassword) {
        String query = "SELECT update_polyclinic_admin_password(?, ?);";

        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            // Parametrlarni o'rnatish
            pstmt.setInt(1, adminId);          // Admin ID
            pstmt.setString(2, newPassword);  // Yangi parol

            // Funksiya natijasini olish
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return rs.getBoolean(1); // Funksiyaning qaytgan qiymatini o'qish
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Xatolik bo'lsa, false qaytaradi
    }
    // Validate Citizen Passport
    public boolean validateCitizenPassport(String passportNumber) {
        String query = "SELECT 1 FROM Citizens WHERE passport_number = ? LIMIT 1";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, passportNumber);
            ResultSet resultSet = statement.executeQuery();
            return resultSet.next(); // True if exists
        } catch (Exception e) {
            e.printStackTrace();
            return false; // On error
        }
    }

    // Get Citizen Data
    public Citizen getCitizenData(String passportNumber) {
        String query = "SELECT * FROM get_citizen_data(?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, passportNumber);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    // Extract data from ResultSet
                    int citizenId = resultSet.getInt("out_citizen_id");
                    String firstName = resultSet.getString("out_first_name");
                    String lastName = resultSet.getString("out_last_name");
                    java.sql.Date sqlDate = resultSet.getDate("out_birth_date");
                    java.util.Date birthDate = new java.util.Date(sqlDate.getTime());
                    String outPassportNumber = resultSet.getString("out_passport_number");
                    int polyclinicId = resultSet.getInt("out_polyclinic_id");
                    String vaccineName = resultSet.getString("vaccine_name");
                    int timesVaccinated = resultSet.getInt("times_vaccinated");
                    int remainingDoses = resultSet.getInt("remaining_doses");
                    java.sql.Date lastVaccinationDate = resultSet.getDate("last_vaccination_date");
                    java.sql.Date nextVaccinationDate = resultSet.getDate("next_vaccination_date");
                    boolean vaccinationStatus = resultSet.getBoolean("vaccination_status");

                    // Debugging/logging output (optional)
                    System.out.println("Citizen ID: " + citizenId);
                    System.out.println("Name: " + firstName + " " + lastName);
                    System.out.println("Vaccine: " + vaccineName);
                    System.out.println("Vaccinated: " + timesVaccinated);
                    System.out.println("Remaining Doses: " + remainingDoses);
                    System.out.println("Last Vaccination: " + lastVaccinationDate);
                    System.out.println("Next Vaccination: " + nextVaccinationDate);
                    System.out.println("Fully Vaccinated: " + vaccinationStatus);

                    // Create and return the Citizen object
                    return new Citizen(
                            citizenId,
                            firstName,
                            lastName,
                            birthDate,
                            outPassportNumber,
                            polyclinicId,
                            vaccineName,
                            timesVaccinated,
                            remainingDoses,
                            lastVaccinationDate != null ? new java.util.Date(lastVaccinationDate.getTime()) : null,
                            nextVaccinationDate != null ? new java.util.Date(nextVaccinationDate.getTime()) : null,
                            vaccinationStatus
                    );
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null; // Return null if no record is found or an error occurs
    }


    public ArrayList<Staff> getStaffDetailsWithRatingByPolyclinic(int polyclinicId) {
        ArrayList<Staff> staffList = new ArrayList<>();
        String query = "SELECT * FROM get_staff_details_with_rating_by_polyclinic(?);";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, polyclinicId); // Set Polyclinic ID

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int staffId = rs.getInt("staff_id");
                    String firstName = rs.getString("first_name");
                    String lastName = rs.getString("last_name");
                    String specialization = rs.getString("specialization");
                    int rating = rs.getInt("rating");

                    Staff staff = new Staff(staffId, firstName, lastName, specialization, rating);
                    staffList.add(staff);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return staffList;
    }

    // Delete Polyclinic Vaccine
    public boolean deletePolyclinicVaccine(int id) {
        String query = "DELETE FROM Polyclinic_Vaccines WHERE polyclinic_vaccine_id = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setInt(1, id);
            int affectedRows = statement.executeUpdate();
            return affectedRows > 0; // True if at least one row deleted
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // On error
        }
    }

    // Get Vaccines by Polyclinic
    public ArrayList<Vaccines> getVaccinesByPolyclinic(int polyclinicId) {
        ArrayList<Vaccines> vaccineList = new ArrayList<>();
        String query = "SELECT * FROM get_vaccines_by_polyclinic(?)";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setInt(1, polyclinicId); // Set Polyclinic ID

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("polyclinic_vaccine_id");
                    String vaccineName = rs.getString("vaccine_name");
                    int numberOfReceptions = rs.getInt("number_of_receptions");
                    String about = rs.getString("about");
                    int receptionRange = rs.getInt("reception_range");
                    int availableQuantity = rs.getInt("available_quantity");

                    vaccineList.add(new Vaccines(id, vaccineName, availableQuantity, numberOfReceptions, about, receptionRange));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vaccineList;
    }

    // Get Rating Info
    public ArrayList<Rating> getRating() {
        ArrayList<Rating> ratingList = new ArrayList<>();
        String query = "SELECT * FROM get_rating_info()";

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    String polyclinicName = rs.getString("polyclinic_name");
                    int recoveredPatients = rs.getInt("recover_of_patients");
                    int recoveringPatients = rs.getInt("recovering_of_patients");

                    // Add the mapped object to the list
                    ratingList.add(new Rating(polyclinicName, recoveredPatients, recoveringPatients));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return ratingList; // Return the list of ratings
    }

    // Get Vaccination Status by Polyclinic
    public VaccinationStatus getVaccinationStatusByPolyclinic(int polyclinicId) {
        String sql = "SELECT * FROM get_vaccination_status_by_polyclinic(?);";
        VaccinationStatus result = null;

        try (PreparedStatement pstmt = connection.prepareStatement(sql)) {
            pstmt.setInt(1, polyclinicId);

            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int polyclinicEmlangan = rs.getInt("polyclinic_emlangan");
                    int polyclinicEmlanayotgan = rs.getInt("polyclinic_emlanayotgan");
                    int polyclinicEmlanmagan = rs.getInt("polyclinic_emlanmagan");

                    int districtEmlangan = rs.getInt("district_emlangan");
                    int districtEmlanayotgan = rs.getInt("district_emlanayotgan");
                    int districtEmlanmagan = rs.getInt("district_emlanmagan");

                    int regionEmlangan = rs.getInt("region_emlangan");
                    int regionEmlanayotgan = rs.getInt("region_emlanayotgan");
                    int regionEmlanmagan = rs.getInt("region_emlanmagan");
                    String polyclinicName = rs.getString("polyclinic_name");
                    String districtName = rs.getString("district_name");
                    String regionName = rs.getString("region_name");

                    result = new VaccinationStatus(
                            polyclinicEmlangan,
                            polyclinicEmlanayotgan,
                            polyclinicEmlanmagan,
                            districtEmlangan,
                            districtEmlanayotgan,
                            districtEmlanmagan,
                            regionEmlangan,
                            regionEmlanayotgan,
                            regionEmlanmagan,
                            polyclinicName,
                            districtName,
                            regionName
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return result;
    }

    // Close Database Connection
    public void closeConnection() {
        try {
            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Additional Methods
// Update the available quantity of an existing vaccine in a polyclinic
    // Check if a vaccine with the given name exists in the specified polyclinic
    public Vaccines getVaccineByNameAndPolyclinic(String vaccineName, int polyclinicId) {
        String query = "SELECT * FROM Polyclinic_Vaccines pv " +
                "JOIN Vaccines v ON pv.vaccine_id = v.vaccine_id " +
                "WHERE v.name = ? AND pv.polyclinic_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, vaccineName);
            pstmt.setInt(2, polyclinicId);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("polyclinic_vaccine_id");
                    String name = rs.getString("name");
                    int availableQuantity = rs.getInt("stock");
                    int numberOfReceptions = rs.getInt("number_of_receptions");
                    String about = rs.getString("about");
                    int receptionRange = rs.getInt("reception_range");
                    return new Vaccines(id, name, availableQuantity, numberOfReceptions, about, receptionRange);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; // Vaccine does not exist
    }


    // Update Vaccine Stock
    // Update the available quantity of an existing vaccine in a polyclinic
    public boolean updateVaccineQuantity(int polyclinicVaccineId, int additionalQuantity) {
        String updateQuery = "UPDATE Polyclinic_Vaccines SET stock = stock + ? WHERE polyclinic_vaccine_id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(updateQuery)) {
            pstmt.setInt(1, additionalQuantity);
            pstmt.setInt(2, polyclinicVaccineId);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    // Add New Vaccine to Polyclinic
    // Insert a new vaccine into a polyclinic
    public boolean addVaccineToPolyclinic(String vaccineName, int polyclinicId, int availableQuantity) {
        // First, retrieve the vaccine ID from the Vaccines table
        String getVaccineIdQuery = "SELECT vaccine_id FROM Vaccines WHERE name = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(getVaccineIdQuery)) {
            pstmt.setString(1, vaccineName);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    int vaccineId = rs.getInt("vaccine_id");
                    // Now, insert into Polyclinic_Vaccines
                    String insertQuery = "INSERT INTO Polyclinic_Vaccines (polyclinic_id, vaccine_id, stock) VALUES (?, ?, ?)";
                    try (PreparedStatement insertPstmt = connection.prepareStatement(insertQuery)) {
                        insertPstmt.setInt(1, polyclinicId);
                        insertPstmt.setInt(2, vaccineId);
                        insertPstmt.setInt(3, availableQuantity);
                        int affectedRows = insertPstmt.executeUpdate();
                        return affectedRows > 0;
                    }
                } else {
                    // Vaccine does not exist in Vaccines table
                    // Optionally, you can handle this case by adding the vaccine to Vaccines table first
                    return false;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Add Vaccination Process
    public boolean addVaccinationProcess(int citizenId, int vaccineId, int staffMemberId, Timestamp vaccinationDatetime, Date vaccinationDate) {
        String query = "INSERT INTO Vaccination_Processes (citizen_id, vaccine_id, staff_member_id, vaccination_datetime, vaccination_date) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, citizenId);
            pstmt.setInt(2, vaccineId);
            pstmt.setInt(3, staffMemberId);
            pstmt.setTimestamp(4, vaccinationDatetime);
            pstmt.setDate(5, vaccinationDate);
            int affectedRows = pstmt.executeUpdate();
            return affectedRows > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
