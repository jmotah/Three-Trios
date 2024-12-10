package cs3500.threetrios.view.textual;

import java.util.List;

import cs3500.threetrios.controller.HintsToggleListener;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.view.ThreeTriosView;
import cs3500.threetrios.view.graphical.GridLayoutPanel;
import cs3500.threetrios.view.graphical.PlayerCardsLayoutPanel;

/**
 * A textual view for the Three Trios game. Is in charge of displaying the current player's turn,
 * game grid, and the current player's hand in a text format. The textual view is outputted to the
 * console.
 */
public class TextualView implements ThreeTriosView {

  private final ThreeTriosModel model;

  /**
   * A TextualView constructor.
   *
   * @param model the model object managing our primary game functions
   */
  public TextualView(ThreeTriosModel model) {
    if (model == null) {
      throw new IllegalArgumentException("The model cannot be null!");
    }
    this.model = model;
  }

  /**
   * Calls the refresh method to display the game state. The purpose of this method is primarily
   * for the graphical view.
   */
  @Override
  public void makeVisible() {
    this.refresh();
  }


  /**
   * Displays the refreshed game state in a textual manner.
   */
  @Override
  public void refresh() {
    if (!model.isGameOver()) {
      System.out.print(this);
    } else {
      System.out.println(endGameMessage());
    }
  }

  /**
   * Would return the entire red card panel. Since this is unnecessary in a textual view, returns
   * null.
   *
   * @return null
   */
  @Override
  public PlayerCardsLayoutPanel getRedCardPanel() {
    return null;
  }

  /**
   * Would return the entire blue card panel. Since this is unnecessary in a textual view, returns
   * null.
   *
   * @return null
   */
  @Override
  public PlayerCardsLayoutPanel getBlueCardPanel() {
    return null;
  }

  /**
   * Would return the entire grid panel. Since this is unnecessary in a textual view, returns null.
   *
   * @return null
   */
  @Override
  public GridLayoutPanel getGridPanel() {
    return null;
  }

  /**
   * Transmit a message to the view.
   * Since this is unnecessary in a textual view, just returns.
   *
   * @param title   the title of the message window
   * @param message the message to transmit
   */
  @Override
  public void showMessage(String title, String message) {
    return;
  }

  /**
   * Transmit an error message to the view, in case the command could not be processed correctly.
   * Since this is unnecessary in a textual view, just returns.
   *
   * @param error just returns
   */
  @Override
  public void showErrorMessage(String error) {
    return;
  }

  @Override
  public void setHintsToggleListener(HintsToggleListener listener) {
    return;
  }

  @Override
  public void setCurrentlyClickedCardIndex(int currentlyClickedCardIndex) {
    return;
  }

  /**
   * The game states converted to a textual view.
   *
   * @return a string of the game state converted to a textual view
   */
  @Override
  public String toString() {
    return "Player: " + this.model.getCurrentTurnPlayer().getPlayersColor() + "\n" +
            textualGrid() +
            "Hand:\n" + textualHand() + "\n";
  }

  /**
   * Converts the game's grid to a textual view.
   *
   * @return the game's grid to a string
   */
  private String textualGrid() {
    StringBuilder returnString = new StringBuilder();

    Grid[][] grid = this.model.getGrid();
    for (Grid[] gridTiles : grid) {
      for (Grid tile : gridTiles) {
        if (tile.getCellType() == CellType.PLAYER_CELL) {
          if (tile.getWhichPlayersTile().getPlayersColor() == PlayerColor.RED) {
            returnString.append("R");
          } else if (tile.getWhichPlayersTile().getPlayersColor() == PlayerColor.BLUE) {
            returnString.append("B");
          }
        } else if (tile.getCellType() == CellType.CARD_CELL) {
          returnString.append("_");
        } else if (tile.getCellType() == CellType.HOLE) {
          returnString.append("X");
        }
      }
      returnString.append("\n");
    }
    return returnString.toString();
  }

  /**
   * Converts the current player turn's hand to a textual view.
   *
   * @return the current player turn's hand to a string
   */
  private String textualHand() {
    StringBuilder returnString = new StringBuilder();
    PlayerColor currentPlayerColor = this.model.getCurrentTurnPlayer().getPlayersColor();
    List<?> hand;

    if (currentPlayerColor == PlayerColor.RED) {
      hand = model.getPlayerOfColor(PlayerColor.RED).getHand();
    } else {
      hand = model.getPlayerOfColor(PlayerColor.BLUE).getHand();
    }

    for (Object o : hand) {
      returnString.append(o.toString()).append("\n");
    }

    return returnString.toString();
  }

  /**
   * The end game state converted to a textual view.
   *
   * @return a string of the end game state converted to a textual view
   */
  private String endGameMessage() {
    return "Winning Player: " + findWinningPlayerWithTie() + "\n" +
            textualGrid() +
            "GAME OVER!";
  }

  private String findWinningPlayerWithTie() {
    if (model.findWinningPlayer() == null) {
      return "Red and Blue";
    } else {
      return "" + model.findWinningPlayer().getPlayersColor();
    }
  }
}