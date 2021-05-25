package office.database.repository;

import office.database.config.DatabaseConfiguration;
import office.doctor.Pediatrician;

import java.sql.*;
import java.util.ArrayList;

public class PediatricianRepo {
    public Pediatrician insert(Pediatrician ped) {

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into pediatricians(lastname, firstname, email, hire_year, bonus) VALUES(?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, ped.getLastName());
            preparedStatement.setString(2, ped.getFirstName());
            preparedStatement.setString(3, ped.getEmail());
            preparedStatement.setInt(4, ped.getHireYear());
            preparedStatement.setInt(5, ped.getBonus());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                ped.setID(resultSet.getInt(1));
            }
            resultSet.close();
            return ped;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while inserting: " + ped);
        }
    }

    public ArrayList<Pediatrician> findAll() {
        ArrayList<Pediatrician> peds = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * FROM pediatricians";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                peds.add(mapToPeds(resultSet));
            }

            resultSet.close();
            return peds;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to get all pediatricians. ");
        }
    }

    public Pediatrician find(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * from pediatricians where id=" + id;
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
                int bonus = resultSet.getInt(6);
                ped.setBonus(bonus);
            }

            return ped;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to find pediatrician with id " + id);
        }
    }

    public boolean update(Pediatrician ped) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "{?= call update_pediatrician(?,?,?,?,?,?)}";

            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setInt(2, ped.getID());
            callableStatement.setString(3, ped.getLastName());
            callableStatement.setString(4, ped.getFirstName());
            callableStatement.setString(5, ped.getEmail());
            callableStatement.setInt(6, ped.getHireYear());
            callableStatement.setInt(7, ped.getBonus());
            callableStatement.registerOutParameter(1, Types.INTEGER);

            callableStatement.executeUpdate();
            int response = callableStatement.getByte(1);

            return response == 1;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to updated the pediatrician with id: " + ped);
        }
    }

    private Pediatrician mapToPeds(ResultSet resultSet) throws SQLException {
        Pediatrician ped = new Pediatrician(resultSet.getString(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getInt(5),
                resultSet.getInt(6));
        ped.setID(resultSet.getInt(1));
        return ped;
    }

    public Pediatrician delete(Pediatrician ped) {

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "DELETE from pediatricians WHERE id = (?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, ped.getID());

            preparedStatement.execute();
            return ped;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while deleting: " + ped);
        }
    }
}
