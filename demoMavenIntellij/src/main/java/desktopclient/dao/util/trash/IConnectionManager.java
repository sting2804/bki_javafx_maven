package desktopclient.dao.util.trash;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * Created by sting on 16.01.15.
 */
public interface IConnectionManager {
    /**
     * Получение объекта Connection
     *
     * @return возвращает объект класса Connection, полученный из объекта класса SybDataSource
     * @throws SQLException в случае неверных данных выбрасывает исключение
     */
    public Connection getConnection();
}
