package timaguirrecustomerschedulerapp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Model.DBConnect;
import java.sql.Timestamp;
import java.time.LocalDate;





public class TimAguirreCustomerSchedulerApp extends Application {

 @Override
 public void start(Stage stage) throws Exception {
  Parent root = FXMLLoader.load(getClass().getResource("/ViewsAndControllers/Login.fxml"));
  stage.setTitle("Welcome Consultant Specialist");
  Scene scene = new Scene(root);

  stage.setScene(scene);
  stage.show();
  stage.setResizable(false);
  
//Connecting to the MySql Database and performing a query
  DBConnect connect = new DBConnect();
 
 connect.getCustomerId(0);
 Timestamp timestamp = new Timestamp(System.currentTimeMillis());
 System.out.println(timestamp.toString());
 //System.out.println(LocalDate.MAX.toString());
 
 }
 public static void main(String[] args) {
  launch(args);

 }

}