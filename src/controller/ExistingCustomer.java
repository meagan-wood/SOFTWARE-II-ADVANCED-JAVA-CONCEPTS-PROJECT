package controller;

import database.*;
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
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;

/** Existing customers controller class. */
public class ExistingCustomer implements Initializable {

    public TableView<Customer> existingCustomersTable;
    public TableColumn<Customer, String> nameColumn;
    public TableColumn<Customer, String> phoneColumn;
    public TableColumn <Customer, String>addressColumn;
    public TableColumn <Country, String>countryColumn;
    public TableColumn <Customer, String>stateColumn;
    public TableColumn <Customer, String>zipcodeColumn;
    public TableColumn <Customer, Integer>customerIdColumn;
    public TextField nameTextBox;
    public TextField phoneTextBox;
    public TextField addressTextBox;
    public TextField postalCodeTextBox;
    public TextField idTextBox;
    public ComboBox<Country> countryComboBox;
    public ComboBox stateComboBox;
    public TableView existingAppointmentsTable;
    public TableColumn startColumn;
    public TableColumn endColumn;
    public TableColumn locationColumn;
    public TableColumn contactColumn;
    public TableColumn typeColumn;

    /** Initialize Existing customer form, loads the country and state combo boxes.
     * Catches exceptions, prints stacktrace
     * @param resourceBundle resource bundle
     * @param url url
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            existingCustomersTable.setItems(CustomerQueries.existingCustomers());
            nameColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("customerName"));
            phoneColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("phoneNumber"));
            addressColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("address"));
            countryColumn.setCellValueFactory(new PropertyValueFactory<Country, String>("country"));
            stateColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("divisionName"));
            zipcodeColumn.setCellValueFactory(new PropertyValueFactory<Customer, String>("postalCode"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<Customer, Integer>("customerId"));
            countryComboBox.setItems(CountryQueries.getCountries());
            stateComboBox.setItems(DivisionQueries.getDivisions());
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
    }

    /** Create new (customer) button on create existing customer form.
     * @throws IOException IOException
     * @param actionEvent action event
     */
    public void onCreateNewButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddNewCustomer.FXML"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    /** Cancel button on existing customer form. Verifies you wish to cancel and lose unsaved data, returns to main form.
     * @throws IOException IOException
     * @param actionEvent action event
     */
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

