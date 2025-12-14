package games_project.model.resolver;

import games_project.model.Game.game;

import java.util.Map;

public interface IResolver {
    Map<String, Double> getAverageGlobalSalesByPlatform();

    game getMostSellingGameInEurope();

    game getMostSellingSportGameInJapanBetween2000And2006();
}