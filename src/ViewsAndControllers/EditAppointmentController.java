/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewsAndControllers;

import Model.Appointment;
import Model.Customer;
import Model.CustomerList;
import Model.DBConnect;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
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
public class EditAppointmentController implements Initializable {

    /**
     * **********************************
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

    int apptId;

    Locale mexicoLocale = new Locale("es", "MX");

    //Changes default language english to testing language 
    //Locale.setDefault(mexicoLocale);
    Locale currentLocale = Locale.getDefault();

    /**
     * **********************************
     * Changing screens and scenes with buttons.
     * **********************************
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
    private void saveButtonAction(ActionEvent event) throws IOException, ParseException {

        System.out.println(timestamp.toString());

        if (validateAllEdit()) {
            try {

                //          int customerId = selCustomer.getCustomerId();
                int userId = Login.getLoggedInUserId();
                int appointmentId = apptId;
                String title = titleText.getText();
                String description = descriptionText.getText();
                //        String location = String.valueOf(selCustomer.getAddressId());
                //      String contact = String.valueOf(selCustomer.getAddressId());
                String type = typeSelection.getValue().toString();
                String url = urlText.getText();
                String start = startDateSelection.getValue().toString();
                String startTime = validTimeConverter(startTimeSelection.getValue().toString());
                String startDateTime = start + " " + startTime;
                String end = endDateSelection.getValue().toString();
                String endTime = validTimeConverter(endTimeSelection.getValue().toString());
                String endDateTime = end + " " + endTime;
                String lastUpdate = timestamp.toString();
                String createdDate = timestamp.toString();
                String createdBy = Login.getLoggedInUser().getUserName();
                String lastUpdateby = Login.getLoggedInUser().getUserName();

                /*  public void editAppointment(int appointmentId, String title,
            String description, String type, String url, String start, String end,
            String lastUpdate,String lastUpdateBy )throws SQLException {*/
                DatabaseConnect.editAppointment(appointmentId, title,
                        description, type, url, startDateTime, endDateTime,
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
    }

    public Boolean validateAllEdit() throws ParseException {
        if (validateTitle()
                && validateDescription()
                && validateType()
                && validateUrl()
                && validateStartDate()
                && validateEndDate()
                && validateBuissnessHours()
                && validateOverlapAppt()) {
            return true;
        } else {
            // some other code
            return false;
        }
    }

