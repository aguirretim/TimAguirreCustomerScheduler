/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewsAndControllers;

import Model.Appointment;
import Model.CustomerList;
import Model.DBConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class HomeScreenController implements Initializable {

    /**
     * **********************************
     * Variables for Buttons and Field. 
     **********************************
     */
    //The inventory object that contains all of the parts and product listed inside
    CustomerList customerData = new CustomerList();

    @FXML
    private TableView apptTable;

    @FXML
    private TableColumn apptCol;

    @FXML
    private TableColumn startCol;

    @FXML
    private TableColumn endCol;

    @FXML
    private TableColumn customerCol;

    @FXML
    private TableColumn addressCol;

    @FXML
    private Button customerButton;

    @FXML
    private Button addApptButton;

    @FXML
    private Button editApptButton;

    @FXML
    private Button delButton;

    @FXML
    private Button apptMonthReportButton;

    @FXML
    private Button allConsultanReportButton;

    @FXML
    private Button past30DayButton;

    Appointment selAppointment;

    private static HomeScreenController ActiveHomeScreen = null;

    public static HomeScreenController getActiveHomeScreen() {
        return ActiveHomeScreen;
    }

    public static void setActiveHomeScreen(HomeScreenController ActiveHomeScreen) {
        HomeScreenController.ActiveHomeScreen = ActiveHomeScreen;
    }

    Locale mexicoLocale = new Locale("es", "MX");

    //Changes default language english to testing language 
    //Locale.setDefault(mexicoLocale);
    Locale currentLocale = Locale.getDefault();

    //Stage setting variable for Button actions to select new stages to display
    Stage stage = new Stage();

    DBConnect DatabaseConnect = new DBConnect();

    /**
     * ***********************************
     * Changing screens and scenes with buttons.
     ***********************************
     */
    @FXML
    private void customerButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) customerButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/CustomerSceen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Tim Aguirre Customer Scheduler App");
        stage.setScene(scene);
    }

    @FXML
    private void addApptButtonAction(ActionEvent event) throws IOException {
        // stage = (Stage) addApptButton.getScene().getWindow();
        Stage modalStage = new Stage();
        modalStage.initOwner(stage);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/AddAppointment.fxml"));
        Scene scene = new Scene(root);
        modalStage.setTitle("Create New Appointment");
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    @FXML
    private void editApptButtonAction(ActionEvent event) throws IOException {

        try {
            FXMLLoader loader = new FXMLLoader(); //Loads an object hierarchy from an XML document.     
            loader.setLocation(getClass().getResource("/ViewsAndControllers/EditAppointment.fxml")); //  reference FXML files like this in my controllers                 
            loader.load();

            EditAppointmentController eac = loader.getController();

            Parent editAppointmentWindow = loader.getRoot();

            selAppointment = (Appointment) apptTable.getSelectionModel().getSelectedItem();

            eac.transferData(selAppointment.getAppointmentId(), selAppointment.getTitle(),
                    selAppointment.getDescription(), selAppointment.getType(),
                    selAppointment.getUrl(), selAppointment.getStart(), selAppointment.getEnd());

            System.out.println(selAppointment.getAppointmentId() + selAppointment.getTitle()
                    + selAppointment.getDescription() + selAppointment.getType()
                    + selAppointment.getUrl() + selAppointment.getStart() + selAppointment.getEnd());

            Scene scene = new Scene(editAppointmentWindow);
            stage.setTitle("Edit and Modify Appointment");
            stage.setScene(scene);
            stage.showAndWait();
        } /* stage = (Stage) editApptButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/EditAppointment.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle();
        stage.setScene(scene);*/ catch (NullPointerException ex) {
            if (currentLocale != mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please Select an Appointment to edit");
                alert.showAndWait();
                System.out.println("Please Select an Appointment to edit");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Seleccione una cita para editar");
                alert.showAndWait();
                System.out.println("Seleccione una cita para editar");
            }
            System.out.println("erro: " + ex);

        }
    }

    @FXML
    private void delButtonButtonAction(ActionEvent event) throws IOException, SQLException {
        try {
            selAppointment = (Appointment) apptTable.getSelectionModel().getSelectedItem();
            int apptId = selAppointment.getAppointmentId();

            DatabaseConnect.delAppointment(apptId);
            reinitialize();

            //  Block of code to try
        } catch (NullPointerException ex) {
            if (currentLocale != mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please Select an Appointment to delete");
                alert.showAndWait();
                System.out.println("Please Select an Appointment to delete");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor seleccione una cita para eliminar");
                alert.showAndWait();
                System.out.println("Por favor seleccione una cita para eliminar");
            }
            System.out.println("erro: " + ex);

        }

    }

//Timestamp T = java.sql.Timestamp.valueOf("2019-01-23 12:00:00");
//Timestamp EN = java.sql.Timestamp.valueOf("2019-01-23 14:00:00");        
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        setActiveHomeScreen(this);
        reinitialize();
    }

    public void reinitialize() {
        if (Login.isLoggedIn()) {
            //Clears the table to refresh it
            this.customerData.clearAppointments();
            try {
                //Adds appointments to table list by User who is logged in ID
                this.customerData.addAppointments(
                        DatabaseConnect.getAllAppointmentsByUserId(Login.getLoggedInUserId()));

            } catch (SQLException e) {
                // print some msg or popup some error alert box
                throw new UnsupportedOperationException(
                        "Homescreen initialize - caught Exception", e
                );
            }

            apptTable.setItems(customerData.getAppointment());

            int cusId;

            // customerData.getAppointment().forEach(appointment-> getCustomerName(appointment.getCustomerId()));
            customerCol.setCellValueFactory(new PropertyValueFactory<>("CustomerName"));
            apptCol.setCellValueFactory(new PropertyValueFactory<>("title"));
            addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
            startCol.setCellValueFactory(new PropertyValueFactory<>("start"));
            endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
        } else {
            // prompt the user to log in
            throw new UnsupportedOperationException(
                    "Homescreen initialize - user not logged in - fixme force login"
            );
        }

    }
}
