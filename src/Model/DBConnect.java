package Model;

import static java.lang.Integer.parseInt;
import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import javax.swing.text.DateFormatter;

/**
 *
 * @author Tim
 */
public class DBConnect {

    private Connection con;
    private Statement st;
    private ResultSet rs;

    private Connection con2;
    private Statement st2;
    private ResultSet rs2;

    private Connection con3;
    private Statement st3;
    private ResultSet rs3;

//    private String userName;
//    private String password;
//
//    CustomerList customerData = new CustomerList();
    public DBConnect() {

        /**
         * ************************************
         * Connects to MySql Database. ******************************
         */
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://52.206.157.109:3306/U04k77?zeroDateTimeBehavior=convertToNull", "U04k77", "53688267207");
            st = con.createStatement();
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con2 = DriverManager.getConnection("jdbc:mysql://52.206.157.109:3306/U04k77?zeroDateTimeBehavior=convertToNull", "U04k77", "53688267207");
            st2 = con2.createStatement();
        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con3 = DriverManager.getConnection("jdbc:mysql://52.206.157.109:3306/U04k77?zeroDateTimeBehavior=convertToNull", "U04k77", "53688267207");
            st3 = con3.createStatement();
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
            /*
            Timestamp ts = rs.getTimestamp("start");
            LocalDateTime d = ts.toLocalDateTime();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                    "yyyy-MM-dd: mm:ss"
            );
            String formattedDate = d.format(formatter);
            d.getYear();*/

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

            String cusName = customerName(customerId);

            if (customerName(customerId) == null) {
                cusName = "Deleted Customer";
            } else {
                cusName = cusName;
            }
            int locationId = parseInt(location);
            String Address = fullAddressFromId(locationId);

            /*String query2 = "Use U04k77; "
                    + "SELECT customerName "
                    + "FROM customer "
                    + "Where customer.customerId = "+ customerId; 
            rs = st.executeQuery(query2);
            
            String cusName = rs.getString("customerName");*/
            System.out.println("appointmentId: " + appointmentId + " "
                    + "Customer ID: " + customerId + " "
                    + "Customer Name: " + cusName + " "
                    + "Address: " + Address + " "
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
            results.add(new Appointment(appointmentId, customerId, cusName, Address,
                    userId, title, description, location, contact, type,
                    url, start, end, createdBy, lastUpdate, lastUpdateBy));
        }

        return results;
    }

    public List<Customer> getAllCustomers() throws SQLException {

        // create a new Arraylist to return the results of the query
        List<Customer> results = new ArrayList<>();

        String query = "SELECT customer.*, address.address,address.address2,address.postalCode,address.phone, city.city\n"
                + "FROM customer\n"
                + "Left JOIN address  ON address.addressId=customer.addressId\n"
                + "Left JOIN city  ON city.cityId=address.cityId\n"
                + "GROUP BY customer.customerId";
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
            String addr = rs.getString("address")
                    + " " + rs.getString("address2")
                    + " " + rs.getString("city")
                    + " " + rs.getString("postalCode");
            String address1 = rs.getString("address");
            String address2 = rs.getString("address2");
            String postalCode = rs.getString("postalCode");
            String city = rs.getString("city");
            String phone = rs.getString("phone");

            results.add(new Customer(customerId, customerName, addressId, addr, address1, address2, postalCode, city, phone, active,
                    createdBy, lastUpdate, lastUpdateBy));
        }

