package service.impl;

import com.example.client.model.Result;
import com.example.client.model.User;
import dao.DAOException;
import dao.DaoFactory;
import dao.StarterDao;
import service.ServiceException;
import service.StarterService;

import java.util.ArrayList;
import java.util.List;

public class StarterServiceImpl implements StarterService {

    public static final String DATABASE_CONNECTION_EXCEPTION_TXT = "Database server connection has problem";

    @Override
    public User logIn(String login, String password) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        StarterDao starterDao = daoFactory.getStarterDao();
        User result = null;
        try {
            result = starterDao.logIn(login, password);
        } catch (DAOException e) {
            throw new ServiceException(DATABASE_CONNECTION_EXCEPTION_TXT, e);
        }

        return result;
    }

    @Override
    public String registration(String word, String word1, String word2, String word3, String word4) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        StarterDao starterDao = daoFactory.getStarterDao();
        String result = "null";
        try {
            result = starterDao.registraton(word, word1, word2, word3, word4);
        } catch (DAOException e) {
            throw new ServiceException(DATABASE_CONNECTION_EXCEPTION_TXT, e);
        }
        return result;
    }

    @Override
    public Boolean registration(User regUser) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        StarterDao starterDao = daoFactory.getStarterDao();
        Boolean result = false;
        try {
            result = starterDao.registraton(regUser);
        } catch (DAOException e) {
            throw new ServiceException(DATABASE_CONNECTION_EXCEPTION_TXT, e);
        }
        return result;

    }

    @Override
    public Boolean saveResult(Result result, Integer id) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        StarterDao starterDao = daoFactory.getStarterDao();
        Boolean results = false;
        try {
            results=starterDao.saveResult(result,id);
        } catch (DAOException e) {
            throw new ServiceException(DATABASE_CONNECTION_EXCEPTION_TXT, e);
        }

        return results;
    }

    @Override
    public List<Result> setAllResults(int id) throws ServiceException {
        DaoFactory daoFactory = DaoFactory.getInstance();
        StarterDao starterDao = daoFactory.getStarterDao();
        List<Result> results = new ArrayList<>();
        try {
            results=starterDao.getAllResults(id);
        } catch (DAOException e) {
            throw new ServiceException(DATABASE_CONNECTION_EXCEPTION_TXT, e);
        }

        return results;
    }


}
