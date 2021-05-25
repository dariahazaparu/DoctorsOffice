package office.database.repository;

import office.database.config.DatabaseConfiguration;
import office.doctor.Nurse;

import java.sql.*;
import java.util.ArrayList;


public class NurseRepo {

    public Nurse insert(Nurse nurse) {

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into nurses(lastname, firstname, email, hire_year, hours) VALUES(?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, nurse.getLastName());
            preparedStatement.setString(2, nurse.getFirstName());
            preparedStatement.setString(3, nurse.getEmail());
            preparedStatement.setInt(4, nurse.getHireYear());
            preparedStatement.setInt(5, nurse.getHours());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                nurse.setID(resultSet.getInt(1));
            }
            resultSet.close();
            return nurse;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while inserting: " + nurse);
        }
    }

    public ArrayList<Nurse> findAll() {
        ArrayList<Nurse> nurses = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * FROM nurses";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                nurses.add(mapToNurse(resultSet));
            }

            resultSet.close();
            return nurses;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to get all nurses. ");
        }
    }

    public Nurse find(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * from nurses where id=" + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Nurse nurse = new Nurse("", "", "", 0, 0);
            if (resultSet.next()){
                int nid = resultSet.getInt(1);
                nurse.setID(nid);
                String lastname = resultSet.getString(2);
                nurse.setLastName(lastname);
                String firstname = resultSet.getString(3);
                nurse.setFirstName(firstname);
                String email = resultSet.getString(4);
                nurse.setEmail(email);
                int hire = resultSet.getInt(5);
                nurse.setHireYear(hire);
                int hours = resultSet.getInt(6);
                nurse.setHours(hours);
            } else {
                return null;
            }

            return nurse;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to find nurse with id " + id);
        }
    }

    public boolean update(Nurse nurse) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "{?= call update_nurse(?,?,?,?,?,?)}";

            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setInt(2, nurse.getID());
            callableStatement.setString(3, nurse.getLastName());
            callableStatement.setString(4, nurse.getFirstName());
            callableStatement.setString(5, nurse.getEmail());
            callableStatement.setInt(6, nurse.getHireYear());
            callableStatement.setInt(7, nurse.getHours());
            callableStatement.registerOutParameter(1, Types.INTEGER);

            callableStatement.executeUpdate();
            int response = callableStatement.getByte(1);

            return response == 1;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to updated the nurse with id: " + nurse);
        }
    }

    private Nurse mapToNurse(ResultSet resultSet) throws SQLException {
        Nurse nurse = new Nurse(resultSet.getString(2), resultSet.getString(3),
                resultSet.getString(4), resultSet.getInt(5),
                resultSet.getInt(6));
        nurse.setID(resultSet.getInt(1));
        return nurse;
    }

    public Nurse delete(Nurse nurse) {

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "DELETE from nurses WHERE id = (?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, nurse.getID());

            preparedStatement.execute();
            return nurse;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while inserting: " + nurse);
        }
    }
}
