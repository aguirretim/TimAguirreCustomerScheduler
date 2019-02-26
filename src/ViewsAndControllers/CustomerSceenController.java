/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewsAndControllers;

import Model.Customer;
import Model.CustomerList;
import Model.DBConnect;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
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
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Tim
 */
public class CustomerSceenController implements Initializable {

    /**
     * ***********************************
     * Variables for Buttons and Field.
     ***********************************
     */
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

    //Stage setting variable for Button actions to select new stages to display
    Stage stage = new Stage();

    @FXML
    private TableView customerTable;

    @FXML
    private TableColumn nameCol;

    @FXML
    private TableColumn address;

    @FXML
    private TableColumn phone;

    DBConnect DatabaseConnect = new DBConnect();

    Customer selCustomer;

    private static CustomerSceenController ActiveCustomerScreen = null;

    public static CustomerSceenController getActiveCustomerScreen() {
        return ActiveCustomerScreen;
    }

    public static void setActiveCustomerScreen(CustomerSceenController ActiveCustomerScreen) {
        CustomerSceenController.ActiveCustomerScreen = ActiveCustomerScreen;
    }

    /**
     * ***********************************
     * Changing screens and scenes with buttons.
     ***********************************
     */
    @FXML
    private void apptButtonAction(ActionEvent event) throws IOException {
        stage = (Stage) apptButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/HomeScreen.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Tim Aguirre Customer Scheduler App");
        stage.setScene(scene);
    }

    @FXML
    private void addCustomerButtonAction(ActionEvent event) throws IOException {
        Stage modalStage = new Stage();
        modalStage.initOwner(stage);
        modalStage.initModality(Modality.APPLICATION_MODAL);
        Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/AddCustomer.fxml"));
        Scene scene = new Scene(root);
        modalStage.setTitle("Create A New Customer");
        modalStage.setScene(scene);
        modalStage.showAndWait();
    }

    @FXML
    private void editCustomerButtonAction(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(); //Loads an object hierarchy from an XML document.     
            loader.setLocation(getClass().getResource("/ViewsAndControllers/EditCustomer.fxml")); //  reference FXML files like this in my controllers                 
            loader.load();

            EditCustomerController ecc = loader.getController();

            Parent editCustomerWindow = loader.getRoot();

        
        selCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        
        
        //ecc.transferData(0, customerName, address, address2, citySelectionText, zipCode, phone);
        
        stage = (Stage) editCustomerButton.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/EditCustomer.fxml"));
        Scene scene = new Scene(root);
        stage.setTitle("Edit and Modify Customer");
        stage.setScene(scene);
        
        
        
        
        
        
        
        
    }

    @FXML
    private void delButtonButtonAction(ActionEvent event) throws IOException, SQLException {
        selCustomer = (Customer) customerTable.getSelectionModel().getSelectedItem();
        int custId = selCustomer.getCustomerId();
        DatabaseConnect.delCustomer(custId);
        reinitialize();
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        reinitialize();
    }

    public void reinitialize() {
        this.customerData.clearCustomers();
        try {
            // TODO
            this.customerData.addCustomers(DatabaseConnect.getAllCustomers());
        } catch (Exception e) {
            // print some msg or popup some error alert box
            throw new UnsupportedOperationException(
                    "Homescreen initialize - caught Exception", e);
        }
        customerTable.setItems(customerData.getCustomer());

        nameCol.setCellValueFactory(new PropertyValueFactory<>("customerName"));
        address.setCellValueFactory(new PropertyValueFactory<>("address"));
        phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
    }

}
