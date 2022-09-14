package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DivisionQueries {

    public static ObservableList<Division> getDivisions() throws SQLException {

        ObservableList<Division> divisionList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                String divisionName = resultSet.getString("Division");
                Integer divisionId = resultSet.getInt("Division_ID");
                Integer countryId = resultSet.getInt("Country_ID");
                Division newDivision = new Division(divisionId, divisionName, countryId);
                divisionList.add(newDivision);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return divisionList;
    }

    public static ObservableList<Division> associatedDivisions(int countryId) throws SQLException{

        ObservableList sortedDivisions = FXCollections.observableArrayList();

        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, countryId);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                Integer divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                Integer countryID = resultSet.getInt("Country_ID");
                Division newDivision = new Division(divisionId, divisionName, countryID);
                sortedDivisions.add(newDivision);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
       return sortedDivisions;
    }
    public static Division divisionsByCountry(int countryId) throws SQLException{

        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Country_ID=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setInt(1, countryId);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                Integer divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                Integer countryID = resultSet.getInt("Country_ID");
                Division newDivision = new Division(divisionId, divisionName, countryID);
                return newDivision;
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static Division divisionsById(String division) throws SQLException{

        try{
            String sql = "SELECT * FROM first_level_divisions WHERE Division=?";
            PreparedStatement ps = JDBC.connection.prepareStatement(sql);
            ps.setString(1, division);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                Integer divisionId = resultSet.getInt("Division_ID");
                String divisionName = resultSet.getString("Division");
                Integer countryID = resultSet.getInt("Country_ID");
                Division newDivision = new Division(divisionId, divisionName, countryID);
                return newDivision;
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }

}