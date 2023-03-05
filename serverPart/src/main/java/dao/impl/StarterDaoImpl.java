package dao.impl;

import com.example.client.model.Client;
import com.example.client.model.Result;
import com.example.client.model.User;
import connection.pool.ConnectionPool;
import connection.pool.ConnectionPoolException;
import dao.DAOException;
import dao.StarterDao;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StarterDaoImpl implements StarterDao {

    private static final String DATABASE_SERVER_CONNECTION_HAS_PROBLEM = "Database server connection has problem";
    private static final String SQL_EXCEPTION = "SQL Exception";


    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public User logIn(String login, String password) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        User user = new User();
        password = DigestUtils.md5Hex(password);
        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM cursacgdb.users where login=? and password=?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.execute();

            resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {

                user.setRole(resultSet.getString("roles_id"));
                user.setId(resultSet.getInt("id"));

            } else {
                result = "false";
            }


        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(DATABASE_SERVER_CONNECTION_HAS_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return user;
    }

    @Override
    public String registraton(String login, String password, String name, String surname, String faceId) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM cursacgdb.users where login=? and password=?");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.execute();

            resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return "false";
            }


            //reg
            preparedStatement = connection.prepareStatement("INSERT INTO cursacgdb.users (login,password) VALUES (?,?)");
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, password);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("SELECT * FROM cursacgdb.users where login=?");
            preparedStatement.setString(1, login);
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            Integer id = resultSet.getInt("id");
            preparedStatement = connection.prepareStatement("INSERT INTO cursacgdb.clients (firstname,surname,face_id,user_id,id) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, surname);
            preparedStatement.setInt(3, Integer.parseInt(faceId));
            preparedStatement.setInt(4, id);
            preparedStatement.setInt(5, id);
            preparedStatement.execute();


        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(DATABASE_SERVER_CONNECTION_HAS_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return "true";
    }

    @Override
    public Boolean registraton(User regUser) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";
        String hashPassword = DigestUtils.md5Hex(regUser.getPassword());
        try {
            connection = connectionPool.takeConnection();
            preparedStatement = connection.prepareStatement("SELECT * FROM cursacgdb.users where login=?");
            preparedStatement.setString(1, regUser.getLogin());

            preparedStatement.execute();

            resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {
                return false;
            }


            //reg
            preparedStatement = connection.prepareStatement("INSERT INTO cursacgdb.users (login,password) VALUES (?,?)");
            preparedStatement.setString(1, regUser.getLogin());
            preparedStatement.setString(2, hashPassword);
            preparedStatement.execute();

            preparedStatement = connection.prepareStatement("SELECT * FROM cursacgdb.users where login=?");
            preparedStatement.setString(1, regUser.getLogin());
            preparedStatement.execute();
            resultSet = preparedStatement.getResultSet();
            resultSet.next();
            Integer id = resultSet.getInt("id");
            preparedStatement = connection.prepareStatement("INSERT INTO cursacgdb.clients (firstname,surname,face_id,user_id,id) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, regUser.getName());
            preparedStatement.setString(2, regUser.getSurname());
            preparedStatement.setInt(3, Integer.parseInt(regUser.getFace()));
            preparedStatement.setInt(4, id);
            preparedStatement.setInt(5, id);
            preparedStatement.execute();


        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(DATABASE_SERVER_CONNECTION_HAS_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return true;
    }

    @Override
    public Boolean saveResult(Result results, Integer id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;

        String result = "";
        try {
            connection = connectionPool.takeConnection();

            //saveResultTable
            preparedStatement = connection.prepareStatement("INSERT INTO cursacgdb.result (resut,time_stap,analiz_way_id,clientID,date) VALUES (?,?,?,?,?)");
            preparedStatement.setString(1, results.getResult());
            preparedStatement.setString(2, "" + results.getTimestap());
            preparedStatement.setInt(3, results.getAnalizWayId());
            preparedStatement.setInt(4, id);
            preparedStatement.setDate(5, results.getDate());
            preparedStatement.execute();


        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(DATABASE_SERVER_CONNECTION_HAS_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }

        return true;
    }

    @Override
    public List<Result> getAllResults(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";

        Client client = new Client();
        List<Result> list = new ArrayList<>();


        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement("SELECT * FROM cursacgdb.result where clientID=? ");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                Result result1 = new Result();
                result1.setId(resultSet.getInt("id"));
                result1.setResult(resultSet.getString("resut"));
                result1.setTimestap(resultSet.getInt("time_stap"));
                result1.setDate(resultSet.getDate("date"));

                list.add(result1);

            }


        } catch (SQLException e) {
            throw new DAOException(SQL_EXCEPTION, e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(DATABASE_SERVER_CONNECTION_HAS_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement, resultSet);
        }

        return list;

    }
}



