package controller;

import database.CountryQueries;
import database.CustomerQueries;
import database.DivisionQueries;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import model.Country;
import model.Customer;
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

/** Add new customer controller class. */
public class AddNewCustomer implements Initializable {

    public TextField nameText;
    public TextField phoneNumberText;
    public TextField addressText;
    public TextField postalCodeText;
    public TextField customerIdText;
    public ComboBox<Country> countryComboBox;
    public ComboBox stateComboBox;

    /** Initialize new customer form, loads the country and state combo boxes.
     * Catches exceptions, prints stacktrace
     * @param resourceBundle resource bundle
     * @param url url
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            countryComboBox.setItems(CountryQueries.getCountries());
            stateComboBox.setItems(DivisionQueries.getDivisions());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }


    /** Cancel button on add customer form. Verifies you wish to cancel and lose unsaved data, returns to main form
     * @throws IOException IOException
     * @param actionEvent clicked button
    */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {

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

    /** LAMBDA EXPRESSION, filters division combo box by country selection.
     * Filters state/province combo box. Sets state/province combo box with filtered list associated with the country selected from country combo box.
     * @throws SQLException SQLException
     * @param actionEvent button clicked
     */
    public void onCountry(ActionEvent actionEvent) throws SQLException {
        Integer cID = countryComboBox.getSelectionModel().getSelectedItem().getCountryId();
        //ObservableList<Division> d = DivisionQueries.associatedDivisions(cID);
        ObservableList<Division> divisionsList = DivisionQueries.getDivisions();
        ObservableList<Division> filteredD = divisionsList.filtered(d ->{
            if(d.getCountryId() == cID)
                return true;
            return false;
        });
        stateComboBox.setItems(filteredD);
    }

    /** Saves new customer data to database. Verifies data, gives error popups for any fields left blank or invalid data,
     * provides confirmation for successful save, provides error if unable to save.
     * catches exceptions, prints stacktrace
     * @throws SQLException SQLException
     * @param actionEvent button clicked
     */
    public void onSaveCustomer(ActionEvent actionEvent) throws SQLException {
        if(nameText.getText().isEmpty() || addressText.getText().isEmpty() || postalCodeText.getText().isEmpty() || phoneNumberText.getText().isEmpty() || stateComboBox.getValue() == null){
            Alert alert3 = new Alert (Alert.AlertType.ERROR);
            alert3.setTitle("ERROR");
            alert3.setHeaderText((null));
            alert3.setContentText("All boxes must be filled before you can save.");
            Optional<ButtonType> result = alert3.showAndWait();
        }
        else {
             try{
                 int rowsAffected = CustomerQueries.insertCustomer(nameText.getText(), addressText.getText(), postalCodeText.getText(), phoneNumberText.getText(), stateComboBox.getValue().toString());
                 if(rowsAffected >0){

                     Alert alert3 = new Alert (Alert.AlertType.CONFIRMATION);
                     alert3.setTitle("Confirmation");
                     alert3.setHeaderText((null));
                     alert3.setContentText("New customer has been saved.");
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
                     alert2.setContentText("Error saving customer.");
                     Optional<ButtonType> result = alert2.showAndWait();
                 }
             }
             catch(Exception e){
                 e.printStackTrace();
            }
        }
    }
}
