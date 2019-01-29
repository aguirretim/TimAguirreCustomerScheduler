/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewsAndControllers;

import Model.Appointment;
import Model.CustomerList;
import Model.Customer;
import Model.DBConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tim
 */


public class HomeScreenController implements Initializable {
    
    
    
 /************************************
  * Variables for Buttons and Field.
  ************************************/
 
    
    
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
    
 //Stage setting variable for Button actions to select new stages to display
 Stage stage = new Stage();

 
 DBConnect DatabaseConnect = new DBConnect();
 /************************************
  * Changing screens and scenes with buttons.
  ************************************/

 
 
 @FXML
 private void customerButtonAction(ActionEvent event) throws IOException {
  stage=(Stage)customerButton.getScene().getWindow();
  Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/CustomerSceen.fxml"));
  Scene scene = new Scene(root);
  stage.setTitle("Tim Aguirre Customer Scheduler App");
  stage.setScene(scene);
 }

  @FXML
 private void addApptButtonAction(ActionEvent event) throws IOException {
  stage=(Stage)addApptButton.getScene().getWindow();
  Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/AddAppointment.fxml"));
  Scene scene = new Scene(root);
  stage.setTitle("Create New Appointment");
  stage.setScene(scene);
 }
  
  @FXML
  private void editApptButtonAction(ActionEvent event) throws IOException {
  stage=(Stage)editApptButton.getScene().getWindow();
  Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/EditAppointment.fxml"));
  Scene scene = new Scene(root);
  stage.setTitle("Edit and Modify Appointment");
  stage.setScene(scene);
 }
 
//Timestamp T = java.sql.Timestamp.valueOf("2019-01-23 12:00:00");
//Timestamp EN = java.sql.Timestamp.valueOf("2019-01-23 14:00:00");        

 @Override
 public void initialize(URL url, ResourceBundle rb) {
  // TODO
  
  
  
  
//  customerData.addCustomer(new Customer(1, "Will Smith", 2, 2, "Bruce Lee", T, "Jet li"));
  
  //customerData.addAppointment(new Appointment(0, 0, 0, "Meeting about new movie", "Discuss about script for new movie", "Seattle", "Agent J", "Meeting", "willsmith.com", "oct 1", "Dec 2", "Sony Pictures", "Nov 8", "Tim Aguirre"));
  
  
DatabaseConnect.getAppointmentInfo();
  
  
  /*initInventory.addPart(new Inhouse(
     Integer.parseInt(
      machineidText.getText()),
     partID,
     nameText.getText(),
     Double.parseDouble(pricecostText.getText()),
     Integer.parseInt(invText.getText()),
     Integer.parseInt(minText.getText()),
     Integer.parseInt(maxText.getText())));*/
  apptTable.setItems(customerData.getAppointment()); 

customerCol.setCellValueFactory(new PropertyValueFactory<>("customerId")); 
apptCol.setCellValueFactory(new PropertyValueFactory<>("title")); 
addressCol.setCellValueFactory(new PropertyValueFactory<>("location"));
startCol.setCellValueFactory(new PropertyValueFactory<>("start" ));
endCol.setCellValueFactory(new PropertyValueFactory<>("end"));
 }
 
 
}