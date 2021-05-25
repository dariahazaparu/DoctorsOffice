package office.database.repository;

import office.database.config.DatabaseConfiguration;
import office.patient.Adult;
import office.patient.Child;

import java.sql.*;
import java.util.ArrayList;

public class ChildRepo {
    public Child insert(Child child) {

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "INSERT into children(lastname, firstname, birth_year, CNP, tel, parent_id) VALUES(?,?,?,?,?,?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, child.getLastName());
            preparedStatement.setString(2, child.getFirstName());
            preparedStatement.setInt(3, child.getBirthYear());
            preparedStatement.setString(4, child.getCNP());
            preparedStatement.setString(5, child.getTel());
//            String parent_name = child.getParentName();
            preparedStatement.setInt(6, 10);

            preparedStatement.execute();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            if (resultSet.next()) {
                child.setID(resultSet.getInt(1));
            }
            resultSet.close();
            return child;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while inserting: " + child);
        }
    }

//    public ArrayList<Child> findAll() {
//        ArrayList<Child> children = new ArrayList<>();
//        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
//            String query = "SELECT * FROM adults";
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery(query);
//
//            while (resultSet.next()) {
//                children.add(mapToChild(resultSet));
//            }
//
//            resultSet.close();
//            return children;
//
//        } catch (SQLException exception) {
//            throw new RuntimeException("Something went wrong while tying to get all pediatricians. ");
//        }
//    }

    public Child find(int id){
        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "SELECT * from children where id=" + id;
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);

            Child child = new Child("", "", 0, "", "", "");
            if (resultSet.next()){
                int nid = resultSet.getInt(1);
                child.setID(nid);
                String lastname = resultSet.getString(2);
                child.setLastName(lastname);
                String firstname = resultSet.getString(3);
                child.setFirstName(firstname);
                int birth = resultSet.getInt(5);
                child.setBirthYear(birth);
                String CNP = resultSet.getString(4);
                child.setCNP(CNP);
                String tel = resultSet.getString(4);
                child.setTel(tel);
                String hi = resultSet.getString(6);
                child.setParentName(hi);
            }

            return child;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while tying to find adult with id " + id);
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

//    private Child mapToChild(ResultSet resultSet) throws SQLException {
//        Child child = new Child(resultSet.getString(2), resultSet.getString(3),
//                resultSet.getInt(4), resultSet.getString(5),
//                resultSet.getString(6), );
//        child.setID(resultSet.getInt(1));
//        return child;
//    }

    public Child delete(Child child) {

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection()) {
            String query = "DELETE from children WHERE id = (?)";

            PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setInt(1, child.getID());

            preparedStatement.execute();
            return child;

        } catch (SQLException exception) {
            throw new RuntimeException("Something went wrong while deleting: " + child);
        }
    }
}
