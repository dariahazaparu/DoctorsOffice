package office.database.config;

import office.doctor.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class SetupData {

    public void setup(){
        createNurseTable();
        createUpdateNurse();

        createFamilyDoctorTable();
        createUpdateFamilyDoctor();

        createPediatricianTable();
        createUpdatePediatrician();

        createAdultTable();
        createUpdateAdult();

//        createChildTable();
//        createUpdateChild();
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
                "    parent_id INT\n" +
//                "    FOREIGN KEY (parent_id) REFERENCES adults(id) ON DELETE CASCADE\n" +
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
        String query = "CREATE TABLE IF NOT EXISTS doctorsoffice.nurses (\n" +
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

    private void createUpdateNurse() {

        String remove = "drop function if exists doctorsoffice.update_nurse\n";
        String query = "CREATE FUNCTION doctorsoffice.update_nurse(req_id int, lastname varchar(25), firstname varchar(25), " +
                "email varchar(50), hire int, hours int) RETURNS int(11)\n" +
                "DETERMINISTIC\n" +
                "BEGIN\n" +
                "update doctorsoffice.nurses\n" +
                "set lastname = lastname,\n" +
                "firstname = firstname,\n" +
                "email = email,\n" +
                "hire_year = hire,\n" +
                "hours = hours\n" +
                "where id = req_id;\n" +
                "RETURN row_count();\n" +
                "END";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(remove);
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void createUpdatePediatrician() {

        String remove = "drop function if exists doctorsoffice.update_pediatrician\n";
        String query = "CREATE FUNCTION doctorsoffice.update_pediatrician(req_id int, lastname varchar(25), firstname varchar(25), " +
                "email varchar(50), hire int, bonus int) RETURNS int(11)\n" +
                "DETERMINISTIC\n" +
                "BEGIN\n" +
                "update doctorsoffice.pediatricians\n" +
                "set lastname = lastname,\n" +
                "firstname = firstname,\n" +
                "email = email,\n" +
                "hire_year = hire,\n" +
                "bonus = bonus\n" +
                "where id = req_id;\n" +
                "RETURN row_count();\n" +
                "END";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(remove);
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createUpdateFamilyDoctor() {

        String remove = "drop function if exists doctorsoffice.update_familydoctor\n";
        String query = "CREATE FUNCTION doctorsoffice.update_familydoctor(req_id int, lastname varchar(25), firstname varchar(25), " +
                "email varchar(50), hire int, no_of_families int) RETURNS int(11)\n" +
                "DETERMINISTIC\n" +
                "BEGIN\n" +
                "update doctorsoffice.family_doctors\n" +
                "set lastname = lastname,\n" +
                "firstname = firstname,\n" +
                "email = email,\n" +
                "hire_year = hire,\n" +
                "no_of_families = no_of_families\n" +
                "where id = req_id;\n" +
                "RETURN row_count();\n" +
                "END";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(remove);
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createUpdateAdult() {

        String remove = "drop function if exists doctorsoffice.update_adult\n";
        String query = "CREATE FUNCTION doctorsoffice.update_adult(req_id int, lastname varchar(25), firstname varchar(25), " +
                "birth_year int, CNP varchar(25), tel varchar(25), health_insurance bit) RETURNS int(11)\n" +
                "DETERMINISTIC\n" +
                "BEGIN\n" +
                "update doctorsoffice.adults\n" +
                "set lastname = lastname,\n" +
                "firstname = firstname,\n" +
                "birth_year = birth_year,\n" +
                "CNP = CNP,\n" +
                "tel = tel,\n" +
                "health_insurance = health_insurance\n" +
                "where id = req_id;\n" +
                "RETURN row_count();\n" +
                "END";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(remove);
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createUpdateChild() {

        String remove = "drop function if exists doctorsoffice.update_child\n";
        String query = "CREATE FUNCTION doctorsoffice.update_child(req_id int, lastname varchar(25), firstname varchar(25), " +
                "birth_year int, CNP varchar(25), tel varchar(25), parent_id int) RETURNS int(11)\n" +
                "DETERMINISTIC\n" +
                "BEGIN\n" +
                "update doctorsoffice.adults\n" +
                "set lastname = lastname,\n" +
                "firstname = firstname,\n" +
                "birth_year = birth_year,\n" +
                "CNP = CNP,\n" +
                "tel = tel,\n" +
                "parent_id = parent_id\n" +
                "where id = req_id;\n" +
                "RETURN row_count();\n" +
                "END";
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            Statement statement = connection.createStatement();
            statement.execute(remove);
            statement.execute(query);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
