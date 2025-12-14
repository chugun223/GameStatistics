package games_project.infrastructure.parser;

import games_project.model.Game.game;
import games_project.model.entity.gameEntity;
import games_project.model.repository.DBmanager;

import java.sql.SQLException;
import java.util.List;

public class DataDownloader {
    public DBmanager db = null;

    public DataDownloader(DBmanager db) throws SQLException {
        this.db = db;

        List<gameEntity> existing = db.getAllGames();

        if (existing.isEmpty()) {
            System.out.println("БД пуста, загружаем из CSV");

            var parser = new CSV_parser();
            List<game> games;

            try {
                games = parser.parseGames();
            } catch (Exception e) {
                e.printStackTrace();
                return;
            }

            for (game g : games) {
                gameEntity entity = new gameEntity(
                        g.rank, g.name, g.platform, g.year, g.genre,
                        g.publisher, g.naSales, g.euSales, g.jpSales,
                        g.otherSales, g.globalSales
                );
                db.saveGame(entity);
            }
        }
    }
}
