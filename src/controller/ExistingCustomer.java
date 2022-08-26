package controller;

import database.Queries;
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
import model.Appointment;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

public class ExistingCustomer implements Initializable {

    public TableView<Customer> existingCustomersTable;
    public TableColumn<Customer, String> nameColumn;
    public TableColumn<Customer, String> phoneColumn;
    public TableColumn <Customer, String>addressColumn;
    public TableColumn <Country, String>countryColumn;
    public TableColumn <Division, String>stateColumn;
    public TableColumn <Customer, String>zipcodeColumn;
    public TableColumn <Customer, Integer>customerIdColumn;
    public TextField nameTextBox;
    public TextField phoneTextBox;
    public TextField addressTextBox;
    public TextField postalCodeTextBox;
    public TextField idTextBox;
    public ComboBox countryComboBox;
    public ComboBox stateComboBox;
    public TableView existingAppointmentsTable;
    //public TableColumn <Customer, Integer>divisionIdColumn;
    static ObservableList<Customer> allCustomers;
    public TextField searchTextBox;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn locationColumn;
    public TableColumn contactColumn;
    public TableColumn typeColumn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            existingCustomersTable.setItems(existingCustomers());
            nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<Country, String>("country"));
            stateColumn.setCellValueFactory(new PropertyValueFactory<Division, String>("division"));
            zipcodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
            //divisionIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("divisionId"));
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }

    public static ObservableList<Customer> existingCustomers() throws SQLException{

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


    public void onCreateNewButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddNewCustomer.FXML"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void onExitButton(ActionEvent actionEvent) throws IOException{
        Alert alert3 = new Alert (Alert.AlertType.CONFIRMATION);
        alert3.setTitle("Confirmation");
        alert3.setHeaderText((null));
        alert3.setContentText("Are you sure you want to exit? All data will be lost.");
        Optional<ButtonType> result = alert3.showAndWait();
        if (alert3.getResult() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.FXML"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void onEditCustomerButton(ActionEvent actionEvent) {
        //Integer customerID =
      try {
          Integer cID = existingCustomersTable.getSelectionModel().getSelectedItem().getCustomerId();
          Customer c = (Customer) existingCustomersTable.getSelectionModel().getSelectedItem();
          ObservableList<Appointment> a = Queries.associatedApointments(cID);
          System.out.println(a);
          if (c == null) {
              Alert alert = new Alert(Alert.AlertType.ERROR);
              alert.setTitle("Error");
              alert.setHeaderText("Nothing Selected");
              alert.setContentText("Please select a customer");
              alert.showAndWait();
              return;
          } else {
              existingAppointmentsTable.setItems(a);
              startColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startDateTime"));
              endColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("endDateTime"));
              locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
              contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactId"));
              typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
              nameTextBox.setText(c.getCustomerName());
              phoneTextBox.setText(String.valueOf(c.getPhoneNumber()));
              addressTextBox.setText(c.getAddress());
              postalCodeTextBox.setText(String.valueOf(c.getPostalCode()));
              idTextBox.setText(String.valueOf(c.getCustomerId()));
              //countryComboBox.setItems();

          }
      }
      catch (Exception e){
          e.printStackTrace();
      }
    }


}
