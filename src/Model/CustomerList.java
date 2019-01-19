/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Tim
 */
public class CustomerList {
  private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
  private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();  

  
  
      public void addCustomer(Customer CustomerData) {
        allCustomers.add(CustomerData);
    }
      
     public void getCustomer(Customer CustomerData) {
        allCustomers.add(CustomerData);
    } 
     
     public ObservableList<Customer> getCustomer() {
        return allCustomers;
    }
     public void addAppointment(Appointment AppointmentData) {
        allAppointments.add(AppointmentData);
    }
      
     public void getAppointment(Appointment AppointmentData) {
        allAppointments.add(AppointmentData);
    } 
     
     public ObservableList<Appointment> getAppointment() {
        return allAppointments;
    }
}
