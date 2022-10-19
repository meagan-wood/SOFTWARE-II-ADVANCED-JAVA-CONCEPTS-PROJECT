package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Locale;
import java.util.ResourceBundle;

/** Login controller class. */
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

    /** Initialize Login form, checks default language, displays in correct language, displays timezone.
     * Catches exceptions, prints stacktrace
     * @param resourceBundle resource bundle
     * @param url url
     */
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

    /** Gets default timezone and displays in label
     */
    public void timeZoneDisplay(){

        String timeZone = ZoneId.systemDefault().toString();
        timeZoneLabel.setText(timeZone);
    }

    /** Cancel button on login form. Closes application.
     * @throws IOException IOException
     * @param actionEvent action event
     */
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        //Parent root = FXMLLoader.load(getClass().getResource("/view/Login.FXML"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.close();
    }

    /** Enter button on login form. Verifies username/password. Provides alerts in default language for incorrect/missing data. Create/appends login_activity.txt.
     * @throws  IOException IOException
     * @throws SQLException SQLException
     * @param actionEvent action event
     */
    public void onEnterButton(ActionEvent actionEvent) throws IOException, SQLException {
        ResourceBundle rb = ResourceBundle.getBundle("language/language", Locale.getDefault());
        String userName = userNameText.getText();
        String password = passwordText.getText();
        FileWriter file = new FileWriter("login_activity.txt", true);
        PrintWriter outputFile = new PrintWriter(file);
        SimpleDateFormat sDF = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        Timestamp dateTime = new Timestamp(System.currentTimeMillis());
        if(userName.isEmpty() || password.isEmpty()) {
            if (Locale.getDefault().getLanguage().equals("fr") || (Locale.getDefault().getLanguage().equals("en"))){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(rb.getString("error"));
            alert.setHeaderText(rb.getString("blankfields"));
            alert.setContentText(rb.getString("cannotbeblank"));
            ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(rb.getString("okay"));
            alert.showAndWait();
            //return;
            }
        }
        else{
            String sql = "SELECT * FROM USERS WHERE USER_NAME = '" + userName + "'";
            PreparedStatement ps = database.JDBC.connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            if(resultSet.isBeforeFirst()) {

                while (resultSet.next()) {

                    if (resultSet.getString("User_Name").equals(userName) && resultSet.getString("Password").equals(password)) {
                        outputFile.println("Successful login: " + userName + " logged in at " + sDF.format(dateTime)+ "\n" );
                        System.out.println("File Written");
                        Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.FXML"));
                        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } else {
                        outputFile.println("Denied login: " + userName + " attempted login at " + Timestamp.valueOf(LocalDateTime.now()) + "\n");
                        Alert alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle(rb.getString("error"));
                        alert.setHeaderText(rb.getString("check"));
                        alert.setContentText(rb.getString("notfound"));
                        ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(rb.getString("okay"));
                        alert.showAndWait();
                    }
                    outputFile.close();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle(rb.getString("error"));
                alert.setHeaderText(rb.getString("checkuser"));
                alert.setContentText(rb.getString("usernotfound"));
                ((Button) alert.getDialogPane().lookupButton(ButtonType.OK)).setText(rb.getString("okay"));
                alert.showAndWait();
            }
        }
    }
}
