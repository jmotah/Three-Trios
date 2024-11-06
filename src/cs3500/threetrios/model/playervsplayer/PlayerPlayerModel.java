package cs3500.threetrios.model.playervsplayer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.cards.CardCompass;
import cs3500.threetrios.controller.filereader.CardReader;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.GamePhase;
import cs3500.threetrios.model.GameState;
import cs3500.threetrios.controller.filereader.GridReader;
import cs3500.threetrios.grid.GridTile;
import cs3500.threetrios.player.HumanPlayer;
import cs3500.threetrios.player.Player;
import cs3500.threetrios.player.PlayerColor;
import cs3500.threetrios.cards.PlayingCard;
import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Represents the model for the ThreeTrios card game. Manages starting the game,
 * as well as initializing the hands and the grid.
 */
public class PlayerPlayerModel implements ThreeTriosModel {
  HumanPlayer playerRed;
  HumanPlayer playerBlue;

  HumanPlayer currentPlayersTurn;
  GameState currentGameState = GameState.NOT_STARTED;
  GamePhase currentGamePhase;

  /**
   * The grid is 0-index based. As a result, the first row and first column of the grid is at index
   * or row 0 and column 0 in the grid.
   * INVARIANCE: The grid must contain an odd number of grid tiles with the cell type of CELL_CARD
   */
  GridTile[][] grid;

  int rows;
  int columns;
  int handSize;

  /**
   * A deck is a list of cards received from the card configuration class used to distribute cards
   * to the player's hands upon initialization. After this, the deck stays constant and no cards
   * go into it or back out.
   **/
  List<PlayingCard> deck;

  /**
   * A PlayerPlayerModel constructor.
   */
  public PlayerPlayerModel() {
    //Nothing needs initialized here as everything is initialized in the startGame method.
  }

  /**
   * Starts the game with a given set of configurations from a card and grid configuration file.
   *
   * @param cardConfig the desired card configuration file
   * @param gridConfig the desired grid configuration file
   */
  @Override
  public void startGame(File cardConfig, File gridConfig) {
    if (cardConfig == null || gridConfig == null) {
      throw new IllegalArgumentException("Card config and grid config files cannot be null!");
    }

    try {
      CardReader cardReader = new CardReader(cardConfig);
      GridReader gridReader = new GridReader(gridConfig);

      this.deck = cardReader.readConfiguration();
      this.grid = gridReader.readConfiguration();

      this.rows = grid.length;
      this.columns = grid[0].length;

      int gridCardCellCount = getNumCardCells();

      int minNumOfCardsNeededToStart = gridCardCellCount + 1;
      handSize = minNumOfCardsNeededToStart / 2;

      if (currentGameState == GameState.IN_PROGRESS) {
        throw new IllegalStateException("The game has already been started!");
      } else if (currentGameState == GameState.GAME_OVER) {
        throw new IllegalStateException("The game has already ended!");
      } else if (deck == null) {
        throw new IllegalArgumentException("The deck cannot be null!");
      } else if (grid == null) {
        throw new IllegalArgumentException("The grid cannot be null!");
      } else if (gridContainsNullField(grid)) {
        throw new IllegalArgumentException("The grid contains a null field!");
      } else if (rows <= 0 || columns <= 0) {
        throw new IllegalArgumentException(
                "The number of rows and columns must be greater than zero!");
      } else if (deck.size() < minNumOfCardsNeededToStart) {
        throw new IllegalArgumentException("The size of the deck must be greater than double the " +
                "number of cells on the grid plus one!");
      }

      ensureInvarianceAndSetStates();
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("The rows and columns but be valid integers!");
    }
  }

