/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Tim
 */
public class Customer {
    int customerId;
    //varchar customerName;
    int addressId;
    //tinyint active;
    Date createdDate = new Date();
    //varchar createdBy;
    Timestamp lastUpdate;
    //varchar lastUpdateBY;
    
}