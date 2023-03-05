package service.impl;

import com.example.client.model.User;
import dao.AdminDAO;
import dao.DAOException;
import dao.DaoFactory;
import service.AdminService;
import service.ServiceException;

import java.util.ArrayList;
import java.util.List;

public class AdminServiceImpl implements AdminService {
    @Override
    public User findByLogin(String word) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        AdminDAO adminDAO = daoFactory.getAdminDAO();
        User result = null;
        try {
            result = adminDAO.findByLogin(word);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public User findById(int word) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        AdminDAO adminDAO = daoFactory.getAdminDAO();
        User result = null;
        try {
            result = adminDAO.findById(word);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public List<User> findAll() throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        AdminDAO adminDAO = daoFactory.getAdminDAO();
        List<User> result = new ArrayList<>();
        try {
            result = adminDAO.findAll();
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Boolean deleteByID(int id) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        AdminDAO adminDAO = daoFactory.getAdminDAO();
        Boolean result = false;
        try {
            result = adminDAO.deleteById(id);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;
    }

    @Override
    public Boolean deleteByLogin(String login) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        AdminDAO adminDAO = daoFactory.getAdminDAO();
        Boolean result = null;
        try {
            result = adminDAO.deleteByLogin(login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;

    }

    @Override
    public Boolean updateUserRole(int id, String login) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        AdminDAO adminDAO = daoFactory.getAdminDAO();
        Boolean result = null;
        try {
            result = adminDAO.updateUser(id, login);
        } catch (DAOException e) {
            throw new ServiceException(e);
        }
        return result;

    }
}
