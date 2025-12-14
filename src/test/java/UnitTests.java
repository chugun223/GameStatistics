import games_project.model.Game.game;
import games_project.model.entity.gameEntity;
import games_project.model.repository.DBmanager;
import games_project.model.resolver.Resolver;
import org.junit.jupiter.api.Test;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class UnitTests {

    private final DBmanager mockDB = mock(DBmanager.class);

    private Resolver resolverWith(List<gameEntity> games) throws SQLException {
        when(mockDB.getAllGames()).thenReturn(games);
        return new Resolver(mockDB);
    }

    @Test
    void getMostSellingGameInEurope_oneGame() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "GameEU", "PC", 2020, "Action", "EA",
                        1, 10, 1, 1, 13)
        ));

        game result = resolver.getMostSellingGameInEurope();
        assertNotNull(result);
        assertEquals("GameEU", result.getName());
    }

    @Test
    void getMostSellingGameInEurope_noGames() throws SQLException {
        Resolver resolver = resolverWith(List.of());
        assertNull(resolver.getMostSellingGameInEurope());
    }

    @Test
    void getMostSellingSportGameInJapanBetween2000And2006_ok() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PS2", 2001, "Sports", "Konami", 1, 1, 5, 1, 8),
                new gameEntity(2, "B", "PS2", 2003, "Sports", "Konami", 1, 1, 9, 1, 12)
        ));

        game result = resolver.getMostSellingSportGameInJapanBetween2000And2006();
        assertEquals("B", result.getName());
    }

    @Test
    void getMostSellingSportGameInJapanBetween2000And2006_noMatch() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PS2", 1999, "Sports", "Konami", 1, 1, 5, 1, 8)
        ));

        assertNull(resolver.getMostSellingSportGameInJapanBetween2000And2006());
    }

    @Test
    void getAverageGlobalSalesByPlatform() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1, 1, 1, 1, 4),
                new gameEntity(2, "B", "PC", 2021, "Action", "EA", 1, 1, 1, 1, 6)
        ));

        Map<String, Double> result = resolver.getAverageGlobalSalesByPlatform();
        assertEquals(10.0, result.get("PC"));
    }

    @Test
    void getMostProfitableGenre_ok() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1, 1, 1, 1, 10),
                new gameEntity(2, "B", "PC", 2021, "RPG", "EA", 1, 1, 1, 1, 5)
        ));

        String result = resolver.getMostProfitableGenre();
        assertTrue(result.contains("Action"));
    }

    @Test
    void getMostProfitableGenre_empty() throws SQLException {
        Resolver resolver = resolverWith(List.of());
        assertEquals("Нет данных о жанрах", resolver.getMostProfitableGenre());
    }

    @Test
    void getGenrePreferencesByRegion() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 10, 1, 1, 1, 13)
        ));

        String result = resolver.getGenrePreferencesByRegion();
        assertTrue(result.contains("Северной Америке"));
        assertTrue(result.contains("Европе"));
        assertTrue(result.contains("Японии"));
    }

    @Test
    void getPublisherWithMostRelease() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1, 1, 1, 1, 4),
                new gameEntity(2, "B", "PC", 2021, "Action", "EA", 1, 1, 1, 1, 4)
        ));

        String result = resolver.getPublisherWithMostRelease();
        assertTrue(result.contains("EA"));
    }

    @Test
    void getYearWithMostRelease() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1, 1, 1, 1, 4),
                new gameEntity(2, "B", "PC", 2020, "Action", "EA", 1, 1, 1, 1, 4)
        ));

        String result = resolver.getYearWithMostRelease();
        assertTrue(result.contains("2020"));
    }

    @Test
    void getPlatformWithMostGenres() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1, 1, 1, 1, 4),
                new gameEntity(2, "B", "PC", 2021, "RPG", "EA", 1, 1, 1, 1, 4)
        ));

        String result = resolver.getPlatformWithMostGenres();
        assertTrue(result.contains("PC"));
    }

    @Test
    void getAverageGlobalSalesByPlatform_empty() throws SQLException {
        Resolver resolver = resolverWith(List.of());
        assertTrue(resolver.getAverageGlobalSalesByPlatform().isEmpty());
    }

    @Test
    void getGenrePreferencesByRegion_empty() throws SQLException {
        Resolver resolver = resolverWith(List.of());
        String result = resolver.getGenrePreferencesByRegion();
        assertTrue(result.contains("Нет данных"));
    }

    @Test
    void getPublisherWithMostRelease_empty() throws SQLException {
        Resolver resolver = resolverWith(List.of());
        assertEquals("Нет данных о издателях", resolver.getPublisherWithMostRelease());
    }

    @Test
    void getYearWithMostRelease_noValidYears() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", -1, "Action", "EA", 1,1,1,1,4)
        ));

        assertEquals("Нет данных по годам", resolver.getYearWithMostRelease());
    }

    @Test
    void getPlatformWithMostGenres_empty() throws SQLException {
        Resolver resolver = resolverWith(List.of());
        assertEquals("Нет данных о платформах", resolver.getPlatformWithMostGenres());
    }

    @Test
    void getYearWithMostRelease_empty() throws SQLException {
        when(mockDB.getAllGames()).thenReturn(List.of());
        Resolver resolver = new Resolver(mockDB);
        assertEquals("Нет данных по годам", resolver.getYearWithMostRelease());
    }

    @Test
    void getMostSellingSportGameInJapanBetween2000And2006_nonSports() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PS2", 2001, "Action", "Konami", 1,1,5,1,8)
        ));
        assertNull(resolver.getMostSellingSportGameInJapanBetween2000And2006());
    }

    @Test
    void getMostSellingSportGameInJapanBetween2000And2006_outOfYearRange() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PS2", 1995, "Sports", "Konami", 1,1,5,1,8)
        ));
        assertNull(resolver.getMostSellingSportGameInJapanBetween2000And2006());
    }

    @Test
    void getMostProfitableGenre_tie() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1,1,1,1,10),
                new gameEntity(2, "B", "PC", 2021, "RPG", "EA", 1,1,1,1,10)
        ));
        String result = resolver.getMostProfitableGenre();
        assertTrue(result.contains("Action") || result.contains("RPG"));
    }

    @Test
    void getPublisherWithMostRelease_tie() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1,1,1,1,4),
                new gameEntity(2, "B", "PC", 2021, "RPG", "Ubisoft", 1,1,1,1,4)
        ));
        String result = resolver.getPublisherWithMostRelease();
        assertTrue(result.contains("EA") || result.contains("Ubisoft"));
    }

    @Test
    void getPlatformWithMostGenres_tie() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1,1,1,1,4),
                new gameEntity(2, "B", "PS5", 2021, "RPG", "EA", 1,1,1,1,4)
        ));
        String result = resolver.getPlatformWithMostGenres();
        assertTrue(result.contains("PC") || result.contains("PS5"));
    }

    @Test
    void getMostSellingGameInEurope_multipleGames_sameSales() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1,10,1,1,13),
                new gameEntity(2, "B", "PC", 2021, "RPG", "EA", 1,10,1,1,13)
        ));
        game result = resolver.getMostSellingGameInEurope();
        assertTrue(result.getName().equals("A") || result.getName().equals("B"));
    }

    @Test
    void getMostSellingSportGameInJapanBetween2000And2006_nullGenre() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PS2", 2001, null, "Konami", 1,1,5,1,8)
        ));
        assertNull(resolver.getMostSellingSportGameInJapanBetween2000And2006());
    }

    @Test
    void getPublisherWithMostRelease_nullPublisher() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", null, 1,1,1,1,4)
        ));
        String result = resolver.getPublisherWithMostRelease();
        assertTrue(result.contains("Нет данных") || result.contains("null"));
    }

    @Test
    void getYearWithMostRelease_tie() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1,1,1,1,4),
                new gameEntity(2, "B", "PC", 2021, "RPG", "EA", 1,1,1,1,4)
        ));
        String result = resolver.getYearWithMostRelease();
        assertTrue(result.contains("2020") || result.contains("2021"));
    }

    @Test
    void getPlatformWithMostGenres_nullGenre() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, null, "EA", 1,1,1,1,4),
                new gameEntity(2, "B", "PC", 2021, "RPG", "EA", 1,1,1,1,4)
        ));
        String result = resolver.getPlatformWithMostGenres();
        assertTrue(result.contains("PC"));
    }

    @Test
    void getGenrePreferencesByRegion_zeroSales() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 0,0,0,0,0)
        ));
        String result = resolver.getGenrePreferencesByRegion();
        assertTrue(result.contains("Северной Америке"));
        assertTrue(result.contains("Европе"));
        assertTrue(result.contains("Японии"));
    }

    @Test
    void getAverageGlobalSalesByPlatform_differentPlatforms() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 2, 2, 2, 2, 8),
                new gameEntity(2, "B", "PC", 2021, "RPG", "EA", 3, 3, 3, 3, 12),
                new gameEntity(3, "C", "PS5", 2022, "Sports", "Sony", 1, 1, 1, 1, 4)
        ));

        Map<String, Double> result = resolver.getAverageGlobalSalesByPlatform();
        assertEquals(20.0, result.get("PC"));
        assertEquals(4.0, result.get("PS5"));
        assertEquals(2, result.size());
    }

    @Test
    void getMostSellingGameInEurope_multipleGames_differentSales() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1, 5, 1, 1, 8),
                new gameEntity(2, "B", "PC", 2021, "RPG", "EA", 1, 10, 1, 1, 13),
                new gameEntity(3, "C", "PS5", 2022, "Sports", "Sony", 1, 3, 1, 1, 6)
        ));

        game result = resolver.getMostSellingGameInEurope();
        assertEquals("B", result.getName());
        assertEquals(10.0, result.getEUSales());
    }

    @Test
    void getMostSellingGameInEurope_nullEUSales() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1, 0, 1, 1, 3),
                new gameEntity(2, "B", "PC", 2021, "RPG", "EA", 1, 0, 1, 1, 3)
        ));

        game result = resolver.getMostSellingGameInEurope();
        assertNotNull(result);
    }

    @Test
    void getMostSellingSportGameInJapanBetween2000And2006_sameSales() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "Game1", "PS2", 2001, "Sports", "Konami", 1, 1, 8, 1, 11),
                new gameEntity(2, "Game2", "PS2", 2003, "Sports", "Konami", 1, 1, 8, 1, 11)
        ));

        game result = resolver.getMostSellingSportGameInJapanBetween2000And2006();
        assertNotNull(result);
        assertTrue(result.getName().equals("Game1") || result.getName().equals("Game2"));
    }

    @Test
    void getMostSellingSportGameInJapanBetween2000And2006_edgeYears() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "Game2000", "PS2", 2000, "Sports", "Konami", 1, 1, 10, 1, 13),
                new gameEntity(2, "Game2006", "PS2", 2006, "Sports", "Konami", 1, 1, 15, 1, 18)
        ));

        game result = resolver.getMostSellingSportGameInJapanBetween2000And2006();
        assertNotNull(result);
        assertEquals("Game2006", result.getName());
    }

    @Test
    void getGenrePreferencesByRegion_allRegionsZeroSales() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 0, 0, 0, 0, 0),
                new gameEntity(2, "B", "PC", 2021, "RPG", "EA", 0, 0, 0, 0, 0)
        ));

        String result = resolver.getGenrePreferencesByRegion();
        assertTrue(result.contains("Северной Америке"));
        assertTrue(result.contains("Европе"));
        assertTrue(result.contains("Японии"));
    }

    @Test
    void getGenrePreferencesByRegion_nullGenre() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, null, "EA", 10, 5, 3, 2, 20),
                new gameEntity(2, "B", "PC", 2021, "RPG", "EA", 5, 10, 2, 1, 18)
        ));

        String result = resolver.getGenrePreferencesByRegion();
        assertNotNull(result);
    }

    @Test
    void getPlatformWithMostGenres_nullAndEmptyGenres() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, null, "EA", 1, 1, 1, 1, 4),
                new gameEntity(2, "B", "PC", 2021, "", "EA", 1, 1, 1, 1, 4),
                new gameEntity(3, "C", "PS5", 2022, "Action", "EA", 1, 1, 1, 1, 4)
        ));

        String result = resolver.getPlatformWithMostGenres();
        assertTrue(result.contains("PC") || result.contains("PS5"));
    }

    @Test
    void getPlatformWithMostGenres_duplicateGenresSamePlatform() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 2020, "Action", "EA", 1, 1, 1, 1, 4),
                new gameEntity(2, "B", "PC", 2021, "Action", "EA", 1, 1, 1, 1, 4),
                new gameEntity(3, "C", "PS5", 2022, "Sports", "EA", 1, 1, 1, 1, 4),
                new gameEntity(4, "D", "PS5", 2023, "RPG", "EA", 1, 1, 1, 1, 4)
        ));

        String result = resolver.getPlatformWithMostGenres();
        assertEquals("Платформа с наибольшим разнообразием жанров: PS5 (2 жанров)", result.trim());
    }

    @Test
    void getYearWithMostRelease_invalidYearsIgnored() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "A", "PC", 0, "Action", "EA", 1, 1, 1, 1, 4),
                new gameEntity(2, "B", "PC", -1, "RPG", "EA", 1, 1, 1, 1, 4),
                new gameEntity(3, "C", "PC", 2020, "Sports", "EA", 1, 1, 1, 1, 4)
        ));

        String result = resolver.getYearWithMostRelease();
        assertEquals("Год с наибольшим количеством релизов: 2020 (1 игр)", result.trim());
    }

    @Test
    void largeDatasetPerformance() throws SQLException {
        List<gameEntity> largeList = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {
            largeList.add(new gameEntity(
                    i + 1,
                    "Game" + i,
                    "Platform" + (i % 10),
                    2000 + (i % 25),
                    "Genre" + (i % 5),
                    "Publisher" + (i % 8),
                    i % 10,
                    i % 10,
                    i % 10,
                    i % 10,
                    (i % 10) * 4.0
            ));
        }

        Resolver resolver = resolverWith(largeList);
        assertDoesNotThrow(() -> resolver.getMostSellingGameInEurope());
        assertDoesNotThrow(() -> resolver.getAverageGlobalSalesByPlatform());
        assertDoesNotThrow(() -> resolver.getMostProfitableGenre());
        assertDoesNotThrow(() -> resolver.getGenrePreferencesByRegion());
    }

    @Test
    void extremeValuesInSales() throws SQLException {
        Resolver resolver = resolverWith(List.of(
                new gameEntity(1, "MaxSales", "PC", 2020, "Action", "EA",
                        Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE,
                        Double.MAX_VALUE * 4),
                new gameEntity(2, "MinSales", "PC", 2021, "RPG", "EA",
                        0.0, 0.0, 0.0, 0.0, 0.0)
        ));

        game result = resolver.getMostSellingGameInEurope();
        assertEquals("MaxSales", result.getName());

        String profitableGenre = resolver.getMostProfitableGenre();
        assertTrue(profitableGenre.contains("Action"));
    }
}
