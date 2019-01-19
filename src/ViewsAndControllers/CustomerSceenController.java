/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewsAndControllers;

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

 @FXML
 private Button apptButton;
   
        
//Stage setting variable for Button actions to select new stages to display
 Stage stage = new Stage();
  
 
 
     @FXML
 private void apptButtonAction(ActionEvent event) throws IOException {
  stage=(Stage)apptButton.getScene().getWindow();
  Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/HomeScreen.fxml"));
  Scene scene = new Scene(root);
  stage.setTitle("Tim Aguirre Customer Scheduler App");
  stage.setScene(scene);
  stage.showAndWait();
 }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
