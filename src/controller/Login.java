package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

public class Login implements Initializable {

    public TextField userNameText;
    public TextField passwordText;
    public Label timeZoneLabel;
    public Label usernameLabel;
    public Label passwordLabel;
    public Button cancelButton;
    public Button enterButton;
    public Label loginLabel;
    //private ResourceBundle rb;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());

        if(Locale.getDefault().getLanguage().equals("fr") || (Locale.getDefault().getLanguage().equals("en"))){
            loginLabel.setText(rb.getString("login"));
            usernameLabel.setText(rb.getString("username"));
            passwordLabel.setText(rb.getString("password"));
            cancelButton.setText(rb.getString("cancel"));
            enterButton.setText(rb.getString("enter"));
        }
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
        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        String userName = userNameText.getText();
        String password = passwordText.getText();
        if(userName.isEmpty() || password.isEmpty()) {
            if (Locale.getDefault().getLanguage().equals("fr") || (Locale.getDefault().getLanguage().equals("en"))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("error"));
            alert.setHeaderText(rb.getString("blankfields"));
            alert.setContentText(rb.getString("cannotbeblank"));
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(rb.getString("okay"));
            alert.showAndWait();
            //Return;
            }
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
                        alert.setTitle(rb.getString("error"));
                        alert.setHeaderText(rb.getString("check"));
                        alert.setContentText(rb.getString("notfound"));
                        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(rb.getString("okay"));
                        alert.showAndWait();
                        return;
                    }
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("error"));
                alert.setHeaderText(rb.getString("checkuser"));
                alert.setContentText(rb.getString("usernotfound"));
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(rb.getString("okay"));
                alert.showAndWait();
                return;
            }
        }
    }
}
