package cs3500.threetrios.controller;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;

import javax.swing.*;

import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModelListener;
import cs3500.threetrios.model.player.AIPlayer;
import cs3500.threetrios.model.player.AIPlayers;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.view.ThreeTriosView;
import cs3500.threetrios.view.graphical.CardPanel;
import cs3500.threetrios.view.graphical.GridPanel;

public class ThreeTriosController implements Features, ThreeTriosModelListener {

  private ThreeTriosModel model;
  private Players player;
  private ThreeTriosView view;

  private CardPanel currentlyClickedCardPanel;

  public ThreeTriosController(ThreeTriosModel model, Players player, ThreeTriosView view) {
    this.model = model;
    this.player = player;
    this.view = view;

    model.addListener(this);

    setupListeners();
  }

  private void setupListeners() {
    for (int i = 0; i < view.getRedCardPanel().getComponentCount(); i++) {
      view.getRedCardPanel().getComponent(i).addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          handleCardClick(e);
        }
      });
    }

    for (int i = 0; i < view.getBlueCardPanel().getComponentCount(); i++) {
      view.getBlueCardPanel().getComponent(i).addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          handleCardClick(e);
        }
      });
    }

    for (int i = 0; i < view.getGridPanel().getComponentCount(); i++) {
      view.getGridPanel().getComponent(i).addMouseListener(new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
          handleGridCellClick(e);
        }
      });
    }
  }

  @Override
  public void selectCard(int cardIndex) {
    if (!isItMyTurn()) {
      return;
    }

    //Card selection logic
  }

  @Override
  public void selectGridCell(int row, int column) {
    if (!isItMyTurn()) {
      return;
    }
  }

  private void placeCardToGridCell(GridPanel clickedGridPanel) {
    int cardIndex = model.getPlayerOfColor(player.getPlayersColor()).getHand().indexOf(
            currentlyClickedCardPanel.getCardPanelCard());

    int[] rowAndColumn = convertGridIdxToRowAndColumn(clickedGridPanel);
    int row = rowAndColumn[0];
    int column = rowAndColumn[1];

    try {
      model.playToGrid(row, column, cardIndex);
      model.battle(row, column);

      currentlyClickedCardPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
      currentlyClickedCardPanel = null;

      doAITurn();
    } catch (IllegalStateException | IllegalArgumentException e) {
      System.out.println("Error thrown: " + e.getMessage());
    }
  }

  private void handleCardClick(MouseEvent e) {
    if (!(e.getSource() instanceof CardPanel)) {
      return;
    }
    CardPanel clickedCardPanel = (CardPanel) e.getSource();

    if (model.getCurrentTurnPlayer().getPlayersColor() != player.getPlayersColor()) {
      return;
    }

    if (!model.getPlayerOfColor(player.getPlayersColor()).getHand().contains(
            clickedCardPanel.getCardPanelCard())) {
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
  }

  private void handleGridCellClick(MouseEvent e) {
    if (!(e.getSource() instanceof GridPanel)) {
      return;
    }
    GridPanel clickedGridCell = (GridPanel) e.getSource();

    if (currentlyClickedCardPanel != null) {
      placeCardToGridCell(clickedGridCell);
    } else {
      System.out.println("NO CARD SELECTED!");
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

  private boolean isItMyTurn() {
    return model.getCurrentTurnPlayer().getPlayersColor() == player.getPlayersColor();
  }

  private boolean isCurrentTurnPlayerAI() {
    return model.getCurrentTurnPlayer() instanceof AIPlayer;
  }

  private void doAITurn() {
    if (isCurrentTurnPlayerAI()) {

      //HashMap<Point, Integer> positionAndCardIdx = (AIPlayers) player.getStrategy();
    }
  }

  @Override
  public void modelWasUpdated() {
    view.refresh();
    setupListeners();
  }
}