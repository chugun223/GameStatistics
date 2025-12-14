package games_project.service;

import games_project.model.Game.game;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSV_parser {
    public static String[] parseCsvLine(String line) {
        List<String> fields = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (c == '"') {
                if (inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"') {
                    sb.append('"');
                    i++;
                } else {
                    inQuotes = !inQuotes;
                }
            } else if (c == ',' && !inQuotes) {
                fields.add(sb.toString().trim());
                sb.setLength(0);
            } else {
                sb.append(c);
            }
        }
        fields.add(sb.toString().trim());
        return fields.toArray(new String[0]);
    }

    private static int parseIntSafe(String s) {
        if (s == null || s.isEmpty() || s.equalsIgnoreCase("N/A")) return 0;
        try {
            return Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return 0;
        }
    }

    private static double parseDoubleSafe(String s) {
        if (s == null || s.isEmpty() || s.equalsIgnoreCase("N/A")) return 0.0;
        try {
            return Double.parseDouble(s);
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    public List<game> parseGames() throws IOException {
        InputStream stream = getClass().getClassLoader().getResourceAsStream("games.csv");
        if (stream == null)
            throw new FileNotFoundException("games.csv не найден");

        List<game> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {
            String line;
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue;
                String[] arr = parseCsvLine(line);
                if (arr.length != 11) {
                    System.out.println("Некорректная строка: " + line);
                    continue;
                }
                game g = new game(
                        parseIntSafe(arr[0]),
                        arr[1],
                        arr[2],
                        parseIntSafe(arr[3]),
                        arr[4],
                        arr[5],
                        parseDoubleSafe(arr[6]),
                        parseDoubleSafe(arr[7]),
                        parseDoubleSafe(arr[8]),
                        parseDoubleSafe(arr[9]),
                        parseDoubleSafe(arr[10])
                );
                result.add(g);
            }
        }
        return result;
    }
}
