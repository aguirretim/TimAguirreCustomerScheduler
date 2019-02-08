package Model;

import java.sql.*;
import java.text.SimpleDateFormat;

/**
 *
 * @author Tim
 */
public class DBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;
    private String userName;
    private String password;

    CustomerList customerData = new CustomerList();
    
    public DBConnect() {

        
        
        /*************************************
         * Connects to MySql Database.
         ************************************/
        
        
        
        try {                        
            Class.forName("com.mysql.jdbc.Driver");
con = DriverManager.getConnection("jdbc:mysql://52.206.157.109:3306/U04k77?zeroDateTimeBehavior=convertToNull", "U04k77", "53688267207");
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
    }

    //Does query and prints data.
    public void getData() {
        try {
            String query = "select * from user";
            rs = st.executeQuery(query);
            System.out.println("Records from Database");
            while (rs.next()) {
                String userName = rs.getString("userName");
                String password = rs.getString("password");
                System.out.println("Username: " + userName + " password: " + password);
            }
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
    }

    public boolean getLoginData(String UN, String PW) {
         boolean result = false;
        
        try {
            String userT = UN;
            String passT = PW;
           
            String query = "select userName,password from user where userName ='" + userT + "'and password ='" + passT + "'";
            System.out.println(query);
            rs = st.executeQuery(query);

            System.out.println("Attempting to login");
            int count = 0;
            while (rs.next()) {
                count = count + 1;

                if (count == 1) {
                    System.out.println("user found");
                    result = true;

                } else if (count > 1) {
                    System.out.println("user denied");
                } else {
                    System.out.println("user does not exsist");
                }
            }
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
        System.out.println(result);
    return result;
    }

    
        public void getCustomerId() {
        try {
            String query = "select customerId from customer";
            rs = st.executeQuery(query);
            System.out.println("Record from Database");
            while (rs.next()) {
                String customerId = rs.getString("customerId");
                //String password = rs.getString("password");
             System.out.println("Customer ID: " + customerId);
            }
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
    }
    
        public void getAppointmentInfo() {
        try {
            
            String query = "select * from appointment";
            rs = st.executeQuery(query);
            System.out.println("Record from Database");
            while (rs.next()) {
                
                int appointmentId = rs.getInt("appointmentId");
                int customerId = rs.getInt("customerId");
                String title = rs.getString("title");
                String description = rs.getString("description");
                String location = rs.getString("location");
                String contact = rs.getString("contact");
                String url = rs.getString("url");
                String start = rs.getString("Start");
                if (start==null)
                {start="0000-00-00";}
                String end = rs.getString("end");
                 if (end==null)
                {end="0000-00-00";}
                String createDate = rs.getString("createDate");
                 if (createDate==null)
                {createDate="0000-00-00";}
                String createdBy = rs.getString("createdBy");
                String lastUpdate = rs.getString("lastUpdate");
                 if (lastUpdate==null)
                {lastUpdate="0000-00-00";}
                String lastUpdateBy = rs.getString("lastUpdateBy");
                String type = rs.getString("type");
                
             System.out.println("appointmentId: "+ appointmentId+" "+
                                "Customer ID: " + customerId +" "+
                                "title: "+ title+" "+
                                "description: "+ description+" "+
                                "location: "+ location+" "+
                                "contact: " + contact+" "+
                                "url: "+ url+" "+
                                "start: "+ start+" "+
                                "end: "+end+" "+
                                "createDate: "+createDate+" "+
                                "createdBy: "+createdBy+" "+
                                "lastUpdate: "+lastUpdate+" "+
                                "lastUpdateBy: "+ lastUpdateBy+" "+
                                "type: "+type);
             
            customerData.addAppointment(new Appointment(appointmentId, 0, customerId,title, 
                     description, location, contact, type, 
                     url, start, end, createdBy, lastUpdate, lastUpdateBy));
            
            }
        } catch (Exception ex) {
            System.out.println("error: " + ex);
        }
    }
}
