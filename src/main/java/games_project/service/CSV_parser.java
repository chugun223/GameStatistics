package games_project.service;

import com.opencsv.exceptions.CsvValidationException;
import games_project.model.Game.game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVReader;

public class CSV_parser {

    private static int parseIntSafe(String s) {
        if (s == null || s.isBlank() || s.equalsIgnoreCase("N/A")) return 0;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static double parseDoubleSafe(String s) {
        if (s == null || s.isBlank() || s.equalsIgnoreCase("N/A")) return 0.0;
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public List<game> parseGames() throws IOException, CsvValidationException {
        InputStream stream = getClass()
                .getClassLoader()
                .getResourceAsStream("games.csv");

        if (stream == null)
            throw new FileNotFoundException("games.csv не найден");

        List<game> result = new ArrayList<>();

        try (CSVReader reader = new CSVReader(new InputStreamReader(stream))) {

            String[] row;
            reader.readNext(); // пропускаем заголовок

            while ((row = reader.readNext()) != null) {
                if (row.length != 11) continue;

                game g = new game(
                        parseIntSafe(row[0]),
                        row[1],
                        row[2],
                        parseIntSafe(row[3]),
                        row[4],
                        row[5],
                        parseDoubleSafe(row[6]),
                        parseDoubleSafe(row[7]),
                        parseDoubleSafe(row[8]),
                        parseDoubleSafe(row[9]),
                        parseDoubleSafe(row[10])
                );

                result.add(g);
            }
        }

        return result;
    }
}