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
public class User {
    private int userId;
    private String userName;
    boolean active;
    private String createdBy;
    private String createdDate;
    private String lastUpdate;
    private String lastUpdateBY;
    
    public User() {}

    public User(int userId, String userName, boolean active, String 
            createdBy, String createdDate, String lastUpdate, 
            String lastUpdateBY) {
        this.userId = userId;
        this.userName = userName;
        this.active = active;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBY = lastUpdateBY;
    }
    
    

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
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
