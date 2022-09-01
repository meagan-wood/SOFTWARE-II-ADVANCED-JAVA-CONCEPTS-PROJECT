package database;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.Customer;
import model.Division;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerQueries {

    public static ObservableList<Customer> existingCustomers() throws SQLException {

        ObservableList<Customer> existingCustomersList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM customers AS cu INNER JOIN first_level_divisions AS d ON cu.Division_ID = d.Division_ID INNER JOIN countries AS co ON d.Country_ID = co.Country_ID";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                int customerId = resultSet.getInt("Customer_ID");
                String customerName = resultSet.getString("Customer_Name");
                String phoneNumber = resultSet.getString("Phone");
                String address = resultSet.getString("Address");
                String country = resultSet.getString("Country");
                String division = resultSet.getString("Division");
                String postalCode = resultSet.getString("Postal_Code");
                int divisionId = resultSet.getInt("Division_ID");
                int countryId = resultSet.getInt("Country_ID");
                Customer newCustomer = new Customer(customerName,phoneNumber, address, country, division, divisionId, postalCode, customerId, countryId);
                existingCustomersList.add(newCustomer);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return existingCustomersList;
    }


    public static int insertCustomer(String customerName, String address, String postalCode, String phoneNumber, String division) throws SQLException {
        Division d = DivisionQueries.divisionsById(division);
        String sql = "INSERT INTO CUSTOMERS (Customer_Name, Address, Postal_Code, Phone, Division_ID) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, d.getDivisionId());
        //ps.setInt(5, Integer.parseInt(division));
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }

    public static int updateCustomer(int customerId, String customerName, String address, String postalCode, String phoneNumber, String divisionName) throws SQLException {
        Division d = DivisionQueries.divisionsById(divisionName);
        String sql = "UPDATE CUSTOMERS SET Customer_Name=?, Address=?, Postal_Code=?, Phone=?, Division_ID=? WHERE Customer_ID=?";
        PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
        ps.setString(1, customerName);
        ps.setString(2, address);
        ps.setString(3, postalCode);
        ps.setString(4, phoneNumber);
        ps.setInt(5, d.getDivisionId());
        ps.setInt(6, customerId);
        int rowsAffected = ps.executeUpdate();
        return rowsAffected;
    }
}

