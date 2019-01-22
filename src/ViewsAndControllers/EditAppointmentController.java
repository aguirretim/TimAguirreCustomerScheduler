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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class EditAppointmentController implements Initializable {
    
        
  
    
  /************************************
  * Variables for Buttons and Field.
  ************************************/
    
  
    
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
        
  
  
  /************************************
  * Changing screens and scenes with buttons.
  ************************************/
    
    
 
    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException {
    stage=(Stage)cancelButton.getScene().getWindow();
    Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/HomeScreen.fxml"));
    Scene scene = new Scene(root);
    stage.setTitle("Tim Aguirre Customer Scheduler App");
    stage.setScene(scene);

 }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
