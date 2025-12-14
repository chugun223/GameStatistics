package games_project;

import games_project.controller.Controller;
import games_project.infrastructure.parser.DataDownloader;
import games_project.model.repository.DBmanager;
import games_project.model.resolver.Resolver;
import games_project.view_bot.GameBot;
import games_project.view_desktop.MainWindow;
import games_project.view_desktop.chart.chartDrawer;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

public class Main {
    public static void main(String[] args) throws Exception {
        DBmanager db = new DBmanager();
        db.init();
        DataDownloader downloader = new DataDownloader(db);
        Resolver resolver = new Resolver(db);
        chartDrawer drawer = new chartDrawer();
        Controller controller = new Controller(resolver, drawer, db);
        var view = new MainWindow(controller);
        view.setVisible(true);
        TelegramBotsApi api = new TelegramBotsApi(DefaultBotSession.class);
        api.registerBot(new GameBot(controller));
        db.close();
    }
}
