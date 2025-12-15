package games_project.model.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.misc.TransactionManager;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import games_project.model.entity.gameEntity;

import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.Callable;

public class DBmanager {
    private final static String DATABASE_URL = "jdbc:sqlite:games.db";

    private Dao<gameEntity, Integer> gameDao;
    private ConnectionSource connectionSource;

    public void init() throws SQLException {
        connectionSource = new JdbcConnectionSource(DATABASE_URL);
        gameDao = DaoManager.createDao(connectionSource, gameEntity.class);
        TableUtils.createTableIfNotExists(connectionSource, gameEntity.class);
    }

    public void saveGame(gameEntity game) throws SQLException {
        gameDao.create(game);
    }

    public void runInTransaction(Callable<Void> action) throws SQLException {
        try {
            TransactionManager.callInTransaction(connectionSource, action);
        } catch (Exception e) {
            throw new SQLException(e);
        }
    }

    public List<gameEntity> getAllGames() throws SQLException {
        return gameDao.queryForAll();
    }

    public void clearGames() throws SQLException {
        gameDao.deleteBuilder().delete();
    }

    public void close() throws Exception {
        if (connectionSource != null) connectionSource.close();
    }
}