  /**
   * Plays a desired card from the current player's hand to a desired cell in the grid.
   *
   * @param row           the desired row to play to; number is 0-index based
   * @param column        the desired column to play to; number is 0-index based
   * @param cardIdxInHand the desired card index to play from the player's hand; number is 0-index
   *                      based
   */
  @Override
  public void playToGrid(int row, int column, int cardIdxInHand) {
    if (currentGameState == GameState.NOT_STARTED ||
            currentGameState == GameState.GAME_OVER) {
      throw new IllegalStateException("The game is not yet started or the game is already over!");
    } else if (row < 0 || column < 0 || cardIdxInHand < 0) {
      throw new IllegalArgumentException(
              "The row, column, and card index must be greater than or equal to 0!");
    } else if (row >= grid.length || column >= grid[0].length) {
      throw new IllegalArgumentException("The row and column index must be less than the " +
              "row size and column size of the grid, respectively.");
    } else if (cardIdxInHand >= getCurrentTurnPlayer().getHand().size()) {
      throw new IllegalArgumentException("The card index must be less than the hand size!");
    } else if (grid[row][column].getPlayingCard() != null &&
            grid[row][column].getWhichPlayersTile() != null) {
      throw new IllegalArgumentException("Cannot play to an already occupied cell!");
    } else if (currentGamePhase != GamePhase.PLACING) {
      throw new IllegalStateException("The game is not in the placing phase!");
    } else if (grid[row][column].getCellType() == CellType.HOLE ||
            grid[row][column].getCellType() == CellType.PLAYER_CELL) {
      throw new IllegalStateException("Illegal move! Either there already exists a card here " +
              "or this is a hole!");
    }

    grid[row][column] = new GridTile(CellType.PLAYER_CELL,
            removeCardFromCurrentPlayer(cardIdxInHand),
            currentPlayersTurn);
    currentGamePhase = GamePhase.BATTLE;
  }

  /**
   * Battles the nearby cards at the provided row and column index. If the provided card wins the
   * battle, updates the card battled against to become the current player's card.
   *
   * @param row    the desired row index to initiate the battle with; number is 0-index based
   * @param column the desired column index to initiate the battle with; number is 0-index based
   */
  @Override
  public void battle(int row, int column) {
    if (currentGameState == GameState.NOT_STARTED ||
            currentGameState == GameState.GAME_OVER) {
      throw new IllegalStateException("The game is not yet started or the game is already over!");
    } else if (row < 0 || column < 0) {
      throw new IllegalArgumentException(
              "The row and column index must be greater than or equal to 0!");
    } else if (row >= grid.length || column >= grid[0].length) {
      throw new IllegalArgumentException("The row and column index must be less than the " +
              "row size and column size of the grid, respectively.");
    } else if (currentGamePhase != GamePhase.BATTLE) {
      throw new IllegalStateException("The game is not in the battle phase!");
    } else if (grid[row][column].getPlayingCard() == null ||
            grid[row][column].getWhichPlayersTile() == null) {
      throw new IllegalArgumentException("The given row and column lead to an un-played cell!");
    }

    battleAllDirections(row, column);
    currentGamePhase = GamePhase.PLACING;
    updatePlayerTurn();
    checkAndSetForGameOver();
  }

  /**
   * Starts the battle in all four compass directions including north, south, east, and west from
   * the specified tile in the grid.
   *
   * @param row    the row index where the battle starts from; number is 0-index based
   * @param column the column index where the battle starts from; number is 0-index based
   */
  private void battleAllDirections(int row, int column) {
    if (currentGameState == GameState.NOT_STARTED || currentGameState == GameState.GAME_OVER) {
      throw new IllegalStateException("The game is not yet started or the game is already over!");
    } else if (row < 0 || column < 0) {
      throw new IllegalArgumentException(
              "The row and column index must be greater than or equal to 0!");
    } else if (row >= grid.length || column >= grid[0].length) {
      throw new IllegalArgumentException("The row and column index must be less than the " +
              "row size and column size of the grid, respectively.");
    } else if (grid[row][column].getPlayingCard() == null) {
      throw new IllegalArgumentException("The given row and column lead to an un-playable cell!");
    }

    GridTile placedTile = grid[row][column];

    battleSpecificDirection(placedTile, row - 1, column, CardCompass.NORTH_VALUE);
    battleSpecificDirection(placedTile, row + 1, column, CardCompass.SOUTH_VALUE);
    battleSpecificDirection(placedTile, row, column + 1, CardCompass.EAST_VALUE);
    battleSpecificDirection(placedTile, row, column - 1, CardCompass.WEST_VALUE);
  }

