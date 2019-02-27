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
    private String address;
    private String address1;
    private String address2;
    private String postalCode;
    private String city;
    private String phone;
    private int active;
    private String createdDate;
    private String createdBy;
    private String lastUpdate;
    private String lastUpdateBY;

    /**
     * ***********************************
     * Constructor *********************************
     */
    public Customer(int customerId, String customerName, int addressId,
            String address, String address1, String address2,String postalCode, String city,
            String phone, int active, String createdBy, String lastUpdate,
            String lastUpdateBY) {
        
        this.customerId = customerId;
        this.customerName = customerName;
        this.addressId = addressId;
        this.address = address;
        this.address1 = address1;
        this.address2 = address2;
        this.postalCode=postalCode;
        this.city = city;
        this.phone = phone;
        this.active = active;
        this.createdBy = createdBy;
        this.lastUpdate = lastUpdate;
        this.lastUpdateBY = lastUpdateBY;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    /**
     * ***********************************
     * Getters **********************************
     */
    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

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

    /**
     * ***********************************
     * Setters **********************************
     */
    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

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
