package dao;

import dao.impl.AdminDaoImpl;
import dao.impl.StarterDaoImpl;
import service.ServiceFactory;

public class DaoFactory {
    private static final DaoFactory instance = new DaoFactory();

    private final StarterDao starterDao = new StarterDaoImpl();
    private final AdminDAO adminDAO = new AdminDaoImpl();

    public AdminDAO getAdminDAO() {
        return adminDAO;
    }

    public DaoFactory() {

    }

    public static DaoFactory getInstance() {
        return instance;
    }

    public StarterDao getStarterDao() {
        return starterDao;
    }
}
