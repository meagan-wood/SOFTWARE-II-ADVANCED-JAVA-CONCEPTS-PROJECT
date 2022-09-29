package controller;

import database.AppointmentQueries;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Appointment;


import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.ResourceBundle;



public class HomeScreen implements Initializable {

    public ToggleGroup toggleGroup;
    public RadioButton viewWeekRadio;
    public RadioButton viewMonthRadio;
    public RadioButton viewAllRadio;
    @FXML private TableView<Appointment> appointmentTableView;
    @FXML private TableColumn<Appointment, LocalDateTime> startTimeColumn;
    @FXML private TableColumn<Appointment, LocalDateTime> endTimeColumn;
    @FXML private TableColumn<Appointment, Integer> appointmentIdColumn;
    @FXML private TableColumn<Appointment, String>  titleColumn;
    @FXML private TableColumn<Appointment, String> descriptionColumn;
    @FXML private TableColumn<Appointment, String> locationColumn;
    @FXML private TableColumn<Appointment, Integer> contactColumn;
    @FXML private TableColumn<Appointment, String> typeColumn;
    @FXML private TableColumn<Appointment, Integer> customerIdColumn;
    @FXML private TableColumn<Appointment, Integer> userIdColumn;



    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        try {
            checkUpcomingAppointments();
            viewAllRadio.setSelected(true);
            appointmentTableView.setItems(AppointmentQueries.appointments());
            startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startDateTime"));
            endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("endDateTime"));
            appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
            titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
            descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
            locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
            contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactId"));
            typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
            customerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
            userIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));

        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }

    }


    public void onLogoutButton(ActionEvent actionEvent) throws IOException {
        Alert alert3 = new Alert (Alert.AlertType.CONFIRMATION);
        alert3.setTitle("Confirmation");
        alert3.setHeaderText((null));
        alert3.setContentText("Are you sure you want to log out?");
        Optional<ButtonType> result = alert3.showAndWait();
        if (alert3.getResult() == ButtonType.OK) {
            Parent root = FXMLLoader.load(getClass().getResource("/view/Login.FXML"));
            Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }
    }

    public void onAddNewCustomerButton(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/AddNewCustomer.FXML"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void onExistingCustButton(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/ExistingCustomer.FXML"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void onCreateAppButton(ActionEvent actionEvent) throws IOException{
        Parent root = FXMLLoader.load(getClass().getResource("/view/CreateAppointment.FXML"));
        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void onModifyAppointmentButton(ActionEvent actionEvent) throws IOException, SQLException {
        Appointment a = appointmentTableView.getSelectionModel().getSelectedItem();

        if (a == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Nothing Selected");
            alert.setContentText("Please select an appointment");
            alert.showAndWait();
            return;
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/ModifyAppointment.FXML"));
        Parent root = loader.load();

        ModifyAppointmentController MAppointmentController = loader.getController();
        MAppointmentController.sendAppointment(a);

        Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void onViewAll(ActionEvent actionEvent) throws SQLException {
        appointmentTableView.setItems(AppointmentQueries.appointments());
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startDateTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("endDateTime"));
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));
    }

    public void onViewWeek(ActionEvent actionEvent) throws SQLException {
        appointmentTableView.setItems(AppointmentQueries.appointmentsByWeek());
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startDateTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("endDateTime"));
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));
    }

    public void onViewMonth(ActionEvent actionEvent) throws SQLException {
        appointmentTableView.setItems(AppointmentQueries.appointmentsByMonth());
        startTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("startDateTime"));
        endTimeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, LocalDateTime>("endDateTime"));
        appointmentIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("appointmentId"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("title"));
        descriptionColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("description"));
        locationColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("location"));
        contactColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("contactId"));
        typeColumn.setCellValueFactory(new PropertyValueFactory<Appointment, String>("type"));
        customerIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("customerId"));
        userIdColumn.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("userId"));
    }

    public void onDelete(ActionEvent actionEvent) throws IOException {
        Appointment a = appointmentTableView.getSelectionModel().getSelectedItem();

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
                            Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.FXML"));
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
                            Parent root = FXMLLoader.load(getClass().getResource("/view/HomeScreen.FXML"));
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
            alert.setContentText("Please select an appointment to delete");
            alert.showAndWait();
        }
    }

private static boolean firstTime = true;

    private void checkUpcomingAppointments(){
        if(!firstTime){
            return;
        }
        firstTime = false;

        LocalDateTime loginTime = LocalDateTime.now();
        LocalDateTime loginTimePlus15 = LocalDateTime.now().plusMinutes(15);
       try {
           ObservableList<Appointment> appointmentsIn15 = FXCollections.observableArrayList();
           ObservableList<Appointment> appointmentList = AppointmentQueries.appointments();
           if (appointmentList != null) {
               for (Appointment upcomingAppointment : appointmentList) {
                   if (upcomingAppointment.getStartDateTime().isAfter(loginTime) && upcomingAppointment.getStartDateTime().isBefore(loginTimePlus15)) {
                       appointmentsIn15.add(upcomingAppointment);
                       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                       alert.setTitle("CONFIRMATION");
                       alert.setHeaderText("Upcoming Appointments");
                       alert.setContentText("Appointment coming up: " + "Appointment ID " +upcomingAppointment.getAppointmentId() + " on " + upcomingAppointment.getStartDateTime().toLocalDate() +" at " + upcomingAppointment.getStartDateTime().toLocalTime() + " - " + upcomingAppointment.getEndDateTime().toLocalTime());
                       alert.showAndWait();
                   }
                   if(upcomingAppointment.getStartDateTime().isBefore(loginTime) && upcomingAppointment.getEndDateTime().isAfter(loginTime)){
                       appointmentsIn15.add(upcomingAppointment);
                       Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                       alert.setTitle("CONFIRMATION");
                       alert.setHeaderText("Appointments in progress");
                       alert.setContentText("Appointment currently in progress: " + upcomingAppointment.getStartDateTime().toLocalTime() + " - " + upcomingAppointment.getEndDateTime().toLocalTime());
                       alert.showAndWait();
                   }

               }
           }
           if(appointmentsIn15.isEmpty()){
               Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
               alert.setTitle("CONFIRMATION");
               alert.setHeaderText("No Upcoming Appointments");
               alert.setContentText("No appointments in the next 15 minutes");
               alert.showAndWait();
           }
       }
        catch(Exception e){
           e.printStackTrace();
        }
    }
}
