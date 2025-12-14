package games_project.model.repository;

import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;
import games_project.model.entity.gameEntity;

import java.sql.SQLException;
import java.util.List;

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