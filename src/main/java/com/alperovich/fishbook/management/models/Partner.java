package com.alperovich.fishbook.management.models;


import java.util.Objects;

public class Partner {

    private int id;
    private String companyName;
    private String address;
    private String phone;
    private String lakeName;
    private String serviceName;
    private String serviceInfo;
    private Double price;

    public Partner() {
    }

    public Partner(String lakeName, String serviceName, String serviceInfo, Double price) {
        this.lakeName = lakeName;
        this.serviceName = serviceName;
        this.serviceInfo = serviceInfo;
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Partner partner = (Partner) o;
        return id == partner.id && Objects.equals(companyName, partner.companyName) && Objects.equals(address, partner.address) && Objects.equals(phone, partner.phone) && Objects.equals(lakeName, partner.lakeName) && Objects.equals(serviceName, partner.serviceName) && Objects.equals(serviceInfo, partner.serviceInfo) && Objects.equals(price, partner.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, companyName, address, phone, lakeName, serviceName, serviceInfo, price);
    }

    @Override
    public String toString() {
        return "Partner{" +
                "id=" + id +
                ", companyName='" + companyName + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", lakeName='" + lakeName + '\'' +
                ", serviceName='" + serviceName + '\'' +
                ", serviceInfo='" + serviceInfo + '\'' +
                ", price=" + price +
                '}';
    }
}
