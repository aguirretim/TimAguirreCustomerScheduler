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
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
public class EditAppointmentController implements Initializable {

    /**
     * **********************************
     * Variables for Buttons and Field.
  ***********************************
     */
    @FXML
    private TextField titleText;

    @FXML
    private TextField descriptionText;

    @FXML
    private ChoiceBox typeSelection;

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

    int apptId;

    /**
     * **********************************
     * Changing screens and scenes with buttons.
  ***********************************
     */
    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) cancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/HomeScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Tim Aguirre Customer Scheduler App");
        stage.setScene(scene);

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
            int appointmentId = apptId;
            String title = titleText.getText();
            String description = descriptionText.getText();
            String location = String.valueOf(selCustomer.getAddressId());
            String contact = String.valueOf(selCustomer.getAddressId());
            String type = typeSelection.getValue().toString();
            String url = urlText.getText();
            String start = startDateSelection.getValue().toString();
            String end = endDateSelection.getValue().toString();
            String lastUpdate = timestamp.toString();
            String createdDate = timestamp.toString();
            String createdBy = Login.getLoggedInUser().getUserName();
            String lastUpdateby = Login.getLoggedInUser().getUserName();

            /*  public void editAppointment(int appointmentId, String title,
            String description, String type, String url, String start, String end,
            String lastUpdate,String lastUpdateBy )throws SQLException {*/
            DatabaseConnect.editAppointment(appointmentId, title,
                    description, type, url, start, end,
                    lastUpdate, lastUpdateby);

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

    public String dateConverter(String Date) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        return LocalDate.parse(Date, inputFormat).format(outputFormat);

    }

    public void transferData(
            int appointmentId,
            String title, String description, String type,
            String url, String start, String end) {

        DateTimeFormatter Formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        start = start.trim();
        end = end.trim();
        LocalDate result = LocalDate.parse(dateConverter(start), format);
        LocalDate result2 = LocalDate.parse(dateConverter(end), format);

        titleText.setText(String.valueOf(title));
        descriptionText.setText(String.valueOf(description));
        typeSelection.setValue(String.valueOf(type));
        urlText.setText(String.valueOf(url));
        startDateSelection.setValue(result);
        endDateSelection.setValue(result2);
        int apptId = appointmentId;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        typeSelection.getItems().addAll("Lunch", "Meeting", "Phone");

        /*startTimeSelection.getItems().addAll("12:00 AM", "12:30 AM",
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
         */
    }
}
