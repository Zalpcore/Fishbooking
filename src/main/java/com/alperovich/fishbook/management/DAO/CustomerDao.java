package com.alperovich.fishbook.management.DAO;

import com.alperovich.fishbook.management.models.Customer;
import javafx.collections.ObservableList;

public interface CustomerDao {

    ObservableList<Customer> getAllCustomers();

    ObservableList<Customer> getCustomersByName(String firstName, String lastName);

    void createCustomer(Customer customer);

    void updateCustomer(Customer customer);

    void deleteCustomerById(int id);


}
