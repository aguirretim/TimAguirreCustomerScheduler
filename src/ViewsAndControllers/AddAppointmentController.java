/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewsAndControllers;

import Model.Customer;
import Model.CustomerList;
import Model.DBConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class AddAppointmentController implements Initializable {

    /**
     * ***********************************
     * Variables for Buttons and Field. *********************************
     */
    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private ChoiceBox typeSelection;
    @FXML
    private ChoiceBox startTimeSelection;
    @FXML
    private ChoiceBox endTimeSelection;

    @FXML
    private TextField urlText;

    @FXML
    private DatePicker startDateSelection;

    @FXML
    private DatePicker endDateSelection;

    @FXML
    private TableView customerTable;

    @FXML
    private TableColumn customerNameCol;

    @FXML
    private TableColumn addressCol;

    @FXML
    private TableColumn phoneCol;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    //Stage setting variable for Button actions to select new stages to display
    Stage stage = new Stage();

    //The inventory object that contains all of the parts and product listed inside
    CustomerList customerData = new CustomerList();

    DBConnect DatabaseConnect = new DBConnect();

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    Locale mexicoLocale = new Locale("es", "MX");

    //Changes default language english to testing language 
    //Locale.setDefault(mexicoLocale);
    Locale currentLocale = Locale.getDefault();

    /**
     * ***********************************
     * Changing screens and scenes with buttons.
     * **********************************
     */
    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) cancelButton.getScene().getWindow();
        stage.hide();
    }

    @FXML
    private void saveButtonAction(ActionEvent event) throws IOException {

        System.out.println(timestamp.toString());

        // first try to validate the form
        /*if(!validateForm()) {
                // maybe popup a message with hint,
                //   and/or validation messages in form
                return;
            }*/
        // Now that we have a valid form try to save
        Customer selCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();

        try {

            int customerId = selCustomer.getCustomerId();
            int userId = Login.getLoggedInUserId();
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = String.valueOf(selCustomer.getAddressId());
            String contact = String.valueOf(selCustomer.getAddressId());
            String type = typeSelection.getValue().toString();
            String url = urlText.getText();

            String start = startDateSelection.getValue().toString();
            String startTime = timeConverter(startTimeSelection.getValue().toString());
            String startDateTime = start + " " + startTime;

            String end = endDateSelection.getValue().toString();
            String endTime = timeConverter(endTimeSelection.getValue().toString());
            String endDateTime = end + " " + endTime;

            String lastUpdate = timestamp.toString();
            String createdDate = timestamp.toString();
            String createdBy = Login.getLoggedInUser().getUserName();
            String lastUpdateby = Login.getLoggedInUser().getUserName();

            DatabaseConnect.createAppontment(customerId, userId, title,
                    description, location, contact, type, url, startDateTime, endDateTime,
                    lastUpdate, createdDate, createdBy, lastUpdateby);

            // Save the appointment 
            // Close the window 
            stage = (Stage) cancelButton.getScene().getWindow();
            stage.hide();
            HomeScreenController.getActiveHomeScreen().reinitialize();

        } catch (SQLException e) {
            // possibly show a popup with a try again or cancel option

            System.out.println("error: " + e);

        }

    }

    public String timeConverter(String Date) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("h:mm a");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

        return LocalTime.parse(Date, inputFormat).format(outputFormat);

    }

    public boolean validateTitle() {
        String title = titleText.getText();
        if (title.equals(null) || title.isEmpty()) {
            if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a title");
                alert.showAndWait();
                System.out.println("Please enter a title");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor ingrese un título");
                alert.showAndWait();
                System.out.println("Por favor ingrese un título");
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean validateDescription() {
        String description = descriptionText.getText();
        if (description.equals(null) || description.isEmpty()) {
            if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a description");
                alert.showAndWait();
                System.out.println("Please enter a description");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor ingrese una descripción");
                alert.showAndWait();
                System.out.println("Por favor ingrese una descripción");
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean validateType() {
        String Type = typeSelection.getValue().toString();
        if (Type.equals(null) || Type.isEmpty()) {
            if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please select a type");
                alert.showAndWait();
                System.out.println("Please select a type");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor seleccione un tipo");
                alert.showAndWait();
                System.out.println("Por favor seleccione un tipo");
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean validateUrl() {
        String UrlText = urlText.getText();
        try {
            URL url = new URL(UrlText);
            url.toURI();
            return true;
        } catch (Exception exception) {
            if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please enaer a  Valid type");
                alert.showAndWait();
                System.out.println("Please select a type");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor seleccione un tipo");
                alert.showAndWait();
                System.out.println("Por favor seleccione un tipo");
            }
            System.out.println("erro: " + exception);
            return false;
        }
        
    }
    
    public LocalDateTime dateTimeConverter(String Date) {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime.parse(Date, outputFormat);

    }
    
    public boolean validateStartDate() {
            String start = startDateSelection.getValue().toString();
            String startTime = timeConverter(startTimeSelection.getValue().toString());
            String startDateTime = start + " " + startTime;

            LocalDateTime startDate = dateTimeConverter(startDateTime);
            
            String end = endDateSelection.getValue().toString();
            String endTime = timeConverter(endTimeSelection.getValue().toString());
            String endDateTime = end + " " + endTime;
            
            LocalDateTime endDate = dateTimeConverter(endDateTime);
            
        
        if (startDateTime.equals(null) || startDateTime.isEmpty()) {
            if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please select a date");
                alert.showAndWait();
                System.out.println("Please select a date");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor seleccione una fecha");
                alert.showAndWait();
                System.out.println("Por favor seleccione una fecha");
            }
            return false;
        } 
        else if (startDate.isBefore(LocalDateTime.now())){
        
        if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please Select a Start date and "
                        + "time ahead of Current Date and Time");
                alert.showAndWait();
                System.out.println("Please Select a Start date and "
                        + "time ahead of Current Date and Time");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Seleccione una fecha y hora "
                        + "de inicio antes de la fecha y hora actuales");
                alert.showAndWait();
                System.out.println("Seleccione una fecha y hora "
                        + "de inicio antes de la fecha y hora actuales");
            }
            return false;
        }
        else if(startDate.isAfter(endDate)){
         if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please Select Start date and time"
                        + " Before of End Date and Time");
                alert.showAndWait();
                System.out.println("Please Select Start date and time"
                        + " Before of End Date and Time");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Seleccione la fecha y hora de"
                        + " inicio antes de la fecha y hora de finalización");
                alert.showAndWait();
                System.out.println("Seleccione la fecha y hora de"
                        + " inicio antes de la fecha y hora de finalización");
            }
            return false;
        }
        else {
            return true;
        }
    }
    
    public boolean validateEndDate() {
            String start = startDateSelection.getValue().toString();
            String startTime = timeConverter(startTimeSelection.getValue().toString());
            String startDateTime = start + " " + startTime;

            LocalDateTime startDate = dateTimeConverter(startDateTime);
            
            String end = endDateSelection.getValue().toString();
            String endTime = timeConverter(endTimeSelection.getValue().toString());
            String endDateTime = end + " " + endTime;
            
            LocalDateTime endDate = dateTimeConverter(endDateTime);
            
        
        if (endDateTime.equals(null) || endDateTime.isEmpty()) {
            if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please select a date");
                alert.showAndWait();
                System.out.println("Please select a date");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor seleccione una fecha");
                alert.showAndWait();
                System.out.println("Por favor seleccione una fecha");
            }
            return false;
        } 
        else if (endDate.isBefore(startDate)){
        
        if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please Select a End date and "
                        + "time ahead of Start Date and Time");
                alert.showAndWait();
                System.out.println("Please Select a End date and "
                        + "time ahead of Start Date and Time");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Seleccione una fecha y hora de"
                        + " finalización antes de la fecha y hora de inicio");
                alert.showAndWait();
                System.out.println("Seleccione una fecha y hora de"
                        + " finalización antes de la fecha y hora de inicio");
            }
            return false;
        }
        else {
            return true;
        }
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        this.customerData.clearCustomers();
        try {
            this.customerData.addCustomers(DatabaseConnect.getAllCustomers());
        } catch (Exception e) {
            // print some msg or popup some error alert box
            throw new UnsupportedOperationException(
                    "Homescreen initialize - caught Exception", e);
        }
        customerTable.setItems(customerData.getCustomer());

        customerNameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));
        phoneCol.setCellValueFactory(new PropertyValueFactory<>("phone"));

        typeSelection.getItems().addAll("Lunch", "Meeting", "Phone");

        startTimeSelection.getItems().addAll("12:00 AM", "12:30 AM",
                "1:00 AM", "1:30 AM",
                "2:00 AM", "2:30 AM",
                "3:00 AM", "3:30 AM",
                "4:00 AM", "4:30 AM",
                "5:00 AM", "5:30 AM",
                "6:00 AM", "6:30 AM",
                "7:00 AM", "7:30 AM",
                "8:00 AM", "8:30 AM",
                "9:00 AM", "9:30 AM",
                "10:00 AM", "10:30 AM",
                "11:00 AM", "11:30 AM",
                "12:00 PM", "12:30 PM",
                "1:00 PM", "1:30 PM",
                "2:00 PM", "2:30 PM",
                "3:00 PM", "3:30 PM",
                "4:00 PM", "4:30 PM",
                "5:00 PM", "5:30 PM",
                "6:00 PM", "6:30 PM",
                "7:00 PM", "7:30 PM",
                "8:00 PM", "8:30 PM",
                "9:00 PM", "9:30 PM",
                "10:00 PM", "10:30 PM",
                "11:00 PM", "11:30 PM");

        endTimeSelection.getItems().addAll("12:00 AM", "12:30 AM",
                "1:00 AM", "1:30 AM",
                "2:00 AM", "2:30 AM",
                "3:00 AM", "3:30 AM",
                "4:00 AM", "4:30 AM",
                "5:00 AM", "5:30 AM",
                "6:00 AM", "6:30 AM",
                "7:00 AM", "7:30 AM",
                "8:00 AM", "8:30 AM",
                "9:00 AM", "9:30 AM",
                "10:00 AM", "10:30 AM",
                "11:00 AM", "11:30 AM",
                "12:00 PM", "12:30 PM",
                "1:00 PM", "1:30 PM",
                "2:00 PM", "2:30 PM",
                "3:00 PM", "3:30 PM",
                "4:00 PM", "4:30 PM",
                "5:00 PM", "5:30 PM",
                "6:00 PM", "6:30 PM",
                "7:00 PM", "7:30 PM",
                "8:00 PM", "8:30 PM",
                "9:00 PM", "9:30 PM",
                "10:00 PM", "10:30 PM",
                "11:00 PM", "11:30 PM");

    }

    /*private boolean validateForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/
}
