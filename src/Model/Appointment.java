/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;



import java.util.Date;
import javafx.scene.text.Text;

/**
 *
 * @author Tim
 */
public class Appointment {

   private Integer appointmentId;
   private int customerId;
   //private String customerName;
   private int userId;
   private String title;
   private String description;
   private String location;
   private String contact;
   private String type;
   private String url;
    //Start time suppose to be datetime in SQL
   private String start;
   //End time suppose to be datetime in SQL
   private String end;
   Date createdDate = new Date();
   private String createdBy;
   private String lastUpdate;
   private String lastUpdateBY;
   
   public Appointment(Integer appointmentId, int customerId, /*String customerName,*/ int userId, 
           String title, String description, String location, String contact, 
           String type, String url, String start, String end, String createdBy, 
           String lastUpdate, String lastUpdateBY) {
        this.appointmentId = appointmentId;
        this.customerId = customerId;
        //this.customerName = customerName;
        this.userId = userId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.contact = contact;
        this.type = type;
        this.url = url;
        this.start = start;
        this.end = end;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBY = lastUpdateBY;
    }
   
       public Integer getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(Integer appointmentId) {
        this.appointmentId = appointmentId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getStart() {
        return start;
    }

    public void setStart(String start) {
        this.start = start;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public String getLastUpdateBY() {
        return lastUpdateBY;
    }

    public void setLastUpdateBY(String lastUpdateBY) {
        this.lastUpdateBY = lastUpdateBY;
    }
   
}
