package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Country;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CountryQueries {


    public static Country getCountryByDivision(int divisionID) throws SQLException {

        try{
            String sql = "SELECT * FROM countries AS c INNER JOIN first_level_divisions AS d ON c.country_ID=d.country_ID" +
                    " AND d.division_ID=?";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ps.setInt(1, divisionID);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                String countryName = resultSet.getString("Country");
                Integer countryID = resultSet.getInt("Country_ID");
                Country newCountry = new Country(countryID, countryName);
                return newCountry;
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return null;
    }


    public static ObservableList<Country> getCountries() throws SQLException {

        ObservableList<Country> countryList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM countries";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                String countryName = resultSet.getString("Country");
                Integer countryID = resultSet.getInt("Country_ID");
                Country newCountry = new Country(countryID, countryName);
                countryList.add(newCountry);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return countryList;
    }
}
