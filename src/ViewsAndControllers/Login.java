/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewsAndControllers;

import Model.Appointment;
import Model.CustomerList;
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
import static Model.DBConnect.dateTimeConverter;
import Model.User;
import java.sql.SQLException;
import javafx.scene.control.Alert;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Locale;

/**
 *
 * @author Tim
 */
public class Login implements Initializable {

    private static User loggedInUser;

    /**
     * ***********************************
     * Variables for Buttons and Field. **********************************
     */
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
    Locale mexicoLocale = new Locale("es", "MX");
    //Changes default language english to testing language 
    //Locale.setDefault(mexicoLocale);
    Locale currentLocale = Locale.getDefault();

    //String sql = "select userName,password from user where user = '" + userT+"'password = '"+passT+"'" ;
    DBConnect connect = new DBConnect();

    // Static methods - logged in user
    public static boolean isLoggedIn() {
        return Login.loggedInUser != null;
    }

    public static int getLoggedInUserId() {
        if (Login.isLoggedIn()) {
            return Login.loggedInUser.getUserId();
        } else {
            throw new RuntimeException(
                    "Login.getLoggedInUserId() - error: user not logged in");
        }
    }

    public static User getLoggedInUser() {
        if (Login.isLoggedIn()) {
            return Login.loggedInUser;
        } else {
            throw new RuntimeException(
                    "Login.getLoggedInUserId() - error: user not logged in");
        }
    }

    /**
     * ***********************************
     * Changing screens and scenes with buttons.
     * **********************************
     */
    @FXML
    private void loginButtonAction(ActionEvent event) throws IOException, Exception {

        String userT = usernameText.getText().trim();
        String passT = passwordText.getText().trim();

        // test to see if the login credentials are correct
        User user = null;
        User userInData = connect.getUserByUsernamePassword(userT, passT);

        if ((user = userInData) != null) {
            System.out.println("log in completed");

            // Login passed
            Login.loggedInUser = user;

            // Create a login session, and set the user to that session
            stage = (Stage) login.getScene().getWindow();
            System.out.println("(Succesful login) Welcome Consultant");
            Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/HomeScreen.fxml"));

            Scene scene = new Scene(root);
            stage.setTitle("Tim Aguirre Customer Scheduler App");

            stage.setScene(scene);
            
        } else {
            if (currentLocale == mexicoLocale) {
                System.out.println("(Invalid login attempted) Sorry, The username or password you entered is incorrect.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Intento de inicio de sesión no válido");
                alert.setHeaderText("Lo siento");
                alert.setContentText("El nombre de usuario o la contraseña son incorrectos.");
                alert.showAndWait();
            } else {
                System.out.println("(Invalid login attempted) Sorry, The username or password you entered is incorrect.");
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Invalid login attempted");
                alert.setHeaderText("Sorry");
                alert.setContentText("The username or password you entered is incorrect.");
                alert.showAndWait();
            }
        }

    }

    

    public void initialize(URL url, ResourceBundle rb) {
        //Test print of current time for understanding how timestamps work
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        System.out.println("Current time using current time: " + timestamp);

        Locale mexicoLocale = new Locale("es", "MX");

        //Changes default language english to testing language 
        //Locale.setDefault(mexicoLocale);
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
        if (currentLocale == mexicoLocale) {
            System.out.println("Changing to localized language.........hola como estas");
        }
    }

}
