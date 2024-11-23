package cs3500.threetrios.controller;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.view.ThreeTriosView;
import cs3500.threetrios.view.graphical.CardPanel;
import cs3500.threetrios.view.graphical.GridPanel;

/**
 * The controller class for a Three Trios game. Analyzes and reads user interactions and generates
 * an output accordingly to the input. Manages communications between the model, view, and inputs.
 */
public class ThreeTriosController implements Features, ThreeTriosModelListener {

  private final ThreeTriosModel model;
  private final Players player;
  private final ThreeTriosView view;
  private int selectedCardIdx = -1;

  private CardPanel currentlyClickedCardPanel;

  /**
   * ThreeTriosController class.
   *
   * @param model  the model to gain game data from
   * @param player the player object associated with this controller
   * @param view   the view object associated with this controller
   */
  public ThreeTriosController(ThreeTriosModel model, Players player, ThreeTriosView view) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    } else if (player == null) {
      throw new IllegalArgumentException("Player cannot be null");
    } else if (view == null) {
      throw new IllegalArgumentException("View cannot be null");
    }

    this.model = model;
    this.player = player;
    this.view = view;

    this.model.addViewListener(this);
    this.player.addActionListener(this);

    setupListeners();
  }

  /**
   * Sets up listeners for clickable components in the view like the card panels and grid panels.
   */
  private void setupListeners() {
    //sets up listeners for the red card panels
    for (int i = 0; i < view.getRedCardPanel().getComponentCount(); i++) {
      view.getRedCardPanel().getComponent(i).addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          handleCardClick(e);
        }
      });
    }

    //sets up listeners for the blue card panels
    for (int i = 0; i < view.getBlueCardPanel().getComponentCount(); i++) {
      view.getBlueCardPanel().getComponent(i).addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          handleCardClick(e);
        }
      });
    }

    //sets up listeners for the grid panels
    for (int i = 0; i < view.getGridPanel().getComponentCount(); i++) {
      view.getGridPanel().getComponent(i).addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          handleGridCellClick(e);
        }
      });
    }
  }

  /**
   * Given a card index, selects the respective card in the player's hand.
   *
   * @param cardIndex the card index of the card to select
   */
  @Override
  public void selectCard(int cardIndex) {
    if (cardIndex < 0 ||
            (cardIndex >= view.getRedCardPanel().getComponentCount() &&
                    player.getPlayersColor() == PlayerColor.RED) ||
            (cardIndex >= view.getBlueCardPanel().getComponentCount() &&
                    player.getPlayersColor() == PlayerColor.BLUE)) {
      view.showErrorMessage("Card index out of bounds!");
      return;
    } else if (!isItMyTurn()) {
      view.showErrorMessage("It is not your turn!");
      return;
    }

    JPanel layoutView;
    if (player.getPlayersColor() == PlayerColor.RED) {
      layoutView = view.getRedCardPanel();
    } else {
      layoutView = view.getBlueCardPanel();
    }

    CardPanel clickedCardPanel = (CardPanel) layoutView.getComponent(cardIndex);
    clickingACardLogic(clickedCardPanel);
    selectedCardIdx = cardIndex;
  }

  /**
   * Given a row and column index, selects the respective grid tile in the game grid and places the
   * selected card index there. The requirement to use this method is that there must be a selected
   * card index already.
   *
   * @param row    the row index to play to
   * @param column the column index to play to
   */
  @Override
  public void selectGridCell(int row, int column) {
    if (row < 0 || row >= view.getGridPanel().getComponentCount()) {
      view.showErrorMessage("Row index out of bounds!");
    } else if (column < 0 || column >= view.getGridPanel().getComponentCount()) {
      view.showErrorMessage("Column index out of bounds!");
    } else if (selectedCardIdx == -1) {
      view.showErrorMessage("Card index is out of bounds!");
    } else if (!isItMyTurn()) {
      view.showErrorMessage("It is not your turn!");
      return;
    }

    model.playToGrid(row, column, selectedCardIdx);
    model.battle(row, column);

    currentlyClickedCardPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    currentlyClickedCardPanel = null;
    selectedCardIdx = -1;
  }

  /**
   * Places a card to a grid cell and resets the logic of clicking a card and grid.
   *
   * @param clickedGridPanel the clicked grid panel to play to
   */
  private void placeCardToGridCell(GridPanel clickedGridPanel) {
    if (clickedGridPanel == null) {
      throw new IllegalArgumentException("ClickedGridPanel cannot be null");
    }

    int cardIndex = model.getPlayerOfColor(player.getPlayersColor()).getHand().indexOf(
            currentlyClickedCardPanel.getCardPanelCard());

    int[] rowAndColumn = convertGridIdxToRowAndColumn(clickedGridPanel);
    int row = rowAndColumn[0];
    int column = rowAndColumn[1];

    model.playToGrid(row, column, cardIndex);
    model.battle(row, column);

    currentlyClickedCardPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    currentlyClickedCardPanel = null;
  }

  /**
   * Handles clicking a card on the view.
   *
   * @param e the respective mouse event for clicking a card
   */
  private void handleCardClick(MouseEvent e) {
    if (e == null) {
      throw new IllegalArgumentException("MouseEvent cannot be null");
    } else if (!(e.getSource() instanceof CardPanel)) {
      return;
    }

    CardPanel clickedCardPanel = (CardPanel) e.getSource();

    clickingACardLogic(clickedCardPanel);
  }

  /**
   * Logic that handles clicking a card.
   *
   * @param clickedCardPanel the clicked CardPanel object
   */
  private void clickingACardLogic(CardPanel clickedCardPanel) {
    try {
      if (model.getCurrentTurnPlayer().getPlayersColor() != player.getPlayersColor()) {
        this.view.showErrorMessage("It is not your turn!");
        return;
      } else if (!model.getPlayerOfColor(player.getPlayersColor()).getHand().contains(
              clickedCardPanel.getCardPanelCard())) {
        this.view.showErrorMessage("This is not your card!");
        return;
      }

      if (currentlyClickedCardPanel == clickedCardPanel) {
        clickedCardPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        currentlyClickedCardPanel = null;
      } else {
        if (currentlyClickedCardPanel != null) {
          currentlyClickedCardPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
        }
        clickedCardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
        currentlyClickedCardPanel = clickedCardPanel;
      }
    } catch (IllegalStateException e) {
      view.showErrorMessage("The game is over! Game play will not continue!");
    }
  }

  /**
   * Handles clicking a grid cell on the view.
   *
   * @param e the respective mouse event for clicking a card
   */
  private void handleGridCellClick(MouseEvent e) {
    try {
      if (e == null) {
        throw new IllegalArgumentException("MouseEvent cannot be null");
      } else if (!(e.getSource() instanceof GridPanel)) {
        return;
      }

      if (model.getCurrentTurnPlayer().getPlayersColor() != player.getPlayersColor()) {
        this.view.showErrorMessage("It is not your turn!");
        return;
      }

      GridPanel clickedGridCell = (GridPanel) e.getSource();

      if (currentlyClickedCardPanel != null) {
        placeCardToGridCell(clickedGridCell);
      } else {
        this.view.showErrorMessage("You must select a card to play to the grid first!");
      }
    } catch (IllegalStateException ex) {
      view.showErrorMessage("The game is over! Game play will not continue!");
    }
  }

  /**
   * Converts a grid index to the respective row and column.
   *
   * @param clickedGridPanel the currently clicked GridPanel object
   * @return an array of integers where the 0th index is the row the clicked GridPanel object is
   * stored in while the 1st index is the column the clicked GridPanel object is stored in
   */
  private int[] convertGridIdxToRowAndColumn(GridPanel clickedGridPanel) {
    if (clickedGridPanel == null) {
      throw new IllegalArgumentException("ClickedGridPanel cannot be null");
    }

    int totalNumOfCells = model.getGrid().length * model.getGrid()[0].length;
    int[] rowAndColumn = new int[2];

    for (int i = 0; i < totalNumOfCells; i++) {
      if (view.getGridPanel().getComponents()[i] == clickedGridPanel) {
        rowAndColumn[0] = i / model.getGrid()[0].length;
        rowAndColumn[1] = i % model.getGrid()[0].length;
        break;
      }
    }
    return rowAndColumn;
  }

  /**
   * Returns true if it is the controller player's turn. False if otherwise.
   *
   * @return true if it is the controller player's turn, false otherwise
   */
  private boolean isItMyTurn() {
    return model.getCurrentTurnPlayer().getPlayersColor() == player.getPlayersColor();
  }

  /**
   * Refreshes the view and adds readjusts the listeners to every clickable panel.
   */
  @Override
  public void modelWasUpdated() {
    view.refresh();
    setupListeners();

    if (model.isGameOver()) {
      if (model.isGameOver()) {
        this.view.showErrorMessage("Game over! " +
                model.findWinningPlayer().getPlayersColor().toString() + " wins!");
      }
    }
  }
}