package games_project.model.resolver;

import games_project.model.Game.game;
import games_project.model.entity.gameEntity;
import games_project.model.repository.DBmanager;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class Resolver implements IResolver {
    private List<gameEntity> games;

    public Resolver(DBmanager db) throws SQLException {
        this.games = db.getAllGames();
    }

    @Override
    public Map<String, Double> getAverageGlobalSalesByPlatform() {
        return games.stream()
                .collect(Collectors.groupingBy(gameEntity::getPlatform, Collectors.collectingAndThen(Collectors.summarizingDouble(gameEntity::getGlobalSales), DoubleSummaryStatistics::getSum)));
    }

    @Override
    public game getMostSellingGameInEurope() {
        return games.stream()
                .max(Comparator.comparing(gameEntity::getEUSales))
                .map(entity -> new game(entity.getRank(), entity.getName(), entity.getPlatform(), entity.getYear(), entity.getGenre(), entity.getPublisher(), entity.getNASales(), entity.getEUSales(), entity.getJPSales(), entity.getOtherSales(), entity.getGlobalSales()))
                .orElse(null);
    }

    @Override
    public game getMostSellingSportGameInJapanBetween2000And2006() {
        return games.stream()
                .filter(game -> "Sports".equals(game.getGenre()) && game.getYear() >= 2000 && game.getYear() <= 2006)
                .max(Comparator.comparing(gameEntity::getJPSales))
                .map(entity -> new game(entity.getRank(), entity.getName(), entity.getPlatform(), entity.getYear(), entity.getGenre(), entity.getPublisher(), entity.getNASales(), entity.getEUSales(), entity.getJPSales(), entity.getOtherSales(), entity.getGlobalSales()))
                .orElse(null);
    }

    public String getMostProfitableGenre() {
        Map<String, Double> genreRevenue = games.stream()
                .collect(Collectors.groupingBy(
                        gameEntity::getGenre,
                        Collectors.summingDouble(gameEntity::getGlobalSales)
                ));

        Map.Entry<String, Double> mostProfitable = genreRevenue.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if (mostProfitable == null) {
            return "Нет данных о жанрах";
        }

        return String.format("%s (общие продажи: %.2f млн.)", mostProfitable.getKey(), mostProfitable.getValue());
    }

    public String getGenrePreferencesByRegion() {
        String naGenres = games.stream()
                .filter(gameEntity -> gameEntity.getGenre() != null)
                .collect(Collectors.groupingBy(
                        gameEntity::getGenre,
                        Collectors.summingDouble(gameEntity::getNASales))).entrySet()
                .stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(entry -> String.format("Самый популярный жанр в Северной Америке: %s (%.2f млн.) \n",
                        entry.getKey(), entry.getValue()))
                .orElse("Нет данных");

        String euGenres = games.stream()
                .filter(gameEntity -> gameEntity.getGenre() != null)
                .collect(Collectors.groupingBy(
                        gameEntity::getGenre,
                        Collectors.summingDouble(gameEntity::getEUSales))).entrySet()
                .stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(entry -> String.format("Самый популярный жанр в Европе: %s (%.2f млн.) \n",
                        entry.getKey(), entry.getValue()))
                .orElse("Нет данных");

        String jpGenres = games.stream()
                .filter(gameEntity -> gameEntity.getGenre() != null)
                .collect(Collectors.groupingBy(
                        gameEntity::getGenre,
                        Collectors.summingDouble(gameEntity::getJPSales))).entrySet()
                .stream()
                .max(Comparator.comparingDouble(Map.Entry::getValue))
                .map(entry -> String.format("Самый популярный жанр в Японии: %s (%.2f млн.) \n",
                        entry.getKey(), entry.getValue()))
                .orElse("Нет данных");

        StringBuilder result = new StringBuilder();
        result.append("Региональные предпочтения по жанрам:\n");
        result.append(naGenres).append(euGenres).append(jpGenres);
        return result.toString();
    }

    public String getPublisherWithMostRelease() {
        Map<String, Long> publisherCount = games.stream()
                .filter(entity -> entity.getPublisher() != null)
                .collect(Collectors.groupingBy(
                        gameEntity::getPublisher,
                        Collectors.counting()));

        Optional<Map.Entry<String, Long>> topPublisher = publisherCount.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        return topPublisher.map(entry ->
                String.format("Издатель с наибольшим количеством релизов: %s (%d игр)", entry.getKey(), entry.getValue()))
                .orElse("Нет данных о издателях");
    }

    public String getYearWithMostRelease() {
        Map<Integer, Long> yearCount = games.stream()
                .filter(game -> game.getYear() > 0)
                .collect(Collectors.groupingBy(gameEntity::getYear, Collectors.counting()));

        Optional<Map.Entry<Integer, Long>> topYear = yearCount.entrySet().stream()
                .max(Map.Entry.comparingByValue());

        return topYear.map(entry ->
                String.format("Год с наибольшим количеством релизов: %d (%d игр)",
                        entry.getKey(), entry.getValue()))
                .orElse("Нет данных по годам");
    }

    public String getPlatformWithMostGenres() {
        Map<String, Long> platformGenresCount = games.stream()
                .collect(Collectors.groupingBy(
                        gameEntity::getPlatform,
                        Collectors.mapping(gameEntity::getGenre, Collectors.toSet())))
                .entrySet().stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> (long) entry.getValue().size()));

        Map.Entry<String, Long> topPlatform = platformGenresCount.entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .orElse(null);

        if (topPlatform == null) {
            return "Нет данных о платформах";
        }

        return String.format("Платформа с наибольшим разнообразием жанров: %s (%d жанров)",
                topPlatform.getKey(), topPlatform.getValue());
    }
}