package dao;

import dao.impl.StarterDaoImpl;
import org.junit.Test;

import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;

public class DaoTests {


    @Test
    public void logIn_If_UserIsReal() throws SQLException, ClassNotFoundException, DAOException {
        StarterDao starterDao = new StarterDaoImpl();
        assertThat(starterDao).isNotNull();
        assertThat(starterDao.logIn("admin", "admin")).isEqualTo("1");
    }

    @Test
    public void logIn_If_UserIsNotReal() throws SQLException, ClassNotFoundException, DAOException {
        StarterDao starterDao = new StarterDaoImpl();
        assertThat(starterDao).isNotNull();
        assertThat(starterDao.logIn(" ", " ")).isEqualTo("false");
    }

    @Test
    public void registration_returnFalse_if_userNotNewLogin() throws SQLException, ClassNotFoundException, DAOException {
        StarterDao starterDao = new StarterDaoImpl();
        assertThat(starterDao).isNotNull();
        assertThat(starterDao.registraton("admin", "admin", "admin", "admin", "admin"))
                .isEqualTo("false");
    }

}
