package controller;

import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Contact;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.*;
import java.util.Optional;
import java.util.ResourceBundle;

public class CreateAppointment implements Initializable {

    public TableView custCreateAppTable;
    public TableColumn nameColumn;
    public TableColumn phoneColumn;
    public TableColumn addressColumn;
    public TableColumn countryColumn;
    public TableColumn stateColumn;
    public TableColumn zipcodeColumn;
    public TableColumn customerIdColumn;
    public TextField customerIdText;
    public TextField descriptionText;
    public TextField userIdText;
    public ComboBox contactNameComboBox;
    public TextField appointmentIdText;
    public TextField locationText;
    public TextField titleText;
    public TextField typeText;
    public DatePicker startDatePicker;
    public DatePicker endDatePicker;
    public ComboBox startTimeComboBox;
    public ComboBox endTimeComboBox;

    private int index;
    Customer customerToModify = null;
    //private Customer newCustomer;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            custCreateAppTable.setItems(customers());
            nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<Country, String>("country"));
            stateColumn.setCellValueFactory(new PropertyValueFactory<Division, String>("division"));
            zipcodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
            //divisionIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionId"));
            contactNameComboBox.setItems(contacts());
            startEndTimeCombos();

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    public static ObservableList<Customer> customers() throws SQLException{

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
                //int divisionId = resultSet.getInt("Division_ID");
                Customer newCustomer = new Customer(customerName,phoneNumber, address, country, division, postalCode, customerId);
                existingCustomersList.add(newCustomer);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        return existingCustomersList;

    }



//observable list to populate contact names into combo box  // public static ObservableList<Contact> contacts()
    public static ObservableList<String> contacts() throws SQLException{

        ObservableList<String> contactNamesList = FXCollections.observableArrayList(); //ObservableList<Contact>
        try{
            String sql = "SELECT contact_name FROM contacts";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                String contactName = resultSet.getString("Contact_Name");
                String newContact = new String(contactName); //Contact newContact = new Contact(contactName);
                contactNamesList.add(newContact);
            }
        }
        catch (SQLException ex){
            ex.printStackTrace();
        }
        //System.out.println("Error 2: " + contactNamesList);
        return contactNamesList;

    }



    private void startEndTimeCombos(){


        ObservableList<String> schedulingTimes = FXCollections.observableArrayList();
        LocalTime start = LocalTime.of(8, 0);
        LocalTime end = LocalTime.of(22, 0);
        schedulingTimes.add(start.toString());
        while(start.isBefore(end)){
            start = start.plusMinutes(30);
            schedulingTimes.add(start.toString());
        }
        startTimeComboBox.setItems(schedulingTimes);
        endTimeComboBox.setItems(schedulingTimes);

        /*ObservableList<String> schedulingTimes = FXCollections.observableArrayList();

        LocalTime start = LocalTime.of(8, 0);
        //ZoneId zone = ZoneId.of("America/New_York");
        //start.atOffset(zone.getRules().getOffset(LocalDateTime.now(ZoneId.of("America/New_York"))));
        //ZoneId zone = ZoneId.of(String.valueOf(ZoneId.systemDefault()));
        //start.atOffset(zone.getRules().getOffset(LocalDateTime.now()));
        //System.out.println(start.atOffset(zone.getRules().getOffset(LocalDateTime.now())));
        OffsetTime end = LocalTime.of(22, 0).atOffset(ZoneOffset.of("America/New_York"));
        //LocalTime st = ZoneOffset.of("America/New_York").
        System.out.println(end);

        schedulingTimes.add(start.toString());
        while(start.isBefore(LocalTime.from(end))){
            start = start.plusMinutes(30);
            schedulingTimes.add(start.toString());
        }
        startTimeComboBox.setItems(schedulingTimes);
        endTimeComboBox.setItems(schedulingTimes);
*/
    }




    public void OnCancelButton(ActionEvent actionEvent) throws IOException {
        Alert alert3 = new Alert (Alert.AlertType.CONFIRMATION);
        alert3.setTitle("Confirmation");
        alert3.setHeaderText((null));
        alert3.setContentText("Are you sure you want to cancel? All data will be lost.");
        Optional<ButtonType> result = alert3.showAndWait();
        if (alert3.getResult() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.FXML"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

/**public void sendId(Customer customer, int index){
        this.index = index;
        customerToModify = customer;
        newCustomer = customer;
        customerIdText.setText(String.valueOf(newCustomer.getCustomerId()));



}**/
    public void selectCustomerButton(ActionEvent actionEvent) {
       Customer c = (Customer) custCreateAppTable.getSelectionModel().getSelectedItem();
            if (c == null){
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Nothing Selected");
                alert.setContentText("Please select a customer");
                alert.showAndWait();
                return;
            }
            else{
                customerIdText.setText(String.valueOf(c.getCustomerId()));
            }
    }
}
