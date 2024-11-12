package cs3500.threetrios.view.graphical;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.view.View;

/**
 * Represents the main container class for everything related to the graphical view. This includes
 * the grid panel, the individual grid panel tiles, each hand panel, and each individual card
 * on each hand panel.
 */
public class GraphicalView extends JFrame implements View, MouseListener {

  private final ReadonlyThreeTriosModel model;

  private final PlayerCardsLayoutPanel redCardPanel;
  private final PlayerCardsLayoutPanel blueCardPanel;
  private final GridLayoutPanel gridPanel;

  private CardPanel currentlyClickedCardPanel;
  private GridPanel currentClickedGridCell;

  /**
   * Represents a constructor for the GraphicalView class.
   *
   * @param model the read only model to gain access to immutable data from
   */
  public GraphicalView(ReadonlyThreeTriosModel model) {
    super();

    this.model = model;
    this.currentClickedGridCell = null;
    this.currentlyClickedCardPanel = null;

    this.setTitle("Current Player: " + model.getCurrentTurnPlayer().getPlayersColor().toString());
    this.setSize(1000, 1000);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // Initialize the red card layout panel with cards
    redCardPanel = new PlayerCardsLayoutPanel(model.getPlayerOfColor(PlayerColor.RED), this);
    redCardPanel.setBackground(new Color(147, 79, 79, 255));
    JScrollPane redCardPanelScrollable = new JScrollPane(redCardPanel);
    redCardPanelScrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    redCardPanelScrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    redCardPanelScrollable.setBounds(0, 0, 150, 1000);
    this.add(redCardPanelScrollable, BorderLayout.WEST);

    //Initialize the blue card layout panel with cards
    blueCardPanel = new PlayerCardsLayoutPanel(model.getPlayerOfColor(PlayerColor.BLUE), this);
    blueCardPanel.setBackground(new Color(96, 103, 158, 255));
    JScrollPane blueCardPanelScrollable = new JScrollPane(blueCardPanel);
    blueCardPanelScrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    blueCardPanelScrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    blueCardPanelScrollable.setBounds(850, 0, 150, 1000);
    this.add(blueCardPanelScrollable, BorderLayout.EAST);

    //Initializes the grid layout panel
    gridPanel = new GridLayoutPanel(
            model.getGrid().length, model.getGrid()[0].length, model, this);
    gridPanel.setBackground(new Color(188, 176, 112, 255));
    gridPanel.setPreferredSize(new Dimension(400, 400));
    this.add(gridPanel, BorderLayout.CENTER);
  }

  /**
   * Displays the graphical view.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Shows an error message.
   *
   * @param error the error message
   */
  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(
            this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Refreshes the graphical view component, updating all the components.
   */
  @Override
  public void refresh() {
    this.redCardPanel.updateComponents();
    this.blueCardPanel.updateComponents();
    this.gridPanel.updateComponents();
    this.updateFrame();
    this.repaint();
    this.revalidate();
  }

  /**
   * Checks to see if the mouse was clicked. Performs various actions depending on what was clicked.
   * If a playing card was clicked, and it is the respective player's turn, highlight the card
   * and waits for further directions. If a grid tile was clicked, highlights the grid tile and
   * waits for further directions.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseClicked(MouseEvent e) {
    GridPanel clickedGridCell = null;
    CardPanel clickedCardPanel = null;

    //Defines Clicked Grid Cel;
    try {
      clickedGridCell = (GridPanel) e.getSource();
    } catch (ClassCastException ex) {
      //Should be empty, next try will catch this
    }

    //Defines Clicked Card Panel
    try {
      clickedCardPanel = (CardPanel) e.getSource();
    } catch (ClassCastException ex) {
      //Should be empty
    }

    //Returns if they are both null
    if (clickedGridCell == null && clickedCardPanel == null) {
      return;
    }

    if (clickedGridCell != null) {
      if (currentClickedGridCell == clickedGridCell) {
        clickedGridCell.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
        currentClickedGridCell = null;
        return;
      } else if (currentClickedGridCell != null) {
        currentClickedGridCell.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
      }

      int[] rowAndColumn = convertGridIdxToRowAndColumn(clickedGridCell);

      System.out.println("Grid cell clicked -- Row: " +
              rowAndColumn[0] + ", Column: " + rowAndColumn[1]);

      clickedGridCell.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
      currentClickedGridCell = clickedGridCell;
      return;
    }

    //Keeps it turn-dependent so red turn cannot click blue cards and vice versa
    if (!model.getCurrentTurnPlayer().getHand().contains(clickedCardPanel.getCardPanelCard())) {
      return;
    }

    if (currentlyClickedCardPanel == clickedCardPanel) {
      clickedCardPanel.setBorder(
              BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
      currentlyClickedCardPanel = null;
      return;
    } else if (currentlyClickedCardPanel != null) {
      currentlyClickedCardPanel.setBorder(
              BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    }

    System.out.println("Card Clicked! -- " +
            "Player: " + model.getCurrentTurnPlayer().getPlayersColor().toString()
            + " -- Index: " + model.getCurrentTurnPlayer().getHand().indexOf(
            clickedCardPanel.getCardPanelCard()));

    clickedCardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
    currentlyClickedCardPanel = clickedCardPanel;
  }

  /**
   * Checks to see if the mouse was pressed down.
   *
   * @param e the event to be processed
   */
  @Override
  public void mousePressed(MouseEvent e) {
    //Do not need to check if the mouse was pressed down, so method is left empty.
  }

  /**
   * Checks to see if the mouse was released.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseReleased(MouseEvent e) {
    //Do not need to check if the mouse was released, so method is left empty.
  }

  /**
   * Checks to see if the mouse entered a certain space.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseEntered(MouseEvent e) {
    //Do not need to check if the mouse entered, so method is left empty.
  }

  /**
   * Checks to see if the mouse exited a certain space.
   *
   * @param e the event to be processed
   */
  @Override
  public void mouseExited(MouseEvent e) {
    //Do not need to check if the mouse exited, so method is left empty.
  }

  /**
   * Updates the graphical frame by updating the title to the next player's turn.
   */
  private void updateFrame() {
    this.setTitle("Current Player: " + model.getCurrentTurnPlayer().getPlayersColor().toString());
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
      if (gridPanel.getComponents()[i] == clickedGridPanel) {
        rowAndColumn[0] = i / model.getGrid()[0].length;
        rowAndColumn[1] = i % model.getGrid()[0].length;
        break;
      }
    }
    return rowAndColumn;
  }
}