        return results;
    }

    public List<City> getAllCitys() throws SQLException {
        List<City> results = new ArrayList<>();

        String query = "SELECT cityId,city FROM U04k77.city;";
        rs2 = st2.executeQuery(query);
        while (rs2.next()) {

            int cityId = rs2.getInt("cityId");
            String city = rs2.getString("city");
            results.add(new City(cityId, city));

        }
        return results;
    }

    public String getCityByCityId(int cityId) throws SQLException {

        List<City> citylist2 = new ArrayList<>();
        citylist2 = this.getAllCitys();
        for (City var : citylist2) {
            if (var.getCityId() == cityId) {
                // codes
                System.out.println(cityId);
                System.out.println(var.toString());

                return var.getCity();
            }
        }
        return null;
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

    public void editCustomer(int customerId, String customerName) {
        String query = "UPDATE `U04k77`.`customer` SET `customerName` = "
                + "'" + customerName + "', `lastUpdate` = '"
                + new Timestamp(System.currentTimeMillis()) + "' "
                + "WHERE (`customerId` = '" + customerId + "');";
        try {
            int tableRowsAffected = st.executeUpdate(query);
            System.out.println(tableRowsAffected + " rows were edited. "
                    + "Edting a Customer with" + query);

        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
    }

    public void editAddress(int addressId, String address, String address2, int cityId,
            String postalCode, String phone, String lastUpdate, String lastUpdateBy) {
        //Code for Method
        String query = "UPDATE `U04k77`.`address` SET "
                + "`address` = '" + address + "', "
                + "`address2` = '" + address2 + "', "
                + "`cityId` = '" + cityId + "', "
                + "`postalCode` = '" + postalCode + "', "
                + "`phone` = '" + phone + "', "
                + "`lastUpdate` = '" + lastUpdate + "', "
                + "`lastUpdateBy` = '" + lastUpdateBy + "' "
                + "WHERE (`addressId` = '" + addressId + "');";
        try {
            int tableRowsAffected = st.executeUpdate(query);
            System.out.println(tableRowsAffected + " rows were edited. "
                    + "editing a address with" + query);

        } catch (Exception ex) {
            System.out.println("erro: " + ex);
        }
    }

    public String customerName(int customerId) {
        try {
            String query = "SELECT customerName\n"
                    + "FROM U04k77.customer\n"
                    + "Where customerId=" + customerId + ";";

            rs2 = st2.executeQuery(query);

            while (rs2.next()) {
                String customerName = rs2.getString("customerName");

                System.out.println("customerName: " + customerName);
                return customerName;
            }

        } catch (NumberFormatException | SQLException ex) {
            System.out.println("erro: " + ex);
        }
        return null;
    }

    public String fullAddressFromId(int addressId) {
        try {
            String query = "SELECT address,address2,cityId,postalCode\n "
                    + "FROM U04k77.address\n"
                    + "Where addressId =" + addressId + ";";

            rs3 = st3.executeQuery(query);

            while (rs3.next()) {
                String Address = rs3.getString("address") + " " + rs3.getString("address2") + " " + getCityByCityId(rs3.getInt("cityId")) + " " + rs3.getString("postalCode");
                System.out.println("Address: " + Address);
                return Address;
            }

        } catch (NumberFormatException | SQLException ex) {
            System.out.println("erro: " + ex);
        }
        return null;
    }

    public String address1FromId(int addressId) {
        try {
            String query = "SELECT address,\n "
                    + "FROM U04k77.address\n"
                    + "Where addressId =" + addressId + ";";

            rs3 = st3.executeQuery(query);

            while (rs3.next()) {
                String Address = rs3.getString("address") + " " + rs3.getString("address2") + " " + getCityByCityId(rs3.getInt("cityId")) + " " + rs3.getString("postalCode");
                System.out.println("Address: " + Address);
                return Address;
            }

        } catch (NumberFormatException | SQLException ex) {
            System.out.println("erro: " + ex);
        }
        return null;
    }

    public String address2FromId(int addressId) {
        try {
            String query = "SELECT address2,\n "
                    + "FROM U04k77.address\n"
                    + "Where addressId =" + addressId + ";";

            rs3 = st3.executeQuery(query);

            while (rs3.next()) {
                String Address = rs3.getString("address") + " " + rs3.getString("address2") + " " + getCityByCityId(rs3.getInt("cityId")) + " " + rs3.getString("postalCode");
                System.out.println("Address: " + Address);
                return Address;
            }

        } catch (NumberFormatException | SQLException ex) {
            System.out.println("erro: " + ex);
        }
        return null;
    }

    public String phoneFromAddressId(int addressId) {
        try {
            String query = "SELECT phone\n "
                    + "FROM U04k77.address\n"
                    + "Where addressId =" + addressId + ";";

            rs2 = st2.executeQuery(query);

            while (rs2.next()) {
                String phone = rs2.getString("phone");

                System.out.println("phone: " + phone);
                return phone;

            }

        } catch (NumberFormatException | SQLException ex) {
            System.out.println("erro: " + ex);
        }

        return null;

    }

    public void editAppointment(int appointmentId, String title,
            String description, String type, String url, String start, String end,
            String lastUpdate, String lastUpdateBy) throws SQLException {

        String query = "UPDATE `U04k77`.`appointment` SET "
                + "`title` = '" + title + "', "
                + "`description` = '" + description + "', "
                + "`type` = '" + type + "', "
                + "`url` = '" + url + "', "
                + "`start` = '" + start + "', "
                + "`end` = '" + end + "', "
                + "`lastUpdate` = '" + lastUpdate + "', "
                + "`lastUpdateBy` = '" + lastUpdateBy + "' "
                + "WHERE (`appointmentId` = '" + appointmentId + "');";

        int tableRowsAffected = st.executeUpdate(query);

        System.out.println(tableRowsAffected + " rows were edited. "
                + "Editing an Appointment with" + query);
    }
    /*
    public List<Appointment> getAllAppointmentViewsByUserId(
            int LoggedInUserId) throws SQLException {

        // create a new Arraylist to return the results of the query
        List<Appointment> results = new ArrayList<>();

        String query = "SELECT * FROM U04k77.appointment "
                + "where userId = "
                + LoggedInUserId;
        rs = st.executeQuery(query);
        System.out.println("Record from Database");
        while (rs.next()) {}
    
}*/

}
