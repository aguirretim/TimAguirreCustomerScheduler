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
public class Country {
    int countryId;
    private String country;
    Date createdDate = new Date();
    private String createdBy;
    Timestamp lastUpdate;
    private String lastUpdateBY;
}
