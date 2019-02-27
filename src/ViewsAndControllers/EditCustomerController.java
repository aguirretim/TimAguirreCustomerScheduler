/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewsAndControllers;

import Model.DBConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.Timestamp;
import java.time.LocalDate;
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
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class EditCustomerController implements Initializable {

    /**
     * **********************************
     * Variables for Buttons and Field. **********************************
     */
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
    
    int cusId;

    //Stage setting variable for Button actions to select new stages to display
    Stage stage = new Stage();

    DBConnect DatabaseConnect = new DBConnect();

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    /**
     * **********************************
     * Changing screens and scenes with buttons.
     * **********************************
     */
    
    
    
    @FXML
    private void cancelButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) cancelButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/CustomerSceen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Tim Aguirre Customer Scheduler App");
        stage.setScene(scene);

    }

    public void transferData(
            int customerId,
            String customerName, String address, String address2,
            String citySelectionText, String zipCode, String phone) {

        customerNameText.setText(String.valueOf(customerName));
        addressText.setText(String.valueOf(address));
        address2Text.setText(String.valueOf(address2));
        zipCodeText.setText(String.valueOf(zipCode));
        phoneText.setText(String.valueOf(phone));
        citySelection.setValue(citySelectionText);
        
        cusId=customerId;

    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
