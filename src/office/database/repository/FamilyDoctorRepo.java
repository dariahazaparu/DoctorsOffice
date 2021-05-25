package office.database.repository;

import office.database.config.DatabaseConfiguration;
import office.doctor.FamilyDoctor;
import office.doctor.Pediatrician;

import java.sql.*;
import java.util.ArrayList;

public class FamilyDoctorRepo {

    public FamilyDoctor insert(FamilyDoctor fam) {

        try (
        Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
        String query = "INSERT into family_doctors(lastname, firstname, email, hire_year, no_of_families) VALUES(?,?,?,?,?)";

        PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, fam.getLastName());
        preparedStatement.setString(2, fam.getFirstName());
        preparedStatement.setString(3, fam.getEmail());
        preparedStatement.setInt(4, fam.getHireYear());
        preparedStatement.setInt(5, fam.getNoOfFamilies());

        preparedStatement.execute();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            fam.setID(resultSet.getInt(1));
        }
        resultSet.close();
        return fam;

    } catch (
    SQLException exception) {
        throw new RuntimeException("Something went wrong while inserting: " + fam);
    }
}

    public ArrayList<FamilyDoctor> findAll() {
        ArrayList<FamilyDoctor> fams = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * FROM family_doctors";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                fams.add(mapToFam(resultSet));
            }

            resultSet.close();
            return fams;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to get all pediatricians. ");
        }
    }

    public Pediatrician find(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * from family_doctors where id=" + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Pediatrician ped = new Pediatrician("", "", "", 0, 0);
            if (resultSet.next()){
                int nid = resultSet.getInt(1);
                ped.setID(nid);
                String lastname = resultSet.getString(2);
                ped.setLastName(lastname);
                String firstname = resultSet.getString(3);
                ped.setFirstName(firstname);
                String email = resultSet.getString(4);
                ped.setEmail(email);
                int hire = resultSet.getInt(5);
                ped.setHireYear(hire);
                int no_of_families = resultSet.getInt(6);
                ped.setBonus(no_of_families);
            }

            return ped;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to find pediatrician with id " + id);
        }
    }

//    public boolean update(Nurse nurse) {
//        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
//            String query = "{?= call update_nurse(?,?,?,?,?,?)}";
//
//            CallableStatement callableStatement = connection.prepareCall(query);
//            System.out.println(nurse.getHours());
//            callableStatement.setInt(1, nurse.getID());
//            callableStatement.setString(2, nurse.getLastName());
//            callableStatement.setString(3, nurse.getFirstName());
//            callableStatement.setString(4, nurse.getEmail());
//            callableStatement.setInt(5, nurse.getHireYear());
//            callableStatement.setInt(6, nurse.getHours());
//            callableStatement.registerOutParameter(1, Types.INTEGER);
//
//            callableStatement.executeUpdate();
//            int response = callableStatement.getByte(1);
//
//            return response == 1;
//
//        } catch (SQLException exception) {
//            throw new RuntimeException("Something went wrong while tying to updated the nurse with id: " + nurse);
//        }
//    }

    private FamilyDoctor mapToFam(ResultSet resultSet) throws SQLException {
        FamilyDoctor fam = new FamilyDoctor(resultSet.getString(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getInt(5),
                resultSet.getInt(6));
        fam.setID(resultSet.getInt(1));
        return fam;
    }

    public FamilyDoctor delete(FamilyDoctor ped) {

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "DELETE from family_doctors WHERE id = (?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, ped.getID());

            preparedStatement.execute();
            return ped;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while deleting: " + ped);
        }
    }
}