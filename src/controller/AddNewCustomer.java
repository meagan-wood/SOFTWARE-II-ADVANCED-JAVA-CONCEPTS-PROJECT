package controller;

import javafx.collections.FXCollections;
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
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import java.util.ResourceBundle;

public class AddNewCustomer implements Initializable {

    public TextField nameText;
    public TextField phoneNumberText;
    public TextField addressText;
    public TextField postalCodeText;
    public TextField customerIdText;
    public ComboBox<Country>countryComboBox;
    public ComboBox stateComboBox;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            countryComboBox.setItems(countries());
            stateComboBox.setItems(divisions());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

    }
    public static ObservableList<Country> countries() throws SQLException {


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

    public static ObservableList<Division> divisions() throws SQLException {


        ObservableList<Division> divisionList = FXCollections.observableArrayList();
        try{
            String sql = "SELECT * FROM first_level_divisions";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while(resultSet.next()){
                String divisionName = resultSet.getString("Division");
                Integer divisionId = resultSet.getInt("Division_ID");
                Integer countryId = resultSet.getInt("Country_ID");
                Division newDivision = new Division(divisionId, divisionName, countryId);
                divisionList.add(newDivision);
            }
        }
        catch(SQLException ex) {
            ex.printStackTrace();
        }
        return divisionList;
    }


    public void onCancelButton(ActionEvent actionEvent) throws IOException {

        Alert alert3 = new Alert (Alert.AlertType.CONFIRMATION);
        //alert3.getDialogPane().getScene().getRoot().setStyle("-fx-font-family : 'Times New Roman';");
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


    public void onCountry(ActionEvent actionEvent) {


    }
}
