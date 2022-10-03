package com.alperovich.fishbook.management.DAO;

import com.alperovich.fishbook.management.models.Partner;
import javafx.collections.ObservableList;

import java.util.HashSet;

public interface PartnerDao {

    ObservableList<Partner> getAllPartners();

    ObservableList<Partner> getServiceByLake(String lakeName);

    HashSet<String> getAllLakes();

    void updatePartner(Partner partner);

    void createPartner(Partner partner);

    void deletePartnerById(int id);
}
