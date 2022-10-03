package com.alperovich.fishbook.management.models;

import java.sql.Date;

public class Customer {

    private int id;
    private String firstName;
    private String lastName;
    private String phone;
    private String lakeName;
    private String serviceName;
    private String serviceInfo;
    private Double price;
    private Date checkIn;
    private Date checkOut;

    public Customer() {
    }

    public Customer(String lakeName, String serviceName, String serviceInfo, Double price) {
        this.lakeName = lakeName;
        this.serviceName = serviceName;
        this.serviceInfo = serviceInfo;
        this.price = price;
    }

    public Customer(String lakeName) {
        this.lakeName = lakeName;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getLakeName() {
        return lakeName;
    }

    public void setLakeName(String lakeName) {
        this.lakeName = lakeName;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public String getServiceInfo() {
        return serviceInfo;
    }

    public void setServiceInfo(String serviceInfo) {
        this.serviceInfo = serviceInfo;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", lakeName='" + lakeName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceInfo='" + serviceInfo + '\'' +
                ", price=" + price +
                ", checkIn=" + checkIn +
                ", checkOut=" + checkOut +
                '}';
    }
}
