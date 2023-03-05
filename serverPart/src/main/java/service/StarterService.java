package service;

import com.example.client.model.Result;
import com.example.client.model.User;

import java.util.List;

public interface StarterService {
    User logIn(String login, String password) throws ServiceException;
    String registration(String word, String word1, String word2, String word3, String word4) throws ServiceException;


    Boolean registration(User regUser) throws ServiceException;

    Boolean saveResult(Result result, Integer id) throws ServiceException;

    List<Result> setAllResults(int id) throws ServiceException;

}
