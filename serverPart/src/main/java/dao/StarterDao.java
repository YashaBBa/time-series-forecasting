package dao;

import com.example.client.model.Result;
import com.example.client.model.User;

import java.util.List;

public interface StarterDao {
    User logIn(String login, String password) throws  DAOException;

    String registraton(String word, String word1, String word2, String word3, String word4) throws DAOException;


    Boolean registraton(User regUser) throws DAOException;

    Boolean saveResult(Result result, Integer id) throws DAOException;



    List<Result> getAllResults(int id) throws DAOException;
}
