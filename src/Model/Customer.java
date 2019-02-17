/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.Date;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Tim
 */
public class Customer {

    
    
    private int customerId;
    private String customerName;
    private int addressId;
    private int active;
    private String createdDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdateBY;
    
    
    
     /*************************************
     * Constructor
     ************************************/
    
    
    
    @Override
    public String toString() {
        return "Customer{" + "customerId=" + customerId + ", customerName=" + customerName + ", addressId=" + addressId + ", active=" + active + ", createdDate=" + createdDate + ", createdBy=" + createdBy + ", lastUpdate=" + lastUpdate + ", lastUpdateBY=" + lastUpdateBY + '}';
    }

    public Customer(int customerId, String customerName, int addressId, int active, String createdBy, String lastUpdate, String lastUpdateBY) {
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.active = active;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBY = lastUpdateBY;
    }
    

     /*************************************
     * Getters
     ************************************/
    
    
    
    public int getCustomerId() {
        return customerId;
    }

    public String getCustomerName() {
        return customerName;
    }

    public int getAddressId() {
        return addressId;
    }

    public int getActive() {
        return active;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public String getLastUpdate() {
        return lastUpdate;
    }

    public String getLastUpdateBY() {
        return lastUpdateBY;
    }        

    
    
     /*************************************
     * Setters
     ************************************/
    
    
    
    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public void setAddressId(int addressId) {
        this.addressId = addressId;
    }

    public void setActive(int active) {
        this.active = active;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public void setLastUpdate(String lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    public void setLastUpdateBY(String lastUpdateBY) {
        this.lastUpdateBY = lastUpdateBY;
    }
    
}
