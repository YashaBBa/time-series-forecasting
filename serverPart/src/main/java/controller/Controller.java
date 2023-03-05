package controller;

import com.example.client.model.Result;
import dao.StarterDao;
import dao.impl.StarterDaoImpl;
import com.example.client.model.User;
import com.example.client.model.enums.ControllerEnum;
import service.AdminService;
import service.ServiceException;
import service.ServiceFactory;
import service.StarterService;

import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class Controller implements Runnable {
    InputStream inputStream = null;
    OutputStream outputStream = null;
    ObjectInputStream objectInputStream = null;
    ObjectOutputStream objectOutputStream = null;
    private int clientsCounter;
    private Socket client;

    public Controller(int clientsCounter, Socket client) {
        this.clientsCounter = clientsCounter;
        this.client = client;
    }

    @Override
    public void run() {

        System.out.println("==============================");
        System.out.println("Client " + clientsCounter + " connected");
        try {
            inputStream = client.getInputStream();
            outputStream = client.getOutputStream();
            objectOutputStream = new ObjectOutputStream(outputStream);
            objectInputStream = new ObjectInputStream(inputStream);

            boolean flag = true;
            while (flag) {

                ControllerEnum controllerEnum = (ControllerEnum) objectInputStream.readObject();


                String result;
                StarterDao starterDao = new StarterDaoImpl();
                ServiceFactory serviceFactory = ServiceFactory.getInstance();
                StarterService starterService = serviceFactory.getStarterService();
                AdminService adminService = serviceFactory.getAdminService();
                User user = new User();

                switch (controllerEnum) {
                    case LOGINATION:
                        user = (User) objectInputStream.readObject();
                        // result= connectionHandler.logIn(words[1], words[2]);
                        // result = starterDao.logIn(words[1], words[2]);
                        user = starterService.logIn(user.getLogin(), user.getPassword());


                        objectOutputStream.writeObject(user);
                        break;
                    case REGISTRATION:
                        user = (User) objectInputStream.readObject();
                        //   result = starterService.registration(words[1], words[2], words[3], words[4], words[5]);
                        Boolean res = starterService.registration(user);

                        objectOutputStream.writeObject(res);
                        break;
                    case FINDBYID:
                        user = (User) objectInputStream.readObject();
                        user = adminService.findById(user.getId());

                        objectOutputStream.writeObject(user);
                        break;
                    case FINDBYLOGIN:
                        user = (User) objectInputStream.readObject();
                        user = adminService.findByLogin(user.getLogin());

                        objectOutputStream.writeObject(user);
                        break;
                    case FINDALL:
                        List<User> list = new ArrayList<>();
                        list = adminService.findAll();
                        objectOutputStream.writeObject(list);

                        break;
                    case DELETEBYID:
                        user = (User) objectInputStream.readObject();
                        Boolean aBoolean = adminService.deleteByID(user.getId());


                        objectOutputStream.writeObject(aBoolean);
                        break;
                    case DELETEBYLOGIN:
                        user = (User) objectInputStream.readObject();
                        Boolean bBoolean = adminService.deleteByLogin(user.getLogin());
                        objectOutputStream.writeObject(bBoolean);
                        break;
                    case UPDATEUSERROLE:
                        user = (User) objectInputStream.readObject();
                        Boolean aBoolean1 = adminService.updateUserRole(user.getId(), user.getLogin());

                        objectOutputStream.writeObject(aBoolean1);
                        break;
                    case SAVERESULT:
                        Result result1 = (Result) objectInputStream.readObject();
                        Integer id = (Integer) objectInputStream.readObject();
                        Boolean booleans = starterService.saveResult(result1, id);
                        objectOutputStream.writeObject(booleans);
                        break;
                    case GETRESULTS:
                        user = (User) objectInputStream.readObject();
                        List<Result> list1 = starterService.setAllResults(user.getId());
                        objectOutputStream.writeObject(list1);
                        break;
                    default:
                        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXxx");
                }


            }
            objectOutputStream.close();
            objectInputStream.close();
            inputStream.close();
            outputStream.close();

            System.out.println("Client " + clientsCounter + " disconnected");
        } catch (IOException e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } catch (ServiceException e) {
            throw new RuntimeException(e.getMessage(), e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
