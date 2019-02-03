package Model;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Tim
 */
public class DBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;
//    private String userName;
//    private String password;
//
//    CustomerList customerData = new CustomerList();

    public DBConnect() {

        /**
         * ***********************************
         * Connects to MySql Database. **********************************
         */
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

    // public User getUserByUsernamePassword - if not null success
    public User getUserByUsernamePassword(
            String userName, String passWord
    ) throws Exception {

        // if username or password is empty throw an exception
        if(userName ==null || userName.trim().length()<1){
               System.out.println("Username cannot be empty");}
        if(passWord ==null || passWord.trim().length()<1)
        {    System.out.println("PassWord cannot be empty");}
        
        String query = 
                "select * from user "
                + "where userName = '" + userName + "' "
                + "and password = '" + passWord + "'";

        System.out.println(query);
        rs = st.executeQuery(query);

        System.out.println("Attempting to login");
try {
        if (rs.next())
        {
            // user found for username, check if passwords match
            String actualPass = rs.getString("password");
            if (passWord.equals(actualPass)) {
                // passwords match build a User Object and return it
                /*
                    User(int userId, String userName, boolean active, String 
            createdBy, Date createdDate, Timestamp lastUpdate, 
            String lastUpdateBY)
                 */
                String createDate = rs.getString("createDate");
                        if (createDate == null) {
                          createDate = "0000-00-00";
            }
                String lastUpdate = rs.getString("lastUpdate");
                        if (lastUpdate == null) {
                          lastUpdate = "0000-00-00";
            }
                        
                        
                        int userid=rs.getInt("userId");
                        String uN=rs.getString("userName");
                        Boolean active=rs.getBoolean("active");
                        String createBy=rs.getString("createBy");
                       
                        String lastUpdatedBy =rs.getString("lastUpdatedBy");
               User loggedUser = new User(userid,
                        uN,
                        active,
                        createBy,
                       createDate 
                        ,
                        lastUpdate,
                        lastUpdatedBy);
                        
                         return loggedUser;
                
            } else {
                // passwords do not match
            System.out.println("pw not found");
            }
        } else {
            // username not found
            System.out.println("un not found");
        }

        
    }catch (Exception ex) {
            System.out.println("erro: " + ex);
        }return null;}

    // what does this do?
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

    // getting all the appoitments in the databse
    // public List<Appointment> getAllAppointments()
    // appontments = dbConn.getAllAppointments()
    // myObservableList.clear()
    // myObservableList.addAll(appointments)
    public List<Appointment> getAllAppointments() throws SQLException {

        // create a new Arraylist to return the results of the query
        List<Appointment> results = new ArrayList<>();

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
            if (start == null) {
                start = "0000-00-00";
            }
            String end = rs.getString("end");
            if (end == null) {
                end = "0000-00-00";
            }
            String createDate = rs.getString("createDate");
            if (createDate == null) {
                createDate = "0000-00-00";
            }
            String createdBy = rs.getString("createdBy");
            String lastUpdate = rs.getString("lastUpdate");
            if (lastUpdate == null) {
                lastUpdate = "0000-00-00";
            }
            String lastUpdateBy = rs.getString("lastUpdateBy");
            String type = rs.getString("type");

            System.out.println("appointmentId: " + appointmentId + " "
                    + "Customer ID: " + customerId + " "
                    + "title: " + title + " "
                    + "description: " + description + " "
                    + "location: " + location + " "
                    + "contact: " + contact + " "
                    + "url: " + url + " "
                    + "start: " + start + " "
                    + "end: " + end + " "
                    + "createDate: " + createDate + " "
                    + "createdBy: " + createdBy + " "
                    + "lastUpdate: " + lastUpdate + " "
                    + "lastUpdateBy: " + lastUpdateBy + " "
                    + "type: " + type);

            /*
                Appointment(int appointmentId, int customerId, int userId, 
                    String title, String description, String location, String contact, 
                    String type, String url, String start, String end, String createdBy, 
                    String lastUpdate, String lastUpdateBY)
             */
            results.add(new Appointment(appointmentId, 0, customerId, title,
                    description, location, contact, type,
                    url, start, end, createdBy, lastUpdate, lastUpdateBy));

        }

        return results;
    }

    public void getCustomerInfo() {
        try {

            String query = "select * from customer";
            rs = st.executeQuery(query);
            System.out.println("Record from Database");
            while (rs.next()) {

                int customerId = rs.getInt("customerId");
                String customerName = rs.getString("customerName");
                int addressId = rs.getInt("addressId");
                int active = rs.getInt("active");
                String createDate = rs.getString("createDate");
                if (createDate == null) {
                    createDate = "0000-00-00";
                }
                String createdBy = rs.getString("createdBy");
                String lastUpdate = rs.getString("lastUpdate");
                if (lastUpdate == null) {
                    lastUpdate = "0000-00-00";
                }
                String lastUpdateBy = rs.getString("lastUpdateBy");

                System.out.println("Customer ID: " + customerId + " "
                        + "customerName " + customerName + " "
                        + "addessId: " + addressId + " "
                        + "active: " + active + " "
                        + "createDate: " + createDate + " "
                        + "createdBy: " + createdBy + " "
                        + "lastUpdate: " + lastUpdate + " "
                        + "lastUpdateBy: " + lastUpdateBy + " "
                );

//                customerData.addCustomer(new Customer(customerId, customerName, addressId, active,
//                        createdBy, lastUpdate, lastUpdateBy));
            }
        } catch (Exception ex) {
            System.out.println("error: " + ex);
        }
    }
}
