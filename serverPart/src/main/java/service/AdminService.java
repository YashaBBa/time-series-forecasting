package service;

import com.example.client.model.User;

import java.util.List;

public interface AdminService {
    User findByLogin(String login) throws ServiceException;

    User findById(int id) throws ServiceException;

    List<User> findAll() throws ServiceException;

    Boolean deleteByID(int id) throws ServiceException;

    Boolean deleteByLogin(String login) throws ServiceException;

    Boolean updateUserRole(int id, String login) throws ServiceException;

}
