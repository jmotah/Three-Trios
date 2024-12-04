package cs3500.threetrios.providers;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.controller.ThreeTriosModelListener;
import cs3500.threetrios.model.GameModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.model.player.AIPlayerListener;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.providers.model.AttackValue;
import cs3500.threetrios.providers.model.Card;
import cs3500.threetrios.providers.model.CardDirection;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Grid;
import cs3500.threetrios.providers.model.Player;
import cs3500.threetrios.providers.model.PlayerColor;
import cs3500.threetrios.providers.model.ProviderCardAdapter;
import cs3500.threetrios.providers.model.ProviderGridAdapter;
import cs3500.threetrios.providers.model.ProviderGridCellAdapter;
import cs3500.threetrios.providers.model.ProviderHumanAdapter;
import cs3500.threetrios.providers.view.ExtendedView;
import cs3500.threetrios.view.ThreeTriosView;
import cs3500.threetrios.view.graphical.GraphicalView;
import cs3500.threetrios.view.graphical.GridLayoutPanel;
import cs3500.threetrios.view.graphical.PlayerCardsLayoutPanel;

public class Utilities {

  public Utilities() {
    //empty as method parameters will take in what is needed
  }

  public PlayingCard providerCardToMainPlayerCard(Card providerCard) {
    PlayingCard card = new PlayingCard(
            providerCard.getCard(),
            providerAttackValueToMainCardNumber(
                    providerCard.getAttackValue(CardDirection.NORTH)),
            providerAttackValueToMainCardNumber(
                    providerCard.getAttackValue(CardDirection.SOUTH)),
            providerAttackValueToMainCardNumber(
                    providerCard.getAttackValue(CardDirection.EAST)),
            providerAttackValueToMainCardNumber(
                    providerCard.getAttackValue(CardDirection.WEST)));
    return card;
    return null;
  }

  public List<PlayingCard> providerListCardToMainListPlayingCard(List<Card> providerCards) {
    List<PlayingCard> fixedCards = new ArrayList<>();

    for (int i = 0; i < providerCards.size(); i++) {
      Card providerCard = providerCards.get(i);
      fixedCards.add(providerCardToMainPlayerCard(providerCard));
    }
    return fixedCards;
  }

  public Card mainPlayingCardToProviderCard(PlayingCard mainCard) {
    if (mainCard == null) {
      return null;
    } //**PROBLEMATIC STATEMENT

    Card card = new ProviderCardAdapter(
            mainCard.getName(),
            mainCardNumberIntToProviderAttackValue(
                    mainCard.getValue(CardCompass.NORTH_VALUE)),
            mainCardNumberIntToProviderAttackValue(
                    mainCard.getValue(CardCompass.SOUTH_VALUE)),
            mainCardNumberIntToProviderAttackValue(
                    mainCard.getValue(CardCompass.EAST_VALUE)),
            mainCardNumberIntToProviderAttackValue(
                    mainCard.getValue(CardCompass.WEST_VALUE)));

    return null;
  }

  public Player mainPlayersToProviderPlayer(Players mainPlayer) {
    return new ProviderHumanAdapter(
            mainColorToProvidedPlayerColor(mainPlayer.getPlayersColor()),
            mainPlayingCardListToProviderCardList(mainPlayer.getHand()));
  }

  public Players providerPlayerToMainPlayers(Player providerPlayer) {
    return new cs3500.threetrios.model.player.Player(
            providerPlayerColorToMainPlayerColor(providerPlayer.getColor()),
            providerCardListToMainPlayingCardList(providerPlayer.getHand()));
  }

  public List<PlayingCard> providerCardListToMainPlayingCardList(List<Card> providerCards) {
    List<PlayingCard> cards = new ArrayList<>();

    for (Card providerCard : providerCards) {
      cards.add(providerCardToMainPlayerCard(providerCard));
    }
    return cards;
  }

  public List<Card> mainPlayingCardListToProviderCardList(List<PlayingCard> mainCards) {
    List<Card> fixedCards = new ArrayList<>();

    for (PlayingCard playingCard : mainCards) {
      fixedCards.add(mainPlayingCardToProviderCard(playingCard));
    }
    return fixedCards;
  }

  public Cell mainGridTileToProviderCell(GridTile mainGridTile) {
    boolean isHole = false;
    Card card = null;

    if (mainGridTile.getCellType() == CellType.HOLE) {
      isHole = true;
    } else if (mainGridTile.getCellType() == CellType.PLAYER_CELL) {
      card = mainPlayingCardToProviderCard(mainGridTile.getPlayingCard());
    }

    return new ProviderGridCellAdapter(isHole, card);
  }

