package com.alperovich.fishbook.management.DAO.impl;

import com.alperovich.fishbook.management.DAO.PartnerDao;
import com.alperovich.fishbook.management.DB.DBConnector;
import com.alperovich.fishbook.management.models.Partner;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;

public class PartnerDaoImpl implements PartnerDao {

    private Partner buildPartner(ResultSet rs) throws SQLException {
        Partner partner = new Partner();
        partner.setId(rs.getInt("id"));
        partner.setCompanyName(rs.getString("companyName"));
        partner.setAddress(rs.getString("address"));
        partner.setPhone(rs.getString("phone"));
        partner.setLakeName(rs.getString("lakeName"));
        partner.setServiceName(rs.getString("serviceName"));
        partner.setServiceInfo(rs.getString("serviceInfo"));
        partner.setPrice(rs.getDouble("price"));
        return partner;

    }

    @Override
    public ObservableList<Partner> getAllPartners() {
        Partner partner;
        ObservableList<Partner> partners = FXCollections.observableArrayList();
        String sql = "SELECT * FROM fishbooking_db.partners";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                partner = buildPartner(rs);
                partners.add(partner);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partners;
    }

    @Override
    public ObservableList<Partner> getServiceByLake(String lakeName) {
        Partner partner;
        ObservableList<Partner> partners = FXCollections.observableArrayList();
        String sql = "SELECT * FROM fishbooking_db.partners";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                partner = buildPartner(rs);
                if (partner.getLakeName().equals(lakeName)) {
                    partners.add(partner);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partners;
    }

    @Override
    public HashSet<String> getAllLakes() {
        HashSet<String> lakes = new HashSet<>();
        String sql = "SELECT partners.lakeName FROM fishbooking_db.partners";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet result = pstmt.executeQuery();
            while (result.next()) {
                lakes.add(result.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lakes;
    }

    @Override
    public void updatePartner(Partner partner) {
        String sql = "UPDATE fishbooking_db.partners SET companyName = ?, address = ?, phone = ?, lakeName = ?, serviceName = ?, serviceInfo = ?, price = ? WHERE partners.id = ?";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, partner.getCompanyName());
            pstmt.setString(2, partner.getAddress());
            pstmt.setString(3, partner.getPhone());
            pstmt.setString(4, partner.getLakeName());
            pstmt.setString(5, partner.getServiceName());
            pstmt.setString(6, partner.getServiceInfo());
            pstmt.setDouble(7, partner.getPrice());
            pstmt.setInt(8, partner.getId());
            if (pstmt.executeUpdate() == 1) {
                System.out.println("Row is Updated");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createPartner(Partner partner) {
        String sql = "INSERT INTO fishbooking_db.partners (companyName, address, phone, lakeName, serviceName, serviceInfo, price) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, partner.getCompanyName());
            pstmt.setString(2, partner.getAddress());
            pstmt.setString(3, partner.getPhone());
            pstmt.setString(4, partner.getLakeName());
            pstmt.setString(5, partner.getServiceName());
            pstmt.setString(6, partner.getServiceInfo());
            pstmt.setDouble(7, partner.getPrice());
            if (pstmt.executeUpdate() == 1) {
                System.out.println("Row created");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletePartnerById(int id) {
        String sql = "DELETE FROM fishbooking_db.partners WHERE id = ?";
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