    /** Edit button on existing customer form. Transfers customer data from table to text boxes/combo boxes to edit.
     * Catches exceptions, prints stack trace. Alerts if no customer to edit has been selected.
     * @param actionEvent action event
     */
    public void onEditCustomerButton(ActionEvent actionEvent) {
        Customer c =  existingCustomersTable.getSelectionModel().getSelectedItem();

        if(c !=null){
            try{
                Integer cID = existingCustomersTable.getSelectionModel().getSelectedItem().getCustomerId();
                ObservableList<Appointment> a = AppointmentQueries.associatedApointments(cID);
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
                countryComboBox.setValue(c.getCountry());
                stateComboBox.setValue(c.getDivisionName());
            }
            catch (Exception e){
            e.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Please select a customer");
            alert.showAndWait();
            return;
        }
    }

    /** Country combo box on existing customer form. Filters the state combo box based off of country selection.
     * @throws SQLException SQLException
     * @param actionEvent action event
     */
    public void onCountry(ActionEvent actionEvent) throws SQLException {

        int cID = countryComboBox.getValue().getCountryId();
        ObservableList<Division> d =DivisionQueries.associatedDivisions(cID);
        stateComboBox.setItems(d);
    }

    /** Update button on existing customer form. Checks for invalid data, provides alerts, save updated customer to database. Provides confirmation of save.
     * Catches exceptions, prints stack trace. Alerts for error updating customer.
     * @param actionEvent action event
     */
    public void onUpdate(ActionEvent actionEvent) {
        if(nameTextBox.getText().isEmpty() || addressTextBox.getText().isEmpty() || postalCodeTextBox.getText().isEmpty() || phoneTextBox.getText().isEmpty() || stateComboBox.getValue() == null){
            Alert alert3 = new Alert (Alert.AlertType.ERROR);
            alert3.setTitle("ERROR");
            alert3.setHeaderText((null));
            alert3.setContentText("Cannot save until all entries are filled.");
            Optional<ButtonType> result = alert3.showAndWait();
        }
        else {
            try{
                int rowsAffected = CustomerQueries.updateCustomer(Integer.parseInt(idTextBox.getText()), nameTextBox.getText(), addressTextBox.getText(), postalCodeTextBox.getText(), phoneTextBox.getText(), stateComboBox.getValue().toString());
                if(rowsAffected >0){

                    Alert alert3 = new Alert (Alert.AlertType.CONFIRMATION);
                    alert3.setTitle("Confirmation");
                    alert3.setHeaderText((null));
                    alert3.setContentText("Updates applied.");
                    Optional<ButtonType> result = alert3.showAndWait();
                    if (alert3.getResult() == ButtonType.OK) {
                        Parent root = FXMLLoader.load(getClass().getResource("/view/ExistingCustomer.FXML"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    }
                }
                else{
                    Alert alert2 = new Alert (Alert.AlertType.ERROR);
                    alert2.setTitle("ERROR");
                    alert2.setHeaderText((null));
                    alert2.setContentText("Error updating customer.");
                    Optional<ButtonType> result = alert2.showAndWait();
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
        }
    }

    /** Delete associated appointment button on existing customer form. Deletes selected appointment, provides confirmation of appointment deleted, updates associated appointments table.
     * Catches exceptions, prints stack trace. Alerts if unable to delete.
     * @param actionEvent action event
     */
    public void onDeleteAppointment(ActionEvent actionEvent) {
        Appointment a = (Appointment) existingAppointmentsTable.getSelectionModel().getSelectedItem();

        if(a != null){
            Alert alert3 = new Alert (Alert.AlertType.CONFIRMATION);
            alert3.setTitle("Confirmation");
            alert3.setHeaderText((null));
            alert3.setContentText("Are you sure you want to delete this appointment?");
            Optional<ButtonType> result = alert3.showAndWait();
            if (alert3.getResult() == ButtonType.OK) {
                try{
                    int rowsAffected = AppointmentQueries.deleteAppointment(a.getAppointmentId());
                    if(rowsAffected >0){
                        Alert alert2 = new Alert (Alert.AlertType.INFORMATION);
                        alert2.setTitle("Confirmation");
                        alert2.setHeaderText((null));
                        alert2.setContentText("Appointment Id '"+ a.getAppointmentId()+" ' for '" +a.getType()+" ' has been deleted.");
                        Optional<ButtonType> result1 = alert2.showAndWait();
                        if (alert3.getResult() == ButtonType.OK) {
                            Parent root = FXMLLoader.load(getClass().getResource("/view/ExistingCustomer.FXML"));
                            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.show();
                        }
                    }
                    else{
                        Alert alert2 = new Alert (Alert.AlertType.ERROR);
                        alert2.setTitle("ERROR");
                        alert2.setHeaderText((null));
                        alert2.setContentText("Unable to delete appointment at this time.");
                        Optional<ButtonType> result2 = alert2.showAndWait();
                        if (alert3.getResult() == ButtonType.OK) {
                            Parent root = FXMLLoader.load(getClass().getResource("/view/ExistingCustomer.FXML"));
                            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                            stage.setScene(new Scene(root));
                            stage.show();
                        }
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        else{
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Select appointment");
            alert.setContentText("Please select an appointment to delete.");
            alert.showAndWait();
        }
    }

    /** Delete customer button on existing customer form. Deletes the customer from the database, provides confirmation, updates customer table.
     * Catches exceptions, prints stack trace. Alerts if no customer selected, or unable to delete.
     * @throws SQLException SQLException
     * @param actionEvent action event
     */
    public void onDeleteCustomer(ActionEvent actionEvent) throws SQLException {
        Customer c = existingCustomersTable.getSelectionModel().getSelectedItem();

        if(c != null){
            Alert alert3 = new Alert (Alert.AlertType.CONFIRMATION);
            alert3.setTitle("Confirmation");
            alert3.setHeaderText((null));
            alert3.setContentText("Are you sure you want to delete "+ c.getCustomerName() +"?");
            Optional<ButtonType> result = alert3.showAndWait();
            if (alert3.getResult() == ButtonType.OK) {
                Integer cID = existingCustomersTable.getSelectionModel().getSelectedItem().getCustomerId();
                ObservableList<Appointment> aAppointments = AppointmentQueries.associatedApointments(cID);
                if(aAppointments.isEmpty()){
                    try{
                        int rowsAffected = CustomerQueries.deleteCustomer(c.getCustomerId());
                        if(rowsAffected >0){
                            Alert alert2 = new Alert (Alert.AlertType.INFORMATION);
                            alert2.setTitle("Confirmation");
                            alert2.setHeaderText((null));
                            alert2.setContentText("Customer '"+ c.getCustomerName() +"' has been deleted.");
                            Optional<ButtonType> result1 = alert2.showAndWait();
                            if (alert2.getResult() == ButtonType.OK) {
                                Parent root = FXMLLoader.load(getClass().getResource("/view/ExistingCustomer.FXML"));
                                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                stage.setScene(new Scene(root));
                                stage.show();
                            }
                        }
                        else{
                            Alert alert2 = new Alert (Alert.AlertType.ERROR);
                            alert2.setTitle("ERROR");
                            alert2.setHeaderText((null));
                            alert2.setContentText("Unable to delete customer at this time.");
                            Optional<ButtonType> result2 = alert2.showAndWait();
                            if (alert2.getResult() == ButtonType.OK) {
                                Parent root = FXMLLoader.load(getClass().getResource("/view/ExistingCustomer.FXML"));
                                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                                stage.setScene(new Scene(root));
                                stage.show();
                            }
                        }
                    }
                    catch(Exception e){
                        e.printStackTrace();
                    }
                }
                else {
                    Alert alert2 = new Alert(Alert.AlertType.ERROR);
                    alert2.setTitle("ERROR: EXISTING APPOINTMENTS");
                    alert2.setHeaderText((null));
                    alert2.setContentText("Cannot delete customer with existing appointments");
                    Optional<ButtonType> result2 = alert2.showAndWait();

                    existingAppointmentsTable.setItems(aAppointments);
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
                    countryComboBox.setValue(c.getCountry());
                    stateComboBox.setValue(c.getDivisionName());
                }
            }
        }
        else{Alert alert2 = new Alert (Alert.AlertType.ERROR);
            alert2.setTitle("ERROR");
            alert2.setHeaderText((null));
            alert2.setContentText("Please select a customer to delete");
            Optional<ButtonType> result2 = alert2.showAndWait();
        }
    }


}
