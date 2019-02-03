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
import Model.User;
import static com.sun.deploy.uitoolkit.ToolkitStore.dispose;
import javafx.scene.control.Alert;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Locale;
import javafx.stage.Window;

/**
 *
 * @author Tim
 */
public class Login implements Initializable {

    private static User loggedInUser;
    

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
    
    //Locale/language setting Varriable for setting language of app
    Locale currentLocale;
    Locale mexicoLocale;
 
    //String sql = "select userName,password from user where user = '" + userT+"'password = '"+passT+"'" ;
    DBConnect connect = new DBConnect();
    
  /************************************
  * Changing screens and scenes with buttons.
  ************************************/

    
    
    
    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException {
        try {
            String userT = usernameText.getText().trim();
            String passT = passwordText.getText().trim();

      
            // test to see if the login credentials are correct
            User user = null;
            User userInData=connect.getUserByUsernamePassword(userT, passT);
            if ((user = userInData)!= null){
                System.out.println("log in completed");
                // Login passed
                Login.loggedInUser = user;
                
                // Create a login session, and set the user to that session
            stage=(Stage)login.getScene().getWindow();          
            System.out.println("(Succesful login) Welcome Consultant");
            Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/HomeScreen.fxml"));
            Scene scene = new Scene(root);
            stage.setTitle("Tim Aguirre Customer Scheduler App");
           
            stage.setScene(scene);
            stage.showAndWait();
            
            }                       
            else                 
            {if (currentLocale == mexicoLocale){
                System.out.println("(Invalid login attempted) Sorry, The username or password you entered is incorrect.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Intento de inicio de sesión no válido");
                alert.setHeaderText("Lo siento");
                alert.setContentText("El nombre de usuario o la contraseña son incorrectos.");
                alert.showAndWait();} 
            else{
                System.out.println("(Invalid login attempted) Sorry, The username or password you entered is incorrect.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid login attempted");
                alert.setHeaderText("Sorry");
                alert.setContentText("The username or password you entered is incorrect.");
                alert.showAndWait();}}   
        } catch (Exception ex) {            
        }
    }


    @FXML
    private void handleButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        label.setText("Hello World!");
        connect.getCustomerInfo();
        
    }

    
    
    public void initialize(URL url, ResourceBundle rb) {
        //Test print of current time for understanding how timestamps work
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Current time using current time: "+timestamp); 
        
        Locale mexicoLocale = new Locale("es","MX");
        
        //Changes default language english to testing language 
        Locale.setDefault(mexicoLocale);
        Locale currentLocale = Locale.getDefault();
        /*
        System.out.println(currentLocale.getDisplayLanguage());
        System.out.println(currentLocale.getDisplayCountry());
        System.out.println(currentLocale.getLanguage());
        System.out.println(currentLocale.getCountry());
        System.out.println(currentLocale);        
        System.out.println(System.getProperty("user.country"));
        System.out.println(System.getProperty("user.language"));
        */
           if (currentLocale == mexicoLocale)
        {
        System.out.println("Changing to localized language.........hola como estas");
        }
    }

}
