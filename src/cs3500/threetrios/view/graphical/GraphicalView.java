package cs3500.threetrios.view.graphical;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.threetrios.grid.GridTile;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;
import cs3500.threetrios.player.PlayerColor;
import cs3500.threetrios.view.View;

public class GraphicalView extends JFrame implements View, MouseListener {

  private final ReadonlyThreeTriosModel model;

  private final PlayerCardsLayoutPanel redCardPanel;
  private final PlayerCardsLayoutPanel blueCardPanel;
  private final GridLayoutPanel gridPanel;

  private CardPanel currentlyClickedCardPanel;
  private GridPanel currentClickedGridCell;

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

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(
            this, error, "Error", JOptionPane.ERROR_MESSAGE);
  }

  @Override
  public void refresh() {
    this.redCardPanel.updateComponents();
    this.blueCardPanel.updateComponents();
    this.gridPanel.updateComponents();
    this.updateFrame();
    this.repaint();
    this.revalidate();
  }

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

    clickedCardPanel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
    currentlyClickedCardPanel = clickedCardPanel;
  }

  @Override
  public void mousePressed(MouseEvent e) {

  }

  @Override
  public void mouseReleased(MouseEvent e) {

  }

  @Override
  public void mouseEntered(MouseEvent e) {

  }

  @Override
  public void mouseExited(MouseEvent e) {

  }

  private void updateFrame() {
    this.setTitle("Current Player: " + model.getCurrentTurnPlayer().getPlayersColor().toString());
  }
}