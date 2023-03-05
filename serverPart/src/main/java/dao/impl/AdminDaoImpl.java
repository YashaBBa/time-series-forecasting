package dao.impl;

import connection.pool.ConnectionPool;
import connection.pool.ConnectionPoolException;
import dao.AdminDAO;
import dao.DAOException;
import com.example.client.model.Client;
import com.example.client.model.Faces;
import com.example.client.model.Roles;
import com.example.client.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AdminDaoImpl implements AdminDAO {

    private static final String DATABASE_SERVER_CONNECTION_HAS_PROBLEM = "Database server connection has problem";
    private static final String SQL_EXCEPTION = "SQL Exception";
    public static final String LOGIN = "login";
    public static final String ROLES_ID = "roles_id";
    public static final String ID = "id";
    public static final String SELECT_FROM_CURSACGDB_CLIENTS_WHERE_ID = "SELECT  * FROM cursacgdb.clients where id=?";
    public static final String FIRSTNAME = "firstname";
    public static final String SURNAME = "surname";
    public static final String FACE_ID = "face_id";
    private static final String SELECT_FROM_CURSACGDB_USERS_WHERE_ID = "SELECT * FROM cursacgdb.users where id=?";
    private static final String SELECT_FROM_CURSACGDB_USERS_WHERE_LOGIN = "SELECT * FROM cursacgdb.users where login=?";

    private static final String SELECT_ALL_DATA_COMMAND = "SELECT cursacgdb.users.id,cursacgdb.users.login,cursacgdb.clients.firstname,cursacgdb.clients.surname,cursacgdb.roles.role,cursacgdb.face.face_type FROM cursacgdb.users\n" +
            "inner join cursacgdb.roles on cursacgdb.roles.id=cursacgdb.users.roles_id\n" +
            "inner join cursacgdb.clients on cursacgdb.clients.user_id=cursacgdb.users.id\n" +
            "inner join cursacgdb.face on cursacgdb.face.id=cursacgdb.clients.face_id";
    private final ConnectionPool connectionPool = ConnectionPool.getInstance();

    @Override
    public User findByLogin(String word) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";

        Client client = new Client();
        User user = new User();


        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(SELECT_FROM_CURSACGDB_USERS_WHERE_LOGIN);
            preparedStatement.setString(1, word);
            preparedStatement.execute();

            resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {

                user.setLogin(resultSet.getString(LOGIN));
                user.setRole(resultSet.getString(ROLES_ID));
                user.setId(resultSet.getInt(ID));

                preparedStatement = connection.prepareStatement(SELECT_FROM_CURSACGDB_CLIENTS_WHERE_ID);
                preparedStatement.setInt(1, user.getId());
                preparedStatement.execute();

                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    client.setName(resultSet.getString(FIRSTNAME));
                    client.setSurname(resultSet.getString(SURNAME));
                    client.setFace(resultSet.getInt(FACE_ID));

                }

                String role = Roles.map.get(user.getRole());
                String face = Faces.map.get(client.getFace());


                user.setName(client.getName());
                user.setSurname(client.getSurname());
                user.setFace(face);
                user.setRole(role);
            } else {
                user.setRole("0");

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
    public User findById(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";

        Client client = new Client();
        User user = new User();


        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(SELECT_FROM_CURSACGDB_USERS_WHERE_ID);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();

            resultSet = preparedStatement.getResultSet();
            if (resultSet.next()) {

                user.setLogin(resultSet.getString(LOGIN));
                user.setRole(resultSet.getString(ROLES_ID));
                user.setId(resultSet.getInt(ID));

                preparedStatement = connection.prepareStatement(SELECT_FROM_CURSACGDB_CLIENTS_WHERE_ID);
                preparedStatement.setInt(1, user.getId());
                preparedStatement.execute();

                resultSet = preparedStatement.getResultSet();
                if (resultSet.next()) {
                    client.setName(resultSet.getString(FIRSTNAME));
                    client.setSurname(resultSet.getString(SURNAME));
                    client.setFace(resultSet.getInt(FACE_ID));

                }

                String role = Roles.map.get(user.getRole());
                String face = Faces.map.get(client.getFace());


                user.setName(client.getName());
                user.setSurname(client.getSurname());
                user.setFace(face);
                user.setRole(role);
            } else {
                user.setRole("0");


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
    public List<User> findAll() throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;
        String result = "";

        Client client = new Client();
        List<User> list = new ArrayList<>();


        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement(SELECT_ALL_DATA_COMMAND);
            preparedStatement.execute();

            resultSet = preparedStatement.getResultSet();
            while (resultSet.next()) {
                User user = new User();
                result += resultSet.getString("id") + " " + resultSet.getString("login") +
                        " " + resultSet.getString("firstname") +
                        " " + resultSet.getString("surname") +
                        " " + resultSet.getString("role") +
                        " " + resultSet.getString("face_type") + "\n";
                user.setId(resultSet.getInt("id"));
                user.setLogin(resultSet.getString("login"));
                user.setName(resultSet.getString("firstname"));
                user.setSurname(resultSet.getString("surname"));
                user.setRole(resultSet.getString("role"));
                user.setFace(resultSet.getString("face_type"));
                list.add(user);

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

    @Override
    public Boolean deleteById(int id) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean result = true;


        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM cursacgdb.users where id=?");
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
            result = true;


        } catch (SQLException e) {
            result = false;

            throw new DAOException(SQL_EXCEPTION, e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(DATABASE_SERVER_CONNECTION_HAS_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }

        return result;
    }

    @Override
    public Boolean deleteByLogin(String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean result = true;


        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement("DELETE FROM cursacgdb.users where login=?");
            preparedStatement.setString(1, login);
            preparedStatement.execute();
            result = true;


        } catch (SQLException e) {
            result = false;
            throw new DAOException(SQL_EXCEPTION, e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(DATABASE_SERVER_CONNECTION_HAS_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }

        return result;
    }

    @Override
    public Boolean updateUser(int roleId, String login) throws DAOException {
        Connection connection = null;
        PreparedStatement preparedStatement = null;
        Boolean result = true;


        try {
            connection = connectionPool.takeConnection();

            preparedStatement = connection.prepareStatement("UPDATE cursacgdb.users set roles_id=? where login=?");
            preparedStatement.setInt(1, roleId);
            preparedStatement.setString(2, login);
            preparedStatement.execute();
            result = true;


        } catch (SQLException e) {
            result = false;
            throw new DAOException(SQL_EXCEPTION, e);
        } catch (ConnectionPoolException e) {
            throw new DAOException(DATABASE_SERVER_CONNECTION_HAS_PROBLEM, e);
        } finally {
            connectionPool.closeConnection(connection, preparedStatement);
        }

        return result;
    }
}
