package model;


import database.CountryQueries;
import database.DivisionQueries;

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
    private int countryId;

    /**Customer constructor.
     * @param customerName Customer Name as string
     * @param phoneNumber Phone number as string
     * @param address Address as string
     * @param countryName Country Name as string
     * @param division Division name as string
     * @param divisionId Division ID as int
     * @param postalCode Postal code as string
     * @param customerId Customer ID as int
     * @param countryId Country ID as int
     */
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

    /** Getter for the customerId.
     * @return customerId Returns customerId
     */
    public Integer getCustomerId() {
        return customerId;
    }

    /** Setter for customerId.
     * @param customerId Sets customer ID
     */
    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    /** Getter for the customerName.
     * @return customerName Returns customer Name
     */
    public String getCustomerName() {
        return customerName;
    }

    /** Setter for customerName.
     * @param customerName Sets customer name
     */
    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    /** Getter for the address.
     * @return address Returns customer address
     */
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    /** Getter for the postalCode.
     * @return postalCode Returns customer postal code
     */
    public String getPostalCode() {
        return postalCode;
    }

    /** Setter for postalCode.
     * @param postalCode Sets postal code
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /** Getter for the phoneNumber.
     * @return phoneNumber Returns customer phone number
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    /** Setter for phoneNumber.
     * @param phoneNumber Sets phone number
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    /** Getter for the division.
     * @return DivisionQueries.divisionsByCountry() Returns list of division names for country
     * @throws SQLException SQLException
     */
    public Division getDivision() throws SQLException {
        return DivisionQueries.divisionsByCountry(countryId);
    }

    /** Getter for the divisionName.
     * @return division Returns division names
     * @throws SQLException SQLException
     */
    public String getDivisionName() throws SQLException {
        return this.division;
        //return division;
    }

    /** Setter for division.
     * @param division Sets division name
     */
    public void setDivision(String division) {
        this.division = division;
    }

    /** Getter for the countryName.
     * @return countryName Returns country name
     */
    public String getCountryName() {
        return countryName;
    }

    /** Setter for countryName.
     * @param countryName Sets country name
     */
    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    /** Getter for the country.
     * @return CountryQueries.getCountryByDivision() Returns country name for specific division
     * @throws SQLException SQLException
     */
    public Country getCountry() throws SQLException {
        return CountryQueries.getCountryByDivision(divisionId);
    }

    /** Getter for the divisionId.
     * @return divisionId Returns division Id
     */
    public int getDivisionId() {
        return divisionId;
    }

    /** Setter for divisionId.
     * @param divisionId Sets divisionId
     */
    public void setDivisionId(int divisionId) {
        this.divisionId = divisionId;
    }


}

