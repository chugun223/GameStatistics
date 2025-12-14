package games_project.controller;

import games_project.service.CSV_parser;
import games_project.model.Game.game;
import games_project.model.entity.gameEntity;
import games_project.model.repository.DBmanager;
import games_project.model.resolver.Resolver;
import games_project.view_desktop.chart.chartDrawer;
import games_project.view_desktop.chart.chartMapper;
import org.jfree.data.category.DefaultCategoryDataset;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Controller {

    private final Resolver resolver;
    private final chartDrawer chartDrawer;
    private final DBmanager db;

    public Controller(Resolver resolver, chartDrawer chartDrawer, DBmanager db) {
        this.resolver = resolver;
        this.chartDrawer = chartDrawer;
        this.db = db;
    }

    public String getMostSellingInEurope() {
        game game = resolver.getMostSellingGameInEurope();
        return game == null ? "Нет данных" : game.getName();
    }

    public String getBestSportsJapan2000_2006() {
        var game = resolver.getMostSellingSportGameInJapanBetween2000And2006();
        return game == null ? "Нет данных" : game.getName();
    }

    public void showSalesChart() {
        var salesByPlatform = resolver.getAverageGlobalSalesByPlatform();
        DefaultCategoryDataset dataset = chartMapper.mapToDataset(salesByPlatform);
        chartDrawer.drawBarChart(dataset, "Мировые продажи по платформам", "Платформа", "Глобальные продажи");
    }

    public String getMostProfitableGenre() {
        return resolver.getMostProfitableGenre();
    }

    public String getGenrePreferencesByRegion(){
        return resolver.getGenrePreferencesByRegion();
    }

    public void rewriteDB() throws SQLException {
        db.clearGames();
        var parser = new CSV_parser();
        List<game> games;

        try {
            games = parser.parseGames();
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        for (game g : games) {
            gameEntity entity = new gameEntity(g.rank, g.name, g.platform, g.year, g.genre, g.publisher, g.naSales, g.euSales, g.jpSales, g.otherSales, g.globalSales);
            db.saveGame(entity);
        }
    }

    public void saveChartAsFile(String path) throws IOException {
        var salesByPlatform = resolver.getAverageGlobalSalesByPlatform();
        DefaultCategoryDataset dataset = chartMapper.mapToDataset(salesByPlatform);
        chartDrawer.saveBarChart(dataset, "Продажи", "Платформа", "Продажи", path);
    }

    public String getPlatformWithMostGenres(){
        return resolver.getPlatformWithMostGenres();
    }

    public String getPublisherWithMostReleases(){
        return resolver.getPublisherWithMostRelease();
    }

    public String getYearWithMostReleases(){
        return resolver.getYearWithMostRelease();
    }
}