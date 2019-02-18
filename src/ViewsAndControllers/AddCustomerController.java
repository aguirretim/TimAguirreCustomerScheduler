/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewsAndControllers;

import Model.DBConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class AddCustomerController implements Initializable {

    /*************************************
     * Variables for Buttons and Field.
     ************************************/
    
    
    
    @FXML
    private TextField customerNameText;

    @FXML
    private TextField addressText;

    @FXML
    private TextField address2Text;

    @FXML
    private ChoiceBox citySelection;

    @FXML
    private TextField zipCodeText;

    @FXML
    private TextField phoneText;

    @FXML
    private Button saveButton;

    @FXML
    private Button cancelButton;

    //Stage setting variable for Button actions to select new stages to display
    Stage stage = new Stage();

     DBConnect DatabaseConnect = new DBConnect();
     
     Timestamp timestamp = new Timestamp(System.currentTimeMillis());
    
    /*************************************
     * Changing screens and scenes with buttons.
     ************************************/
    
    
    
    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) cancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/CustomerSceen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Tim Aguirre Customer Scheduler App");
        stage.setScene(scene);
    }

    @FXML
    private void saveButtonAction(ActionEvent event) throws IOException {
        try {              
                int userId = Login.getLoggedInUserId();
                String customerName = customerNameText.getText();
                int addressId;
                String address = addressText.getText();
                String address2= address2Text.getText();
                int active=1;
                int city = Integer.parseInt(citySelection.getValue().toString()) ;
                String zipCode = zipCodeText.getText();
                String phone = phoneText.getText();
                        
                String lastUpdate = timestamp.toString();
                String createdDate = timestamp.toString();
                String createdBy = Login.getLoggedInUser().getUserName();
                String lastUpdateby =  Login.getLoggedInUser().getUserName();
                
                /*public void createAddress(String address, String address2, int cityId,
            String postalCode, String phone, String createDate,
            String createdBy, String lastUpdate, String lastUpdateBy)*/
                        
                DatabaseConnect.createAddress(address, address2, city , zipCode,
                       phone, createdDate, createdBy, lastUpdate, lastUpdateby);
                
                addressId = DatabaseConnect.lastAddressId();
                /* public void createCustomer(String customerName, int addressId, int active,
            String createDate, String lastUpdate, String createdBy,
            String lastUpdateBy) */
                
                DatabaseConnect.createCustomer(customerName, addressId, active,
                        createdDate, lastUpdate, createdBy, lastUpdateby);
               
                // Save the appointment 
                
                // Close the window 
                stage = (Stage) saveButton.getScene().getWindow();
                stage.hide();
                CustomerSceenController.getActiveCustomerScreen().reinitialize();
                        
            }
            catch (SQLException e){
                // possibly show a popup with a try again or cancel option
                
            System.out.println("error: "+ e);

            }
        
        
    }
    
    

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        citySelection.getItems().addAll(0);
    }

}