  /**
   * Executes a battle in a specific direction from the given GridTile object. Compares the
   * GridTile object with the object in the specified row and column.
   *
   * @param current          the object to execute the battle with
   * @param row              the row of the object to compare with the current GridTile object with;
   *                         number is 0-index based
   * @param column           the column of the object to compare with the current GridTile object
   *                         with; number is 0-index based
   * @param compareDirection the direction of comparison
   */
  private void battleSpecificDirection(GridTile current, int row, int column,
                                       CardCompass compareDirection) {
    if (currentGameState == GameState.NOT_STARTED ||
            currentGameState == GameState.GAME_OVER) {
      throw new IllegalStateException("The game is not yet started or the game is already over!");
    } else if (current == null) {
      throw new IllegalArgumentException("The provided GridTile object is null!");
    } else if (compareDirection == null) {
      throw new IllegalArgumentException("The provided CardCompass object is null!");
    }

    if (row >= 0 && row < grid.length &&
            column >= 0 && column < grid[0].length &&
            grid[row][column].getCellType() == CellType.PLAYER_CELL) {
      GridTile compareToTile = grid[row][column];

      if (current.compareTo(compareToTile, compareDirection) == current &&
              compareToTile.getWhichPlayersTile() != currentPlayersTurn) {
        grid[row][column] = new GridTile(compareToTile.getCellType(),
                compareToTile.getPlayingCard(),
                currentPlayersTurn);
        battleAllDirections(row, column);
      }
    }
  }

  /**
   * Checks if the game is over or not. The game is over if every tile in the grid has a valid
   * playing card placed in it.
   *
   * @return true if the game is over, false otherwise
   */
  @Override
  public boolean isGameOver() {
    if (currentGameState == GameState.NOT_STARTED) {
      throw new IllegalStateException("The game is not yet started!");
    }

    for (GridTile[] gridTile : grid) {
      for (int column = 0; column < grid[0].length; column++) {
        if (gridTile[column].getCellType() == CellType.CARD_CELL) {
          return false;
        }
      }
    }
    return true;
  }

  /**
   * Finds the current winning player in the game. The winning player is chosen by which player
   * has the greatest number of cards in that player's name. This includes cards on the grid, and
   * cards in the player's hand. If there is a tie, null is returned.
   *
   * @return the winning player as a Players object
   */
  @Override
  public Player findWinningPlayer() {
    if (currentGameState == GameState.NOT_STARTED) {
      throw new IllegalStateException("The game is not yet started!");
    }

    int redCardCount = playerRed.getHand().size();
    int blueCardCount = playerBlue.getHand().size();

    for (GridTile[] gridTile : grid) {
      for (int column = 0; column < grid[0].length; column++) {
        if (gridTile[column].getWhichPlayersTile() == playerRed) {
          redCardCount++;
        } else if (gridTile[column].getWhichPlayersTile() == playerBlue) {
          blueCardCount++;
        }
      }
    }

    if (redCardCount > blueCardCount) {
      return playerRed;
    } else if (blueCardCount > redCardCount) {
      return playerBlue;
    } else {
      return null; //Default cause when players red and blue tie
    }
  }

  /**
   * Updates the current player's turn from the given player to the next player. As an example,
   * if the given player is RED, the current player's turn will update to BLUE, and vice versa.
   */
  @Override
  public void updatePlayerTurn() {
    throwErrorIfGameNotStartedOrGameOver("Cannot update the player's turn. The game is not yet" +
            "started or the game is over!");

    if (getCurrentTurnPlayer() == playerRed) {
      currentPlayersTurn = playerBlue;
    } else if (getCurrentTurnPlayer() == playerBlue) {
      currentPlayersTurn = playerRed;
    }
  }

  /**
   * Gets the current player's turn.
   *
   * @return the current player's turn
   */
  @Override
  public Player getCurrentTurnPlayer() {
    throwErrorIfGameNotStartedOrGameOver("Cannot get the current player's turn. The game is not" +
            "yet started or the game is over!");

    return currentPlayersTurn;
  }

  /**
   * Given a player color, yields the respective Player object.
   *
   * @param color the player color of the player to return
   * @return the found Player object
   */
  @Override
  public Player getPlayerOfColor(PlayerColor color) {
    if (color == null) {
      throw new IllegalArgumentException("The provided PlayerColor object cannot be null!");
    }

    if (color == PlayerColor.RED) {
      return playerRed;
    } else if (color == PlayerColor.BLUE) {
      return playerBlue;
    }
    throw new IllegalArgumentException("The provided PlayerColor object is not valid!");
  }

  /**
   * Gets the current game grid.
   *
   * @return a copy of the game grid
   */
  @Override
  public GridTile[][] getGrid() {
    GridTile[][] gridCopy = new GridTile[grid.length][grid[0].length];

    for (int i = 0; i < grid.length; i++) {
      System.arraycopy(grid[i], 0, gridCopy[i], 0, grid[i].length);
    }
    return gridCopy;
  }

