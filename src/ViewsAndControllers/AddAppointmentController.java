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
public class AddAppointmentController implements Initializable {

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
    
    /**
     * **********************************
     * Changing screens and scenes with buttons.
  ***********************************
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
                
                int customerId=selCustomer.getCustomerId();
                int userId = Login.getLoggedInUserId();
                String title = titleText.getText();
                String description = descriptionText.getText();
                String location = "LocPHGetlocAddrID"+String.valueOf(selCustomer.getAddressId());
                String contact ="ContactPHGetPhoneAddrID"+String.valueOf(selCustomer.getAddressId());
                String type = typeSelection.getValue().toString();
                String url = urlText.getText();
                String start = startDateSelection.getValue().toString();
                String end = endDateSelection.getValue().toString();        
                String lastUpdate = timestamp.toString();
                String createdDate = timestamp.toString();
                String createdBy = Login.getLoggedInUser().getUserName();
                String lastUpdateby =  Login.getLoggedInUser().getUserName();
                  
                DatabaseConnect.createAppontment(customerId, userId, title,
                        description, location,contact, type, url, start, end,
                        lastUpdate, createdDate, createdBy, lastUpdateby);

                // Save the appointment 
                
                // Close the window 
            }
            catch (SQLException e){
                // possibly show a popup with a try again or cancel option
                
            System.out.println("error: "+ e);

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
            addressCol.setCellValueFactory(new PropertyValueFactory<>("addressId"));
            phoneCol.setCellValueFactory(new PropertyValueFactory<>("active"));
        
            typeSelection.getItems().addAll("Lunch", "Meeting", "Phone");
        
    }

    /*private boolean validateForm() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }*/

}
