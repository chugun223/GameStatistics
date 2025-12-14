package games_project.view_bot;

import games_project.controller.Controller;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.File;

public class GameBot extends TelegramLongPollingBot {

    private final Controller controller;

    public GameBot(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (!update.hasMessage() || !update.getMessage().hasText())
            return;

        String text = update.getMessage().getText();
        long chatId = update.getMessage().getChatId();

        switch (text) {
            case "/europe":
                sendText(chatId, "Самая продаваемая в Европе:\n" + controller.getMostSellingInEurope());
                break;
           case "/japan":
                sendText(chatId, "Самая продаваемая игра в Японии с 2000 по 2006:\n" + controller.getBestSportsJapan2000_2006());
                break;
            case "/genre":
                sendText(chatId, "Самый продаваемый жанр игр:\n" + controller.getMostProfitableGenre());
                break;
            case "/regions":
                sendText(chatId, controller.getGenrePreferencesByRegion());
                break;
            case "/chart":
                sendChart(chatId);
                break;
            case "/rewrite":
                try {
                    controller.rewriteDB();
                    sendText(chatId, "БД перезаписана");
                } catch (Exception e) {
                    sendText(chatId, "Ошибка: " + e.getMessage());
                }
                break;
            case "/publisher":
                sendText(chatId, controller.getPublisherWithMostReleases());
                break;
            case "/year":
                sendText(chatId, controller.getYearWithMostReleases());
                break;
            case "/platform":
                sendText(chatId, controller.getPlatformWithMostGenres());
                break;
            default:
                sendText(chatId, "Команды:\n" + "/europe\n/japan\n/genre\n/regions\n/chart\n/platform\n/publisher\n/year\n/rewrite");
        }
    }
    private void sendText(long chatId, String text) {
        SendMessage msg = new SendMessage(String.valueOf(chatId), text);
        try {
            execute(msg);
        } catch (Exception ignored) {}
    }

    private void sendChart(long chatId) {
        try {
            String path = "chart.png";
            controller.saveChartAsFile(path);

            SendPhoto photo = new SendPhoto();
            photo.setChatId(chatId);
            photo.setPhoto(new InputFile(new File(path)));

            execute(photo);
        } catch (Exception e) {
            sendText(chatId, "Не удалось отправить график");
        }
    }

    @Override
    public String getBotUsername() {
        return "gameStatsbot";
    }

    @Override
    public String getBotToken() {
        return "8492895129:AAGM5V4keMaBNhgdN4--GA29E8NzZxduRyA";
    }
}
