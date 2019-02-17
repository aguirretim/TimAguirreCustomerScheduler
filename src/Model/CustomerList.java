/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.List;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Tim
 */
public class CustomerList {
    // TODO do you really want to make these static??
  private static ObservableList<Customer> allCustomers = FXCollections.observableArrayList();
  private static ObservableList<Appointment> allAppointments = FXCollections.observableArrayList();  

  
      public void addCustomers(List<Customer> customerList) {
        allCustomers.addAll(customerList);
    }
      
     public void getCustomer(Customer CustomerData) {
        allCustomers.add(CustomerData);
    } 
     
     public ObservableList<Customer> getCustomer() {
        return allCustomers;
    }
     
    public void clearCustomers() {
        allCustomers.clear();
    }
     
   /*  public void addAppointment(Appointment AppointmentData) {
        allAppointments.add(AppointmentData);
    }*/
     
     public void addAppointments(List<Appointment> appointmentList) {
        allAppointments.addAll(appointmentList);
    }
      
     public void getAppointment(Appointment AppointmentData) {
        allAppointments.add(AppointmentData);
    } 
     
     public ObservableList<Appointment> getAppointment() {
        return allAppointments;
    }
     
    public void clearAppointments() {
        allAppointments.clear();
    }
}
