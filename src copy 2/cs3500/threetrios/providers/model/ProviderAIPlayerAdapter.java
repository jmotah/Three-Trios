//package cs3500.threetrios.providers.model;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import cs3500.threetrios.model.GameModel;
//import cs3500.threetrios.model.cards.CardNumbers;
//import cs3500.threetrios.model.cards.PlayingCard;
//import cs3500.threetrios.model.player.AIPlayer;
//import cs3500.threetrios.model.player.Players;
//import cs3500.threetrios.providers.Utilities;
//import cs3500.threetrios.providers.controller.PlayerTurnListener;
//
//public class ProviderAIPlayerAdapter implements PlayerTurnListener {
//
//  private Players player;
//  private final Strategies strategy;
//  private Utilities utilities;
//
//  public ProviderAIPlayerAdapter(PlayerColor color, List<Card> hand, Strategies strategy, ThreeTriosModel model) {
//
//    this.strategy = strategy;
//    this.utilities = new Utilities();
//  }
//
//  @Override
//  public void onTurn(PlayerColor player) {
//    //
//  }
//}