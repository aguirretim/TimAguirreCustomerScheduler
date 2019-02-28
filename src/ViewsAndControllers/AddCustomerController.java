/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewsAndControllers;

import Model.City;
import Model.DBConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
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

    /**
     * ***********************************
     * Variables for Buttons and Field. *********************************
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

    //Stage setting variable for Button actions to select new stages to display
    Stage stage = new Stage();

    DBConnect DatabaseConnect = new DBConnect();

    Timestamp timestamp = new Timestamp(System.currentTimeMillis());

    Locale mexicoLocale = new Locale("es", "MX");

    //Changes default language english to testing language 
    //Locale.setDefault(mexicoLocale);
    Locale currentLocale = Locale.getDefault();

    /**
     * ***********************************
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

    @FXML
    private void saveButtonAction(ActionEvent event) throws IOException {
        if (validateAll()) {
            try {
            int userId = Login.getLoggedInUserId();
            String customerName = customerNameText.getText();
            int addressId;
            String address = addressText.getText();
            String address2 = address2Text.getText();
            int active = 1;
            int city;

            city = getCityIdByCity(citySelection.getValue().toString());
            String zipCode = zipCodeText.getText();
            String phone = phoneText.getText();

            String lastUpdate = timestamp.toString();
            String createdDate = timestamp.toString();
            String createdBy = Login.getLoggedInUser().getUserName();
            String lastUpdateby = Login.getLoggedInUser().getUserName();

            /*public void createAddress(String address, String address2, int cityId,
            String postalCode, String phone, String createDate,
            String createdBy, String lastUpdate, String lastUpdateBy)*/
            DatabaseConnect.createAddress(address, address2, city, zipCode,
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
            CustomerSceenController.getActiveCustomerScreen().reinitializer();

        } catch (SQLException e) {
            // possibly show a popup with a try again or cancel option

            System.out.println("error: " + e);

        }
        }
    }

    public Integer getCityIdByCity(String cityName) throws SQLException {

        List<City> citylist2 = new ArrayList<>();
        citylist2 = DatabaseConnect.getAllCitys();
        for (City var : citylist2) {
            if (var.getCity().contains(cityName)) {
                // codes
                return var.getCityId();
            }
        }
        return null;
    }

    public boolean validateCustomerName() {
        String customer = customerNameText.getText();
        if (customer.equals(null) || customer.isEmpty()) {
            if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor ingrese un nombre de cliente");
                alert.showAndWait();
                System.out.println("Por favor ingrese un nombre de cliente");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a Customer Name");
                alert.showAndWait();
                System.out.println("Please enter a Customer Name");
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean validateAddress() {
        String address = addressText.getText();
        if (address.equals(null) || address.isEmpty()) {
            if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor, introduzca una dirección en la línea de dirección 1");
                alert.showAndWait();
                System.out.println("Por favor, introduzca una dirección en la línea de dirección 1");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter an Address in Address Line 1");
                alert.showAndWait();
                System.out.println("Please enter an Address in Address Line 1");
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean validateCity() {
        String city = citySelection.getValue().toString();

        if (city.equals(null) || city.isEmpty()) {
            if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor seleccione una ciudad");
                alert.showAndWait();
                System.out.println("Por favor seleccione una ciudad");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please Select a City");
                alert.showAndWait();
                System.out.println("Please Select a City");
            }
            return false;
        } else {
            return true;
        }
    }

    public boolean validateZipCode() {
        String ZipCode = zipCodeText.getText();
        String regex = "^[0-9]{5}(?:-[0-9]{4})?$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ZipCode);

        if (matcher.matches()) {
            return true;

        } else {
            // some other code
            if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor ingrese un código postal válido");
                alert.showAndWait();
                System.out.println("Por favor ingrese un código postal válido");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a valid zip code");
                alert.showAndWait();
                System.out.println("Please enter a valid zip code");
            }
            return false;
        }
    }

    public boolean validatePhone() {
        String ZipCode = phoneText.getText();
        String regex = "\\d{10}|(?:\\d{3}-){2}\\d{4}|\\(\\d{3}\\)\\d{3}-?\\d{4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(ZipCode);

        if (matcher.matches()) {
            return true;

        } else {
            // some other code
            if (currentLocale == mexicoLocale) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Por favor ingrese un número de teléfono de 10 dígitos");
                alert.showAndWait();
                System.out.println("Por favor ingrese un número de teléfono de 10 dígitos");
            } else {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information Dialog");
                alert.setHeaderText("Error");
                alert.setContentText("Please enter a 10 digit Phone number");
                alert.showAndWait();
                System.out.println("Please enter a 10 digit Phone number");
            }
            return false;
        }
    }

    public boolean validateAll() {

        if (validateCustomerName()
                && validateAddress()
                && validateCity()
                && validateZipCode()
                && validatePhone()) {
            return true;

        } else {
            // some other code
            return false;
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        List<City> citylist = new ArrayList<>();
        
        
        try {

            citylist = DatabaseConnect.getAllCitys();
            citySelection.setValue(citylist.get(0).getCity());
        } catch (SQLException ex) {
            System.out.println("error: " + ex);
        }

        for (City var : citylist) {
            citySelection.getItems().addAll(var.getCity());
        }
        
    }

}
