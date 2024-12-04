//package cs3500.threetrios.providers.model;
//
//import java.util.List;
//
//import cs3500.threetrios.model.cards.CardCompass;
//import cs3500.threetrios.model.cards.CardNumbers;
//import cs3500.threetrios.model.cards.PlayingCard;
//import cs3500.threetrios.model.grid.CellType;
//import cs3500.threetrios.model.grid.Grid;
//import cs3500.threetrios.model.grid.GridTile;
//import cs3500.threetrios.model.player.Players;
//import cs3500.threetrios.providers.Utilities;
//
//public class ProviderGridCellAdapter implements Cell {
//
//  private Grid gridTile;
//  private final Utilities utilities;
//
//  public ProviderGridCellAdapter(boolean hole, Card card) {
//    CellType cellType = CellType.CARD_CELL;
//    utilities = new Utilities();
//    Players gridTileOwner = null;
//    PlayingCard gridCard = null;
//
//    if (hole) {
//      cellType = CellType.HOLE;
//    } else if (card != null) {
//      cellType = CellType.PLAYER_CELL;
//    }
//
//    if (card != null && card.getCardOwner() != null) {
//      gridCard = utilities.providerCardToMainPlayerCard(card);
//      gridTileOwner = new cs3500.threetrios.model.player.Player(
//              utilities.providerPlayerColorToMainPlayerColor(card.getCardOwner()),
//              List.of());
//      //*******NO VALID HAND FOR THE SPECIFIED GRID TILE PLAYER!
//      //**SHOULD BE ALRIGHT, BUT MAY CAUSE PROBLEMS IN THE FUTURE
//    }
//
//    this.gridTile = new GridTile(cellType, gridCard, gridTileOwner);
//  }
//
//  @Override
//  public boolean isHole() {
//    if (gridTile.getCellType() == CellType.HOLE) {
//      return true;
//    } else {
//      return false;
//    }
//  }
//
//  @Override
//  public boolean isEmpty() {
//    if (gridTile.getWhichPlayersTile() == null) {
//      return true;
//    } else {
//      return false;
//    }
//  }
//
//  @Override
//  public Card getCard() {
//    return utilities.mainPlayingCardToProviderCard(gridTile.getPlayingCard());
//  }
//
//  @Override
//  public void placeCard(Card card) {
//    PlayingCard playingCard = utilities.providerCardToMainPlayerCard(card);
//    gridTile = new GridTile(CellType.PLAYER_CELL,
//            playingCard,
//            gridTile.getWhichPlayersTile());
//  }
//
//  @Override
//  public Cell copy() {
//    Cell cell = new ProviderGridCellAdapter(
//            this.isHole(),
//            this.getCard());
//    return cell;
//  }
//}