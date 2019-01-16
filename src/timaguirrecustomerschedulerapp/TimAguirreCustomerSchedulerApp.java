package timaguirrecustomerschedulerapp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Model.DBConnect;




public class TimAguirreCustomerSchedulerApp extends Application {

 @Override
 public void start(Stage stage) throws Exception {
  Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/Login.fxml"));
  stage.setTitle("Welcome Consultant Specialist");
  Scene scene = new Scene(root);

  stage.setScene(scene);
  stage.show();

//Connecting to the MySql Database and performing a query
  DBConnect connect = new DBConnect();
  connect.getData();
  

 }

 
 
 public static void main(String[] args) {
  launch(args);

 }

}