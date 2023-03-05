package service;

import service.impl.AdminServiceImpl;
import service.impl.StarterServiceImpl;

public class ServiceFactory {
    private static final ServiceFactory instance = new ServiceFactory();

    private final StarterService starterService = new StarterServiceImpl();
    private final AdminService adminService = new AdminServiceImpl();

    public ServiceFactory() {
    }

    public static ServiceFactory getInstance() {
        return instance;
    }

    public StarterService getStarterService() {
        return starterService;
    }

    public AdminService getAdminService() {
        return adminService;
    }
}
