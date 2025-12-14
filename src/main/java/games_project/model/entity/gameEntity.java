package games_project.model.entity;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "games")
public class gameEntity {

    @DatabaseField(generatedId = true)
    private int id;

    @DatabaseField(canBeNull = false)
    private int rank;

    @DatabaseField(canBeNull = false)
    private String name;

    @DatabaseField(canBeNull = false)
    private String platform;

    @DatabaseField
    private int year;

    @DatabaseField
    private String genre;

    @DatabaseField
    private String publisher;

    @DatabaseField
    private double naSales;

    @DatabaseField
    private double euSales;

    @DatabaseField
    private double jpSales;

    @DatabaseField
    private double otherSales;

    @DatabaseField
    private double globalSales;

    public gameEntity() {}

    public gameEntity(int rank, String name, String platform, int year, String genre, String publisher, double naSales, double euSales, double jpSales, double otherSales, double globalSales) {
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
        return String.valueOf(globalSales);
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
}
