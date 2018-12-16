/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;


import java.sql.Timestamp;
import java.util.Date;
import javafx.scene.text.Text;

/**
 *
 * @author Tim
 */
public class Appointment {
    int appointmentId;
    int customerId;
    int userId;
    //varchar title;
    Text description;
    Text location;
    Text contact;
    Text type;
    //varchar url;
    //datetime start;
    //datetime end;
    Date createdDate = new Date();
    //varchar createdBy;
    Timestamp lastUpdate;
    //varchar lastUpdateBY;
}
