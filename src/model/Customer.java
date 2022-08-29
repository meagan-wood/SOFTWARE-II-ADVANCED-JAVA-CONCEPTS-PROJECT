package model;


import database.CountryQueries;
import database.DivisionQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.SQLException;

/** Customer Class.
 */
public class Customer {
    private Integer customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private int divisionId;
    private String division;
    private String countryName;
    private int countryId; //////


    //private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();



    public Customer(String customerName, String phoneNumber, String address, String countryName, String division, int divisionId, String postalCode, Integer customerId, int countryId) {

        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.countryName = countryName;
        this.division = division;
        this.postalCode = postalCode;
        this.customerId = customerId;
        this.divisionId = divisionId;
        this.countryId = countryId;

    }



    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }


    public Division getDivision() throws SQLException {
        return DivisionQueries.divisionsByCountry(countryId);
        //return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public Country getCountry() throws SQLException {
        return CountryQueries.getCountryByDivision(divisionId);
    }

    public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }



}

