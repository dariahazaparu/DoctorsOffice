package office.database.repository;

import office.database.config.DatabaseConfiguration;

import office.patient.Adult;

import java.sql.*;
import java.util.ArrayList;

public class AdultRepo {

    public Adult insert(Adult adult) {

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into adults(lastname, firstname, birth_year, CNP, tel, health_insurance) VALUES(?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, adult.getLastName());
            preparedStatement.setString(2, adult.getFirstName());
            preparedStatement.setInt(3, adult.getBirthYear());
            preparedStatement.setString(4, adult.getCNP());
            preparedStatement.setString(5, adult.getTel());
            preparedStatement.setBoolean(6, adult.isHealthInsurance());

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                adult.setID(resultSet.getInt(1));
            }
            resultSet.close();
            return adult;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while inserting: " + adult);
        }
    }

    public ArrayList<Adult> findAll() {
        ArrayList<Adult> adults = new ArrayList<>();
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * FROM adults";
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                adults.add(mapToAdult(resultSet));
            }

            resultSet.close();

            return adults;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to get all pediatricians. ");
        }
    }

    public Adult find(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * from adults where id=" + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Adult adult = new Adult("", "", 0, "", "", false);
            if (resultSet.next()){
                int nid = resultSet.getInt("id");
                adult.setID(nid);
                String lastname = resultSet.getString("lastname");
                adult.setLastName(lastname);
                String firstname = resultSet.getString("firstname");
                adult.setFirstName(firstname);
                int birth = resultSet.getInt("birth_year");
                adult.setBirthYear(birth);
                String CNP = resultSet.getString("cnp");
                adult.setCNP(CNP);
                String tel = resultSet.getString("tel");
                adult.setTel(tel);
                boolean hi = resultSet.getBoolean("health_insurance");
                adult.setHealthInsurance(hi);
            } else {
                return null;
            }

            return adult;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to find adult with id " + id);
        }
    }

    public boolean update(Adult adult) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "{?= call update_adult(?,?,?,?,?,?,?)}";

            CallableStatement callableStatement = connection.prepareCall(query);
            callableStatement.setInt(2, adult.getID());
            callableStatement.setString(3, adult.getLastName());
            callableStatement.setString(4, adult.getFirstName());
            callableStatement.setInt(5, adult.getBirthYear());
            callableStatement.setString(6, adult.getCNP());
            callableStatement.setString(7, adult.getTel());
            callableStatement.setBoolean(8, adult.isHealthInsurance());
            callableStatement.registerOutParameter(1, Types.INTEGER);

            callableStatement.executeUpdate();
            int response = callableStatement.getByte(1);
            return response == 1;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to updated the adult with id: " + adult);
        }
    }

    private Adult mapToAdult(ResultSet resultSet) throws SQLException {
        Adult adult = new Adult(resultSet.getString("lastname"),
                resultSet.getString("firstname"),
                resultSet.getInt("birth_year"),
                resultSet.getString("cnp"),
                resultSet.getString("tel"),
                resultSet.getBoolean("health_insurance")
                );
        return adult;
    }

    public Adult delete(Adult adult) {

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "DELETE from adults WHERE id = (?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, adult.getID());

            preparedStatement.execute();
            return adult;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while deleting: " + adult);
        }
    }

    public Adult findParent(String name) {
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * from adults WHERE lastname ='" + name + "'";
            Statement statement = connection.createStatement();
            System.out.println("alo");
            ResultSet resultSet = statement.executeQuery(query);
            Adult adult = new Adult("", "", 0, "", "", false);
            if (resultSet.next()){
                int nid = resultSet.getInt(1);
                adult.setID(nid);
                String lastname = resultSet.getString(2);
                adult.setLastName(lastname);
                String firstname = resultSet.getString(3);
                adult.setFirstName(firstname);
                int birth = resultSet.getInt(5);
                adult.setBirthYear(birth);
                String CNP = resultSet.getString(4);
                adult.setCNP(CNP);
                String tel = resultSet.getString(4);
                adult.setTel(tel);
                boolean hi = resultSet.getBoolean(6);
                adult.setHealthInsurance(hi);
            } else {
                return null;
            }
            resultSet.close();
            return adult;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to find parent with name " + name);
        }
    }
}
