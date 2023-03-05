package dao;

import com.example.client.model.User;

import java.util.List;

public interface AdminDAO {
    User findByLogin(String word) throws DAOException;

    User findById(int word) throws DAOException;

    List<User> findAll() throws DAOException;

    Boolean deleteById(int id) throws DAOException;

    Boolean deleteByLogin(String login) throws DAOException;

    Boolean updateUser(int user, String login) throws DAOException;
}
