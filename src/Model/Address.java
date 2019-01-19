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
public class Address {
    private int addressId;
    private String address;
    private String address2;
    int cityId;
    private String postalCode;
    private String phone;
    Date createdDate = new Date();
    private String createdBy;
    private Timestamp lastUpdate;
    private String lastUpdateBY;
}
