package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/** Divisions queries class. */
public class DivisionQueries {

    /** Get divisions query. Database query retrieves all divisions.
     * Catches exceptions, prints stacktrace
     * @return divisionList Observable list of all divisions
     * @throws SQLException SQLException
     */
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

    /** Divisions by country id query. Database query retrieves divisions given country id.
     * Catches exceptions, prints stacktrace
     * @return sortedDivisionList Observable list of divisions associated with given country
     * @throws SQLException SQLException
     * @param countryId country id
     */
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

    /** Divisions by country id query.
     * Catches exceptions, prints stacktrace
     * @return null
     * @throws SQLException SQLException
     * @param countryId country id
     */
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

    /** Division query to retrieve division id given division name.
     * Catches exceptions, prints stacktrace
     * @return null
     * @throws SQLException SQLException
     * @param division division name
     */
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
