package model;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Timestamp;

/** Customer Class.
 */
public class Customer {
    private Integer customerId;
    private String customerName;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private String division;
    private String country;
    //private int divisionId;

    private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();

    public Customer(String customerName, String phoneNumber, String address, String country, String division, String postalCode, Integer customerId) {

        this.customerName = customerName;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.country = country;
        this.division = division;
        this.postalCode = postalCode;
        this.customerId = customerId;
        //this.divisionId = divisionId;
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


    public String getDivision() {
        return division;
    }

    public void setDivision(String division) {
        this.division = division;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    /**public int getDivisionId() {
        return divisionId;
    }

    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }**/

    //public static ObservableList<Customer> getAllCustomers(){
      //  return allCustomers;
    //}
    public static ObservableList<Customer> lookupCustomer(String customerName) {
        ObservableList<Customer> searchCustomers = FXCollections.observableArrayList();
        //ObservableList<Customer> allCustomers = Customer.getAllCustomers();
        for (Customer customer : allCustomers) {
            if (customer.getCustomerName().contains(customerName)) {
                searchCustomers.add(customer);
            } else if (Integer.toString(customer.getCustomerId()).contains(customerName)){
                searchCustomers.add(customer);
            }
        }
        return searchCustomers;
    }
   /*
    public static Customer lookupCustomer(int customerId){
        ObservableList<Customer> allCustomers = Customer.getAllCustomers();
        for(Customer customer : allCustomers){
            if(customer.getCustomerId() == customerId){
                return customer;
            }
        }
        return null;
    }*/

}

