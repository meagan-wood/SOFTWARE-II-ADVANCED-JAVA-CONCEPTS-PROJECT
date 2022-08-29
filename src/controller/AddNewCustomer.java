package controller;

import database.CountryQueries;
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
import model.Division;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddNewCustomer implements Initializable {

    public TextField nameText;
    public TextField phoneNumberText;
    public TextField addressText;
    public TextField postalCodeText;
    public TextField customerIdText;
    public ComboBox<Country> countryComboBox;
    public ComboBox stateComboBox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            countryComboBox.setItems(CountryQueries.getCountries());
            stateComboBox.setItems(DivisionQueries.getDivisions());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }



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


    public void onCountry(ActionEvent actionEvent) throws SQLException {
        Integer cID = countryComboBox.getSelectionModel().getSelectedItem().getCountryId();
        ObservableList<Division> d = DivisionQueries.associatedDivisions(cID);
        stateComboBox.setItems(d);
    }


}
