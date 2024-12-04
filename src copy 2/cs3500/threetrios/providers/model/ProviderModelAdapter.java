//package cs3500.threetrios.providers.model;
//
//import java.util.List;
//
//import cs3500.threetrios.model.GameModel;
//import cs3500.threetrios.model.grid.GridTile;
//import cs3500.threetrios.model.player.Players;
//import cs3500.threetrios.providers.Utilities;
//import cs3500.threetrios.providers.controller.ModelStateListener;
//
//public class ProviderModelAdapter implements ThreeTriosModel {
//
//  private final GameModel model;
//
//  private Grid grid;
//
//  private List<Card> deck;
//
//  private final Utilities utilities;
//
//  public ProviderModelAdapter(Grid grid, List<Card> deck) {
//    this.grid = grid;
//    this.deck = deck;
//
//    this.utilities = new Utilities();
//
//    this.model = new GameModel();
//  }
//
//  /**
//   * Places a card at the specified grid location.
//   *
//   * @param index The index of the card in the player's hand.
//   * @param row   The row in the grid where the card will be placed.
//   * @param col   The column in the grid where the card will be placed.
//   * @throws IllegalArgumentException if the index is out of bounds,
//   *                                  or if the specified cell is not a valid move.
//   */
//  @Override
//  public void placeCard(int index, int row, int col) {
//    model.playToGrid(row, col, index);
//    model.battle(row, col);
//  }
//
//  /**
//   * Initializes and starts the game.
//   * This method sets up the game state, including shuffling cards,
//   * and determining the first player.
//   */
//  @Override
//  public void startGame() {
//    model.startGame(
//            utilities.providerGridToMainGrid(grid),
//            utilities.providerListCardToMainListPlayingCard(deck));
//  }
//
//  /**
//   * Ends the current player's turn, passing control to the next player.
//   * This method should update the game state accordingly.
//   */
//  @Override
//  public void endTurn() {
//    model.updatePlayerTurn();
//  }
//
//  /**
//   * Adds a listener to track changes in the model's state.
//   *
//   * @param listener the listener to add.
//   */
//  @Override
//  public void addModelStateListener(ModelStateListener listener) {
//    return;
//  }
//
//  @Override
//  public Grid getGrid() {
//    return utilities.mainGridToProviderGrid(model.getGrid());
//  }
//
//  @Override
//  public Player getCurrentPlayer() {
//    return utilities.mainPlayersToProviderPlayer(model.getCurrentTurnPlayer());
//  }
//
//  @Override
//  public Player getOtherPlayer() {
//    if (this.getCurrentPlayer().getColor() == PlayerColor.RED) {
//      return utilities.mainPlayersToProviderPlayer(
//              model.getPlayerOfColor(
//                      cs3500.threetrios.model.player.PlayerColor.BLUE));
//    } else {
//      return utilities.mainPlayersToProviderPlayer(
//              model.getPlayerOfColor(
//                      cs3500.threetrios.model.player.PlayerColor.RED));
//    }
//  }
//
//  @Override
//  public boolean isGameOver() {
//    return model.isGameOver();
//  }
//
//  @Override
//  public Cell getCell(int row, int col) {
//    return utilities.mainGridTileToProviderCell(model.getGrid()[row][col]);
//  }
//
//  @Override
//  public boolean canCurrentPlayerPlaceCard(int row, int col) {
//    return this.getGrid().getCell(row, col).isHole();
//  }
//
//  @Override
//  public int getPlayerScore(Player player) {
//    Players fixedPlayer = model.getPlayerOfColor(
//            utilities.providerPlayerColorToMainPlayerColor(player.getColor()));
//
//    if (fixedPlayer.getPlayersColor() == model.findWinningPlayer().getPlayersColor()) {
//      return model.findWinningPlayerScore();
//    } else {
//      return model.findWinningPlayerScore() -
//              utilities.findNumberOfPlacedCardsOnGrid(this.getGrid());
//    }
//  }
//
//  @Override
//  public List<Card> getPlayerHand(Player player) {
//    if (player.getColor() == PlayerColor.RED) {
//      return utilities.mainPlayingCardListToProviderCardList(
//              model.getPlayerOfColor(
//                      cs3500.threetrios.model.player.PlayerColor.RED).getHand());
//    } else {
//      return utilities.mainPlayingCardListToProviderCardList(
//              model.getPlayerOfColor(
//                      cs3500.threetrios.model.player.PlayerColor.BLUE).getHand());
//    }
//  }
//
//  @Override
//  public Player determineWinner() {
//    return utilities.mainPlayersToProviderPlayer(model.findWinningPlayer());
//  }
//}