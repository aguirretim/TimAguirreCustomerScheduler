package Model;

import java.sql.*;
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
         * ************************************
         * Connects to MySql Database. *********************************
         */
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://52.206.157.109:3306/U04k77?zeroDateTimeBehavior=convertToNull", "U04k77", "53688267207");
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
    }

    // public User getUserByUsernamePassword - if not null success
    public User getUserByUsernamePassword(
            String userName, String passWord
    ) throws Exception {

        // if username or password is empty throw an exception
        if (userName == null || userName.trim().length() < 1) {
            System.out.println("Username cannot be empty");
            // either return here or throw an exception
        }
        if (passWord == null || passWord.trim().length() < 1) {
            System.out.println("PassWord cannot be empty");
            // either return here or throw an exception
        }

        String query
                = "select * from user "
                + "where userName = '" + userName + "' "
                + "and password = '" + passWord + "'";

        System.out.println(query);
        rs = st.executeQuery(query);

        System.out.println("Attempting to login");
        try {
            if (rs.next()) {
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

                    int userid = rs.getInt("userId");
                    String uN = rs.getString("userName");
                    Boolean active = rs.getBoolean("active");
                    String createBy = rs.getString("createBy");

                    String lastUpdatedBy = rs.getString("lastUpdatedBy");
                    User loggedUser = new User(userid,
                            uN,
                            active,
                            createBy,
                            createDate,
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
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
        return null;
    }

    // Returns Customer name By ID 
    // So that you can change ID to a name for appointment (Work in progress)
    public String getCustomerId(int customerId) {
        try {
            String query = "SELECT customerId,customerName FROM U04k77.customer "
                    + "where customerId = " + customerId;

            rs = st.executeQuery(query);
            System.out.println("Record from Database");
            while (rs.next()) {
                String customerName = rs.getString("customerName");
                //String password = rs.getString("password");
                System.out.println("The Customer ID: " + customerId
                        + " Returns Customer Name " + customerName);
                return customerName;
            }
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
        return "Error occured No Customer name found";
    }

    // getting all the appoitments in the databse
    // public List<Appointment> getAllAppointments()
    // appontments = dbConn.getAllAppointments()
    // myObservableList.clear()
    // myObservableList.addAll(appointments)
    public List<Appointment> getAllAppointmentsByUserId(
            int LoggedInUserId) throws SQLException {

        // create a new Arraylist to return the results of the query
        List<Appointment> results = new ArrayList<>();

        String query = "SELECT * FROM U04k77.appointment where userId = "
                + LoggedInUserId;
        rs = st.executeQuery(query);
        System.out.println("Record from Database");
        while (rs.next()) {

            int appointmentId = rs.getInt("appointmentId");
            int customerId = rs.getInt("customerId");
            int userId = rs.getInt("userId");
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

            /*String query2 = "Use U04k77; "
                    + "SELECT customerName "
                    + "FROM customer "
                    + "Where customer.customerId = "+ customerId; 
            rs = st.executeQuery(query2);
            
            String cusName = rs.getString("customerName");*/
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
            results.add(new Appointment(appointmentId, customerId, userId, title,
                    description, location, contact, type,
                    url, start, end, createdBy, lastUpdate, lastUpdateBy));
        }

        return results;
    }

    public List<Customer> getAllCustomers() throws SQLException {

        // create a new Arraylist to return the results of the query
        List<Customer> results = new ArrayList<>();

        String query = "SELECT * FROM U04k77.customer;";
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

            results.add(new Customer(customerId, customerName, addressId, active,
                    createdBy, lastUpdate, lastUpdateBy));
        }

        return results;
    }

    public void createAppontment(int customerId, int userId, String title,
            String description, String location, String contact, String type,
            String url, String start, String end, String lastUpdate,
            String createDate, String createdBy, String lastUpdateBy) throws SQLException {

        String query = "INSERT INTO `U04k77`.`appointment` (`customerId`, `userId`,"
                + " `title`, `description`,"
                + "`location`, `contact`, `type`, `url`, `start`, `end`, `lastUpdate`, "
                + "`createDate`, `createdBy`, `lastUpdateBy`) "
                + "VALUES (" + "'" + customerId + "',"
                + "'" + userId + "',"
                + "'" + title + "',"
                + "'" + description + "',"
                + "'" + location + "',"
                + "'" + contact + "',"
                + "'" + type + "',"
                + "'" + url + "',"
                + "'" + start + "',"
                + "'" + end + "',"
                + "'" + lastUpdate + "',"
                + "'" + createDate + "',"
                + "'" + createdBy + "',"
                + "'" + lastUpdateBy + "');";

        int tableRowsAffected = st.executeUpdate(query);

        System.out.println(tableRowsAffected + " rows was added. "
                + "Creating an appointment with" + query);
    }

    public void createCustomer(String customerName, int addressId, int active,
            String createDate, String lastUpdate, String createdBy,
            String lastUpdateBy) throws SQLException {
        String query = "INSERT INTO `U04k77`.`customer` ("
                + "`customerName`, "
                + "`addressId`,"
                + " `active`, "
                + "`createDate`, "
                + "`createdBy`, "
                + "`lastUpdate`, "
                + "`lastUpdateBy`)"
                + " VALUES ("
                + "'"
                + customerName + "', " + "'"
                + addressId + "', '"
                + active + "', '"
                + createDate + "'," + " '"
                + createdBy + "', '"
                + lastUpdate + "', '"
                + lastUpdateBy + "');";

        int tableRowsAffected = st.executeUpdate(query);

        System.out.println(tableRowsAffected + " rows was added. "
                + "Creating an Customer with" + query);
    }

    public void createAddress(String address, String address2, int cityId,
            String postalCode, String phone, String createDate,
            String createdBy, String lastUpdate, String lastUpdateBy) throws SQLException {
        String query = "INSERT INTO `U04k77`.`address` ("
                + "`address`, "
                + "`address2`, "
                + "`cityId`, "
                + "`postalCode`, "
                + "`phone`, "
                + "`createDate`, "
                + "`createdBy`, "
                + "`lastUpdate`, "
                + "`lastUpdateBy`) "
                + "VALUES ("
                + "'" + address + "', "
                + "'" + address2 + "', "
                + "'" + cityId + "', "
                + "'" + postalCode + "', "
                + "'" + phone + "', "
                + "'" + createDate + "', "
                + "'" + createdBy + "', "
                + "'" + lastUpdate + "', "
                + "'" + lastUpdateBy + "')";

        int tableRowsAffected = st.executeUpdate(query);

        System.out.println(tableRowsAffected + " rows was added. "
                + "Creating an Customer with" + query);
    }

    public Integer lastAddressId() {
        try {
            String query = "SELECT last_insert_id() FROM U04k77.address";
            rs = st.executeQuery(query);

            while (rs.next()) {
                int last_insert_id = Integer.parseInt(rs.getString("last_insert_id()"));

                System.out.println("last_insert_id: " + last_insert_id);
                return last_insert_id;
            }
        } catch (NumberFormatException | SQLException ex) {
            System.out.println("erro: " + ex);
        }
        return null;
    }

    public void delAppointment(int apptId) throws SQLException {
        String query = "DELETE FROM `U04k77`.`appointment` "
                + "WHERE (`appointmentId` = '" + apptId + "');";
        try {
            int tableRowsAffected = st.executeUpdate(query);

            System.out.println(tableRowsAffected + " rows were deleted. "
                    + "Deleting an appointment with" + query);
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
    }
    
    public void delCustomer(int customerId) throws SQLException {
        String query = "DELETE FROM `U04k77`.`customer` "
                + "WHERE (`customerId` = '" + customerId + "');";
        try {
            int tableRowsAffected = st.executeUpdate(query);
            System.out.println(tableRowsAffected + " rows were deleted. "
                    + "Deleting a Customer with" + query);
            
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
    }

    public void editAppointment(int appointmentId, String title,
            String description, String type, String url, String start, String end,
            String lastUpdate,String lastUpdateBy )throws SQLException {
   
            String query = "UPDATE `U04k77`.`appointment` SET "                  
                    + "`title` = '"+title+"', "
                    + "`description` = '"+description+"', "
                    + "`type` = '"+type+"', "
                    + "`url` = '"+url+"', "
                    + "`start` = '"+start+"', "
                    + "`end` = '"+end+"', "
                    + "`lastUpdate` = '"+lastUpdate+"', "                   
                    + "`lastUpdateBy` = '"+lastUpdateBy+"' "
                    + "WHERE (`appointmentId` = '"+appointmentId+"');";
            
        int tableRowsAffected = st.executeUpdate(query);

        System.out.println(tableRowsAffected + " rows were edited. "
                + "Editing an Appointment with" + query);
    }
    
}
