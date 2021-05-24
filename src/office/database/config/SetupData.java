package office.database.config;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupData {

    public void setup(){
        createNurseTable();
        createFamilyDoctorTable();
        createPediatricianTable();
        createAdultTable();
        createChildTable();
        createAppointmentTable();
    }

    private void createAppointmentTable() {
        String query = "CREATE TABLE IF NOT EXISTS appointments (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    patient_id INT,\n" +
                "    doctor_id INT,\n" +
                "    time DATETIME,\n" +
                "    status BIT\n" +
//                "    FOREIGN KEY (parent_name) REFERENCES adults(lastname) ON DELETE CASCADE\n" +
                ")";
        // aici foreign key poate fi din mai multe tabele diferite si am ales sa nu pun nicio constrangere

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createChildTable() {
        String query = "CREATE TABLE IF NOT EXISTS children (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    lastname VARCHAR(25),\n" +
                "    firstname VARCHAR(25),\n" +
                "    birth_year INT,\n" +
                "    CNP VARCHAR(25),\n" +
                "    tel VARCHAR(25),\n" +
                "    parent_id INT,\n" +
                "    FOREIGN KEY (parent_id) REFERENCES adults(id) ON DELETE CASCADE\n" +
                ")";
        // la map o sa modific sa fie in clasa numele, dar altfel aici nu mergea

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createAdultTable() {
        String query = "CREATE TABLE IF NOT EXISTS adults (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    lastname VARCHAR(25),\n" +
                "    firstname VARCHAR(25),\n" +
                "    birth_year INT,\n" +
                "    CNP VARCHAR(25),\n" +
                "    tel VARCHAR(25),\n" +
                "    health_insurance BIT\n" +
                ")";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createPediatricianTable() {
        String query = "CREATE TABLE IF NOT EXISTS pediatricians (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    lastname VARCHAR(25),\n" +
                "    firstname VARCHAR(25),\n" +
                "    email VARCHAR(50),\n" +
                "    hire_year INT,\n" +
                "    bonus INT\n" +
                ")";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createFamilyDoctorTable() {
        String query = "CREATE TABLE IF NOT EXISTS family_doctors (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    lastname VARCHAR(25),\n" +
                "    firstname VARCHAR(25),\n" +
                "    email VARCHAR(50),\n" +
                "    hire_year INT,\n" +
                "    no_of_families INT\n" +
                ")";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createNurseTable() {
        String query = "CREATE TABLE IF NOT EXISTS nurses (\n" +
                "    id INT PRIMARY KEY AUTO_INCREMENT,\n" +
                "    lastname VARCHAR(25),\n" +
                "    firstname VARCHAR(25),\n" +
                "    email VARCHAR(50),\n" +
                "    hire_year INT,\n" +
                "    hours INT\n" +
                ")";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
