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
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import Model.DBConnect;
import javafx.scene.control.Alert;
import java.sql.Timestamp;

/**
 *
 * @author Tim
 */
public class Login implements Initializable {

    
    

 /************************************
  * Variables for Buttons and Field.
  ************************************/ 
  
    @FXML
    private TextField usernameText;

    @FXML
    private TextField passwordText;

    @FXML
    private Label label;

    @FXML
    private Button login;

    //Stage setting variable for Button actions to select new stages to display
    Stage stage = new Stage();


    
 /************************************
  * Changing screens and scenes with buttons.
  ************************************/

    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException {
        try {
            String userT = usernameText.getText().trim();
            String passT = passwordText.getText().trim();

            //String sql = "select userName,password from user where user = '" + userT+"'password = '"+passT+"'" ;
            DBConnect connect = new DBConnect();

            if (connect.getLoginData(userT, passT)== true){
            login.getScene().getWindow().hide();
            Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/HomeScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Tim Aguirre Customer Scheduler App");
            stage.setScene(scene);
            stage.showAndWait();            
            }                       
            
            else                
            
                
            {
                
                Alert alert = new Alert(Alert.AlertType.ERROR);
                //alert.setTitle("Information Dialog");
                alert.setHeaderText("Sorry");
                alert.setContentText("The username or password you enetered is incorrect");
                alert.showAndWait();
                System.out.println("user is wrong");

            {                                               
            System.out.println("user is wrong");

            }
            
        }} catch (Exception ex) {            
        }
    }

    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
         Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Current time using current time: "+timestamp);       
    }

}
