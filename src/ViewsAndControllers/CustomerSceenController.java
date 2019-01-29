/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewsAndControllers;

import Model.CustomerList;
import Model.DBConnect;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
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
public class CustomerSceenController implements Initializable {
 
    
    
  /************************************
  * Variables for Buttons and Field.
  ************************************/
    
 //The inventory object that contains all of the parts and product listed inside
 CustomerList customerData = new CustomerList();
    
 @FXML
 private Button apptButton;
   
 @FXML
 private Button addCustomerButton;
 
 @FXML
 private Button editCustomerButton;
 
 @FXML
 private Button delButton;
 
 @FXML 
 private TableView customerTable;
 
 @FXML
 private TableColumn customerCol;

 @FXML
 private TableColumn addressCol;

 @FXML
 private TableColumn phoneCol;
  
//Stage setting variable for Button actions to select new stages to display
 Stage stage = new Stage();
 
 DBConnect DatabaseConnect = new DBConnect();
 
  /************************************
  * Changing screens and scenes with buttons.
  ************************************/ 
 
 
 
 @FXML
 private void apptButtonAction(ActionEvent event) throws IOException {
  stage=(Stage)apptButton.getScene().getWindow();
  Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/HomeScreen.fxml"));
  Scene scene = new Scene(root);
  stage.setTitle("Tim Aguirre Customer Scheduler App");
  stage.setScene(scene);
  }
 
 @FXML
 private void addCustomerButtonAction(ActionEvent event) throws IOException {
  stage=(Stage)addCustomerButton.getScene().getWindow();
  Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/AddCustomer.fxml"));
  Scene scene = new Scene(root);
  stage.setTitle("Create A New Customer");
  stage.setScene(scene);
 }
 
 @FXML
 private void editCustomerButtonAction(ActionEvent event) throws IOException {
  stage=(Stage)editCustomerButton.getScene().getWindow();
  Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/EditCustomer.fxml"));
  Scene scene = new Scene(root);
  stage.setTitle("Edit and Modify Customer");
  stage.setScene(scene);
 }
 
 
 
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        customerTable.setItems(customerData.getCustomer()); 

customerCol.setCellValueFactory(new PropertyValueFactory<>("customerName")); 
phoneCol.setCellValueFactory(new PropertyValueFactory<>("title")); 
addressCol.setCellValueFactory(new PropertyValueFactory<>("addressId"));
        
        
    }    
    
}