  public int findNumberOfPlacedCardsOnGrid(Grid grid) {
    int placedCardCount = 0;

    for (int row = 0; row < grid.getRows(); row++) {
      for (int column = 0; column < grid.getCols(); column++) {
        if (!grid.getCell(row, column).isHole()) {
          placedCardCount++;
        }
      }
    }
    return placedCardCount;
  }

  public Grid mainGridToProviderGrid(GridTile[][] mainGrid) {
    Cell[][] grid = new Cell[mainGrid.length][mainGrid[0].length];

    for (int row = 0; row < mainGrid.length; row++) {
      for (int column = 0; column < mainGrid[0].length; column++) {
        grid[row][column] = mainGridTileToProviderCell(mainGrid[row][column]);
      }
    }
    return new ProviderGridAdapter(grid);
  }

  public GridTile providerCellToMainGridTile(Cell cell) {
    if (cell == null) {
      throw new IllegalArgumentException("Cell cannot be null!");
    }

    CellType cellType;
    PlayingCard playingCard = null;
    Players gridOwner = null;

    if (cell.isHole()) {
      cellType = CellType.HOLE;
    } else if (cell.getCard() == null) {
      cellType = CellType.CARD_CELL;
    } else {
      cellType = CellType.PLAYER_CELL;
      playingCard = this.providerCardToMainPlayerCard(cell.getCard());

      // Avoid creating an empty player if card owner is null
      if (cell.getCard().getCardOwner() != null) {
        gridOwner = new cs3500.threetrios.model.player.Player(
                providerPlayerColorToMainPlayerColor(cell.getCard().getCardOwner()),
                List.of());
      }
    }

    return new GridTile(cellType, playingCard, gridOwner);
  }

  public GridTile[][] providerGridToMainGrid(Grid providerGrid) {
    GridTile[][] fixedGrid = new GridTile[providerGrid.getRows()][providerGrid.getCols()];

    for (int i = 0; i < providerGrid.getRows(); i++) {
      for (int j = 0; j < providerGrid.getCols(); j++) {
        fixedGrid[i][j] = this.providerCellToMainGridTile(providerGrid.getCell(i, j));
      }
    }
    return fixedGrid;
  }

  public ThreeTriosModel providerModelToMainModel(
          cs3500.threetrios.providers.model.ThreeTriosModel providerModel) {
    return new GameModel() {

      @Override
      public void startGame(GridTile[][] grid, List<PlayingCard> deck) {
        providerModel.startGame();
      }

      //play to grid and battle
      @Override
      public void playToGrid(int row, int col, int cardIndex) {
        providerModel.placeCard(cardIndex, row, col);
      }

      //handled in play to grid
      @Override
      public void battle(int row, int column) {
        return;
      }

      @Override
      public boolean isGameOver() {
        return providerModel.isGameOver();
      }

      @Override
      public Players findWinningPlayer() {
        return providerPlayerToMainPlayers(providerModel.determineWinner());
      }

      @Override
      public int findWinningPlayerScore() {
        return providerModel.getPlayerScore(providerModel.determineWinner());
      }

      @Override
      public void updatePlayerTurn() {
        providerModel.endTurn();
      }

      @Override
      public Players getCurrentTurnPlayer() {
        return providerPlayerToMainPlayers(providerModel.getCurrentPlayer());
      }

      @Override
      public Players getPlayerOfColor(cs3500.threetrios.model.player.PlayerColor color) {
        PlayerColor providerColor = mainColorToProvidedPlayerColor(color);

        if (providerModel.determineWinner().getColor() == providerColor) {
          return providerPlayerToMainPlayers(providerModel.determineWinner());
        }
        return providerPlayerToMainPlayers(providerModel.getOtherPlayer());
      }

      @Override
      public GridTile[][] getGrid() {
        return providerGridToMainGrid(providerModel.getGrid());
      }

      @Override
      public void addViewListener(ThreeTriosModelListener listener) {
        // Adapt the listener logic here, if necessary
      }

      @Override
      public void addAITurnListener(AIPlayerListener listener) {
        // Adapt AI listener logic here, if needed
      }
    };
  }

  public ThreeTriosView providerViewToMainView(ExtendedView providerView) {
    if (providerView == null) {
      throw new IllegalArgumentException("Provider view cannot be null");
    }

    return new ThreeTriosView() {
      @Override
      public void makeVisible() {
        return;
      }

      @Override
      public void refresh() {
        providerView.render();
      }

      @Override
      public PlayerCardsLayoutPanel getRedCardPanel() {
        return null;
      }

      @Override
      public PlayerCardsLayoutPanel getBlueCardPanel() {
        return null;
      }

      @Override
      public GridLayoutPanel getGridPanel() {
        return null;
      }

      @Override
      public void showMessage(String title, String message) {
        providerView.showMessageDialog(message);
      }

      @Override
      public void showErrorMessage(String error) {
        providerView.showMessageDialog(error);
      }
    };
  }
}