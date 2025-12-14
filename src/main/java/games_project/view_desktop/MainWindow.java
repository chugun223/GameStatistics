package games_project.view_desktop;
//getYearWithMostReleases getPublisherWithMostReleases getPlatformWithMostGenres
import games_project.controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class MainWindow extends JFrame {

    private final Controller controller;

    public MainWindow(Controller controller) {
        this.controller = controller;

        setTitle("Games");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        setPreferredSize(new Dimension(600, 400));

        JButton mostSellingEuropeGameBtn = new JButton("Самая продаваемая игра в Европе");
        JButton bestSportsGameJapan2000_2006Btn = new JButton("Самая продаваемая игра в Японии 2000–2006");
        JButton chartBtn = new JButton("Показать график продаж");
        JButton mostProfitableGenreBtn = new JButton("Самый продаваемый жанр игр");
        JButton genrePreferencesByRegionBtn = new JButton("Предпочтения по регионам");
        JButton getYearWithMostReleasesBtn = new JButton("Показать год с самым большим количеством релизов");
        JButton getPublisherWithMostReleasesBtn = new JButton("Издатель с самым большим количеством выпущенных игр");
        JButton getPlatformWithMostGenresBtn = new JButton("Платформа с самым богатым разнообразием жанров игр");
        JButton clearDBBtn = new JButton("Очистить БД");

        JTextArea output = new JTextArea();
        output.setEditable(false);
        output.setLineWrap(true);
        output.setWrapStyleWord(true);

        JPanel buttons = new JPanel();
        buttons.setLayout(new BoxLayout(buttons, BoxLayout.Y_AXIS));

        buttons.add(mostSellingEuropeGameBtn);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(bestSportsGameJapan2000_2006Btn);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(chartBtn);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(mostProfitableGenreBtn);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(genrePreferencesByRegionBtn);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(getYearWithMostReleasesBtn);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(getPublisherWithMostReleasesBtn);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(getPlatformWithMostGenresBtn);
        buttons.add(Box.createVerticalStrut(10));
        buttons.add(clearDBBtn);

        mostSellingEuropeGameBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        bestSportsGameJapan2000_2006Btn.setAlignmentX(Component.CENTER_ALIGNMENT);
        chartBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        mostProfitableGenreBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        genrePreferencesByRegionBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        getYearWithMostReleasesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        getPublisherWithMostReleasesBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        getPlatformWithMostGenresBtn.setAlignmentX(Component.CENTER_ALIGNMENT);
        clearDBBtn.setAlignmentX(Component.CENTER_ALIGNMENT);

        mostSellingEuropeGameBtn.addActionListener(e ->
                output.setText("Самая продаваемая в Европе:\n" +
                        controller.getMostSellingInEurope()));

        bestSportsGameJapan2000_2006Btn.addActionListener(e ->
                output.setText("Самая продаваемая игра в Японии с 2000 по 2006:\n" +
                        controller.getBestSportsJapan2000_2006()));

        chartBtn.addActionListener(e -> controller.showSalesChart());

        mostProfitableGenreBtn.addActionListener(e ->
                output.setText("Самый продаваемый жанр игр:\n" + controller.getMostProfitableGenre()));

        genrePreferencesByRegionBtn.addActionListener(e -> output.setText(controller.getGenrePreferencesByRegion()));

        getYearWithMostReleasesBtn.addActionListener(e -> output.setText(controller.getYearWithMostReleases()));

        getPublisherWithMostReleasesBtn.addActionListener(e -> output.setText(controller.getPublisherWithMostReleases()));

        getPlatformWithMostGenresBtn.addActionListener(e -> output.setText(controller.getPlatformWithMostGenres()));

        clearDBBtn.addActionListener(e -> {
            try {
                controller.rewriteDB();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
            output.setText("База данных перезаписана");
        });



        add(buttons, BorderLayout.WEST);
        add(new JScrollPane(output), BorderLayout.CENTER);

        pack();
        setVisible(true);
    }
}
