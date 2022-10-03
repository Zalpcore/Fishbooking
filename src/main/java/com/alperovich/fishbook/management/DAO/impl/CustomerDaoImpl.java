package com.alperovich.fishbook.management.DAO.impl;

import com.alperovich.fishbook.management.DAO.CustomerDao;
import com.alperovich.fishbook.management.DB.DBConnector;
import com.alperovich.fishbook.management.models.Customer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerDaoImpl implements CustomerDao {

    private Customer buildCustomer(ResultSet rs) throws SQLException {
        Customer customer = new Customer();
        customer.setId(rs.getInt("id"));
        customer.setFirstName(rs.getString("firstName"));
        customer.setLastName(rs.getString("lastName"));
        customer.setPhone(rs.getString("phone"));
        customer.setLakeName(rs.getString("lakeName"));
        customer.setServiceName(rs.getString("serviceName"));
        customer.setServiceInfo(rs.getString("serviceInfo"));
        customer.setPrice(rs.getDouble("price"));
        customer.setCheckIn(rs.getDate("checkIn"));
        customer.setCheckOut(rs.getDate("checkOut"));
        return customer;
    }

    @Override
    public ObservableList<Customer> getAllCustomers() {
        Customer customer;
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM fishbooking_db.customers";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                customer = buildCustomer(rs);
                customers.add(customer);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public ObservableList<Customer> getCustomersByName(String firstName, String lastName) {
        Customer customer;
        ObservableList<Customer> customers = FXCollections.observableArrayList();
        String sql = "SELECT * FROM fishbooking_db.customers";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                customer = buildCustomer(rs);
                if (customer.getFirstName().equals(firstName) && customer.getLastName().equals(lastName)) {
                    customers.add(customer);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return customers;
    }

    @Override
    public void createCustomer(Customer customer) {
        String sql = "INSERT INTO fishbooking_db.customers (firstName, lastName, phone, lakeName, serviceName, serviceInfo, price, checkIn, checkOut) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getLakeName());
            pstmt.setString(5, customer.getServiceName());
            pstmt.setString(6, customer.getServiceInfo());
            pstmt.setDouble(7, customer.getPrice());
            pstmt.setDate(8, customer.getCheckIn());
            pstmt.setDate(9, customer.getCheckOut());
            if (pstmt.executeUpdate() == 1) {
                System.out.println("Row created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCustomer(Customer customer) {
        String sql = "UPDATE fishbooking_db.customers SET firstName = ?, lastName = ?, phone = ?, lakeName = ?, serviceName = ?, serviceInfo = ?, price = ?, checkIn = ?, checkOut = ? WHERE customers.id = ?";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, customer.getFirstName());
            pstmt.setString(2, customer.getLastName());
            pstmt.setString(3, customer.getPhone());
            pstmt.setString(4, customer.getLakeName());
            pstmt.setString(5, customer.getServiceName());
            pstmt.setString(6, customer.getServiceInfo());
            pstmt.setDouble(7, customer.getPrice());
            pstmt.setDate(8, customer.getCheckIn());
            pstmt.setDate(9, customer.getCheckOut());
            pstmt.setInt(10, customer.getId());
            if (pstmt.executeUpdate() == 1) {
                System.out.println("Row is Updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteCustomerById(int id) {
        String sql = "DELETE FROM fishbooking_db.customers WHERE id = ?";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            if (pstmt.executeUpdate() == 1) {
                System.out.println("Row Deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}

