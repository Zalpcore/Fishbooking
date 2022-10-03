package com.alperovich.fishbook.management.DAO;

import com.alperovich.fishbook.management.models.User;
import javafx.collections.ObservableList;

public interface UserDAO {

    User findUserById(int id);

    User findUserByUsername(String username);

    ObservableList<User> getAllUsers();

    void updateUser(User user);

    void createUser(User user);

    void deleteUserById(int id);
}