  /**
   * Initializes the players hands dealing one card for the first player, and one card for the next
   * player. This goes back and forth until player's hand sizes are full. This method should
   * primarily be used when starting the game.
   */
  private List<PlayingCard>[] initializePlayerHands() {
    throwErrorIfGameNotStartedOrGameOver("Cannot initialize the players hands. The game is not " +
            "started or the game is over!");

    if (deck.size() < 2 * handSize) {
      throw new IllegalArgumentException("The deck does not contain enough cards to fully " +
              "initialize both player hands!");
    }

    @SuppressWarnings("unchecked")
    List<PlayingCard>[] hands = new ArrayList[2];
    hands[0] = new ArrayList<>();
    hands[1] = new ArrayList<>();

    for (int i = 0; i < handSize; i++) {
      hands[0].add(deck.remove(0));
      hands[1].add(deck.remove(0));
    }
    return hands;
  }

  /**
   * Removes a specific card from a specific player's hand.
   *
   * @param cardIdxInHand the specific index of the card in the players hand to remove;
   *                      number is 0-index based
   * @return the removed PlayingCard object
   */
  private PlayingCard removeCardFromCurrentPlayer(int cardIdxInHand) {
    throwErrorIfGameNotStartedOrGameOver("Cannot remove a card from the players hand. The game" +
            "is not yet started or the game is over!");
    if (cardIdxInHand < 0) {
      throw new IllegalArgumentException("The provided card index must be greater than 0!");
    } else if (cardIdxInHand >= getCurrentTurnPlayer().getHand().size()) {
      throw new IllegalArgumentException("The provided card index must be less than the size of " +
              "the player's hand!");
    }

    return getCurrentTurnPlayer().removeCardAtIndex(cardIdxInHand);
  }

  /**
   * Checks if the game is not started or if the game is over and throws an IllegalStateException
   * if true.
   *
   * @param message the message the error should send
   */
  private void throwErrorIfGameNotStartedOrGameOver(String message) {
    if (currentGameState == GameState.NOT_STARTED ||
            currentGameState == GameState.GAME_OVER) {
      throw new IllegalStateException(message);
    }
  }

  /**
   * Checks if the grid contains a null field.
   *
   * @param grid the given grid to display
   * @return true if the grid contains a null field, false otherwise
   */
  private boolean gridContainsNullField(GridTile[][] grid) {
    if (grid == null) {
      throw new IllegalArgumentException("The provided grid is null!");
    }

    for (GridTile[] gridTiles : grid) {
      for (GridTile gridTile : gridTiles) {
        if (gridTile == null) {
          return true;
        }
      }
    }
    return false;
  }

  /**
   * Checks to see if the game is over or not. If the game is over, sets the current game state to
   * GAME_OVER.
   */
  private void checkAndSetForGameOver() {
    if (isGameOver()) {
      currentGameState = GameState.GAME_OVER;
    }
  }

  /**
   * Ensures the invariance for the grid variable. The invariance we are ensuring is regarding
   * ensuring the number of cell types of CARD_CELL on the grid being odd.
   */
  private void ensureGridInvariance() {
    int cellCardCount = 0;

    for (GridTile[] gridTiles : grid) {
      for (GridTile gridTile : gridTiles) {
        if (gridTile.getCellType() == CellType.CARD_CELL) {
          cellCardCount++;
        }
      }
    }

    if (cellCardCount % 2 == 0) {
      throw new IllegalArgumentException("The number of cell cards on the grid must be odd!");
    }
  }

  /**
   * Counts the number of card cells within the grid.
   *
   * @return an integer of the number of card cells within the grid
   */
  private int getNumCardCells() {
    int count = 0;
    for (GridTile[] gridTiles : grid) {
      for (int column = 0; column < grid[0].length; column++) {
        if (gridTiles[column].getCellType() == CellType.CARD_CELL) {
          count++;
        }
      }
    }
    return count;
  }

  /**
   * Ensures the grid invariance is withheld with the current grid layout. In addition, also sets
   * game states and manages player hand initialization.
   */
  private void ensureInvarianceAndSetStates() {
    ensureGridInvariance();

    currentGameState = GameState.IN_PROGRESS;

    List<PlayingCard>[] hands = this.initializePlayerHands();

    playerRed = new HumanPlayer(PlayerColor.RED, hands[0]);
    playerBlue = new HumanPlayer(PlayerColor.BLUE, hands[1]);

    currentPlayersTurn = playerRed;
    currentGamePhase = GamePhase.PLACING;
  }
}