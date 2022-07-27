package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public TextField userNameText;
    public TextField passwordText;
    public Label timeZoneLabel;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        timeZoneDisplay();

    }


    public void timeZoneDisplay(){

        String timeZone = ZoneId.systemDefault().toString();
        timeZoneLabel.setText(timeZone);
    }

    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/view/Login.FXML"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }


    public void onEnterButton(ActionEvent actionEvent) throws IOException, SQLException {

        String userName = userNameText.getText();
        String password = passwordText.getText();
        if(userName.isEmpty() || password.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Blank fields");
            alert.setContentText("User name and password cannot be blank.");
            alert.showAndWait();
            return;
        }

        else{
            String sql = "SELECT * FROM USERS WHERE USER_NAME = '" + userName + "'";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            if(resultSet.isBeforeFirst()) {

                while (resultSet.next()) {

                    if (resultSet.getString("User_Name").equals(userName) && resultSet.getString("Password").equals(password)) {
                        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.FXML"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } else {
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error");
                        alert.setHeaderText("Check user name and password");
                        alert.setContentText("User name or password not found.");
                        alert.showAndWait();
                        return;
                    }
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Check user name");
                alert.setContentText("User name not found.");
                alert.showAndWait();
                return;
            }
        }
    }
}