    public Boolean validateOverlapAppt() {
        String start = startDateSelection.getValue().toString();
        String startTime = gTimeConverter(startTimeSelection.getValue().toString());
        String startDateTime = start + " " + startTime;

        LocalDateTime startDate = dateTimeConverter(startDateTime);

        String end = endDateSelection.getValue().toString();
        String endTime = gTimeConverter(endTimeSelection.getValue().toString());
        String endDateTime = end + " " + endTime;

        LocalDateTime endDate = dateTimeConverter(endDateTime);  Boolean isValid = true;
        
        System.out.println("the size of the customer data list" + customerData.getAppointment().size());
        for (Appointment var : customerData.getAppointment()) 
        {
            LocalDateTime apptStartDate = dateTimeConverter(var.getStart());
            LocalDateTime apptEndDate = dateTimeConverter(var.getEnd());

            System.out.println("StartDate from Form " + startDate);
            System.out.println("EndDate from Form " + endDate);
            System.out.println("StartDate from the Table " + apptStartDate);
            System.out.println("EndDate from the Table " + apptEndDate);

            if ((startDate.isAfter(apptStartDate)
                    && startDate.isBefore(apptEndDate))
                    || (endDate.isAfter(apptStartDate)
                    && endDate.isBefore(apptEndDate))) {
                isValid=false;
                if (currentLocale != mexicoLocale) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Sorry looks like you have an overlapping "
                            + "appointment please select an appointment"
                            + " that does not conflict with " + var.getTitle() + " "
                            + var.getDescription() + " Start Time: " + var.getStart()
                            + " End Time: " + var.getEnd());
                    alert.showAndWait();
                    System.out.println("Sorry looks like you have an overlapping "
                            + "appointment please select an appointment"
                            + " that does not conflict with " + var.getTitle() + " "
                            + var.getDescription() + " Start Time: " + var.getStart()
                            + " End Time: " + var.getEnd());
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Lo siento parece que tienes una superposición"
                            + "cita por favor seleccione una cita"
                            + " que no está en conflicto con " + var.getTitle() + " "
                            + var.getDescription() + " Hora de inicio" + var.getStart()
                            + " Hora de finalización:" + var.getEnd());
                    alert.showAndWait();
                    System.out.println("Lo siento parece que tienes una superposición"
                            + "cita por favor seleccione una cita"
                            + " que no está en conflicto con " + var.getTitle() + " "
                            + var.getDescription() + " Hora de inicio" + var.getStart()
                            + " Hora de finalización:" + var.getEnd());
                }
                
            }
        }
        return isValid;
    }
    
    public Boolean validateBuissnessHours() throws ParseException {
        String start = startDateSelection.getValue().toString();
        String startTime = gTimeConverter(startTimeSelection.getValue().toString());
        String startDateTime = start + " " + startTime;

        LocalDateTime startDate = dateTimeConverter(startDateTime);

        String end = endDateSelection.getValue().toString();
        String endTime = gTimeConverter(endTimeSelection.getValue().toString());
        String endDateTime = end + " " + endTime;

        LocalDateTime endDate = dateTimeConverter(endDateTime);

        SimpleDateFormat parser = new SimpleDateFormat("HH:mm");
        Date nineAm = parser.parse("09:00");
        Date fivePm = parser.parse("17:00");

        try {
            Date userSDate = parser.parse(startTime);
            Date userEDate = parser.parse(endTime);
            if ((userSDate.after(nineAm) || userSDate.equals(nineAm))
                    && (userEDate.before(fivePm)
                    || userEDate.equals(fivePm))) {
                return true;
            } else {
                if (currentLocale != mexicoLocale) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please Select a start time and end time "
                            + "within buissness Hours of 9am to 5pm");
                    alert.showAndWait();
                    System.out.println("Please Select a start time and end time "
                            + "within buissness Hours of 9am to 5pm");
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Seleccione una hora de inicio y una hora "
                            + "de finalización dentro del horario "
                            + "comercial de 9 a. M. A 5 p. M.");
                    alert.showAndWait();
                    System.out.println("Seleccione una hora de inicio y una hora de "
                            + "finalización dentro del "
                            + "horario comercial de 9 a. M. A 5 p. M.");
                }
                return false;
            }

        } catch (ParseException e) {
            // Invalid date was entered
            System.out.println("error: " + e);
            return false;
        }

    }
    
    
    public boolean validateTitle() {
        String title = titleText.getText();
        if (title.equals(null) || title.isEmpty()) {
            if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor ingrese un título");
                alert.showAndWait();
                System.out.println("Por favor ingrese un título");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a title");
                alert.showAndWait();
                System.out.println("Please enter a title");
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
                alert.setContentText("Por favor ingrese una descripción");
                alert.showAndWait();
                System.out.println("Por favor ingrese una descripción");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a description");
                alert.showAndWait();
                System.out.println("Please enter a description");
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean validateType() {
        String Type = typeSelection.getValue().toString();

        if (Type.equals(null) || Type.isEmpty()) {
            if (currentLocale != mexicoLocale) {
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
        if (!UrlText.isEmpty()) {
            try {
                URL url = new URL("http://" + UrlText);
                URLConnection conn = url.openConnection();
                conn.connect();
                return true;

            } catch (MalformedURLException e) {
                // the URL is not in a valid form
                if (currentLocale != mexicoLocale) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please enter a Valid Url");
                    alert.showAndWait();
                    System.out.println("Please enter a Valid Url");
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Por favor seleccione un tipo");
                    alert.showAndWait();
                    System.out.println("Por favor seleccione un tipo");
                }
                return false;
            } catch (IOException e) {
                // the connection couldn't be established
                if (currentLocale != mexicoLocale) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please enter a Valid Url");
                    alert.showAndWait();
                    System.out.println("Please enter a Valid Url");
                } else {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Por favor seleccione un tipo");
                    alert.showAndWait();
                    System.out.println("Por favor seleccione un tipo");
                }
                return false;
            } catch (Exception exception) {
                if (currentLocale != mexicoLocale) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Dialog");
                    alert.setHeaderText("Error");
                    alert.setContentText("Please enter a Valid Url");
                    alert.showAndWait();
                    System.out.println("Please enter a Valid Url");
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
        } else {
            return true;
        }
    }

    public boolean validateStartDate() {
        String start;
        String startTime;
        String end;
        String endTime;
        try {
            //  Block of code to try
            start = startDateSelection.getValue().toString();
        } catch (NullPointerException e) {
            //  Block of code to handle errors
            System.out.println("error: " + e);
            if (currentLocale != mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please select a Start date");
                alert.showAndWait();
                System.out.println("Please select a Start date");
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
        try {
            startTime = validTimeConverter(startTimeSelection.getValue().toString());
        } catch (NullPointerException e) {
            //  Block of code to handle errors
            if (currentLocale != mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please select a Start Time");
                alert.showAndWait();
                System.out.println("Please select a Start Time");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor seleccione una hora");
                alert.showAndWait();
                System.out.println("Por favor seleccione una hora");
            }
            System.out.println("error: " + e);
            return false;
        }
        try {
            //  Block of code to try
            end = endDateSelection.getValue().toString();
        } catch (NullPointerException e) {
            //  Block of code to handle errors
            System.out.println("error: " + e);
            if (currentLocale != mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please select a End date");
                alert.showAndWait();
                System.out.println("Please select a End date");
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
        try {
            endTime = validTimeConverter(endTimeSelection.getValue().toString());
        } catch (NullPointerException e) {
            //  Block of code to handle errors
            if (currentLocale != mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please select a End Time");
                alert.showAndWait();
                System.out.println("Please select a End Time");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor seleccione una hora");
                alert.showAndWait();
                System.out.println("Por favor seleccione una hora");
            }
            System.out.println("error: " + e);
            return false;
        }

        String startDateTime = start + " " + startTime;

        LocalDateTime startDate = dateTimeConverter(startDateTime);

        String endDateTime = end + " " + endTime;

        LocalDateTime endDate = dateTimeConverter(endDateTime);

        if (startDateTime.equals(null) || startDateTime.isEmpty()) {
            if (currentLocale != mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please select a date");
                alert.showAndWait();
                System.out.println("Please select a  date");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor seleccione una fecha");
                alert.showAndWait();
                System.out.println("Por favor seleccione una fecha");
            }
            return false;
        } else if (startDate.isBefore(LocalDateTime.now())) {

            if (currentLocale != mexicoLocale) {
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
        } else if (startDate.isAfter(endDate)) {
            if (currentLocale != mexicoLocale) {
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
        } else {
            return true;
        }
    }

    public boolean validateEndDate() {
        String start = startDateSelection.getValue().toString();
        String startTime = validTimeConverter(startTimeSelection.getValue().toString());
        String startDateTime = start + " " + startTime;

        LocalDateTime startDate = dateTimeConverter(startDateTime);

        String end = endDateSelection.getValue().toString();
        String endTime = validTimeConverter(endTimeSelection.getValue().toString());
        String endDateTime = end + " " + endTime;

        LocalDateTime endDate = dateTimeConverter(endDateTime);

        if (endDateTime.equals(null) || endDateTime.isEmpty()) {
            if (currentLocale != mexicoLocale) {
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
        } else if (endDate.isBefore(startDate)) {

            if (currentLocale != mexicoLocale) {
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
        } else {
            return true;
        }
    }

    public LocalDateTime dateTimeConverter(String Date) {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalDateTime.parse(Date, outputFormat);

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

        String startTime = timeConverter(start);
        String endTime = timeConverter(end);

        startTimeSelection.setValue(String.valueOf(startTime));
        endTimeSelection.setValue(String.valueOf(endTime));

        apptId = appointmentId;

    }

    public String timeConverter(String Date) {
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("h:mm a");
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        return LocalTime.parse(Date, inputFormat).format(outputFormat);

    }
    
       public String gTimeConverter(String Date) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("h:mm a");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

        return LocalTime.parse(Date, inputFormat).format(outputFormat);

    }

    public String validTimeConverter(String Date) {
        DateTimeFormatter inputFormat = DateTimeFormatter.ofPattern("h:mm a");
        DateTimeFormatter outputFormat = DateTimeFormatter.ofPattern("HH:mm:ss");

        return LocalTime.parse(Date, inputFormat).format(outputFormat);

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

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
}
