package games_project.model.Game;

import java.util.Objects;

public class game{
    public int rank;
    public String name;
    public String platform;
    public int year;
    public String genre;
    public String publisher;
    public double naSales;
    public double euSales;
    public double jpSales;
    public double otherSales;
    public double globalSales;

    public game(int rank, String name, String platform, int year, String genre, String publisher, double naSales, double euSales, double jpSales, double otherSales, double globalSales) {
        this.rank = rank;
        this.name = name;
        this.platform = platform;
        this.year = year;
        this.genre = genre;
        this.publisher = publisher;
        this.naSales = naSales;
        this.euSales = euSales;
        this.jpSales = jpSales;
        this.otherSales = otherSales;
        this.globalSales = globalSales;
    }
    @Override
    public String toString() {
        return Double.toString(globalSales);
    }
    public double getEUSales() {
        return this.euSales;
    }
    public double getJPSales() {
        return this.jpSales;
    }
    public double getNASales() {
        return this.naSales;
    }
    public double getGlobalSales() {
        return this.globalSales;
    }
    public double getOtherSales() {
        return this.otherSales;
    }
    public int getYear(){
        return this.year;
    }
    public String getName(){
        return this.name;
    }
    public String getPlatform(){
        return this.platform;
    }
    public String getGenre(){
        return this.genre;
    }
    public int getRank(){
        return this.rank;
    }
    public String getPublisher(){
        return this.publisher;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
        {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        game game = (game) o;
        return rank == game.rank &&
                year == game.year &&
                Double.compare(game.naSales, naSales) == 0 &&
                Double.compare(game.euSales, euSales) == 0 &&
                Double.compare(game.jpSales, jpSales) == 0 &&
                Double.compare(game.otherSales, otherSales) == 0 &&
                Double.compare(game.globalSales, globalSales) == 0 &&
                Objects.equals(name, game.name) &&
                Objects.equals(platform, game.platform) &&
                Objects.equals(genre, game.genre) &&
                Objects.equals(publisher, game.publisher);
    }

    @Override
    public int hashCode() {
        return Objects.hash(rank, name, platform, year, genre, publisher, naSales, euSales, jpSales, otherSales, globalSales);
    }
}
