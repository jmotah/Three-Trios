package cs3500.threetrios.view.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;

import javax.swing.*;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.player.PlayerColor;
import cs3500.threetrios.view.ThreeTriosView;

/**
 * Represents the main container class for everything related to the graphical view. This includes
 * the grid panel, the individual grid panel tiles, each hand panel, and each individual card
 * on each hand panel.
 */
public class GraphicalView extends JFrame implements ThreeTriosView {

  private final ReadonlyThreeTriosModel model;

  private final PlayerCardsLayoutPanel redCardPanel;
  private final PlayerCardsLayoutPanel blueCardPanel;
  private final GridLayoutPanel gridPanel;

  /**
   * Represents a constructor for the GraphicalView class. Also initializes all the components
   * to be displayed in the view.
   *
   * @param model the read only model to gain access to immutable data from
   */
  public GraphicalView(ReadonlyThreeTriosModel model) {
    super();

    this.model = model;

    this.setTitle("Current Player: " + model.getCurrentTurnPlayer().getPlayersColor().toString());
    this.setSize(1000, 1000);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLayout(new BorderLayout());

    // Initialize the red card layout panel with cards
    redCardPanel = new PlayerCardsLayoutPanel(model.getPlayerOfColor(PlayerColor.RED));
    redCardPanel.setBackground(new Color(147, 79, 79, 255));
    JScrollPane redCardPanelScrollable = new JScrollPane(redCardPanel);
    redCardPanelScrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    redCardPanelScrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    redCardPanelScrollable.setBounds(0, 0, 150, 1000);
    this.add(redCardPanelScrollable, BorderLayout.WEST);

    //Initialize the blue card layout panel with cards
    blueCardPanel = new PlayerCardsLayoutPanel(model.getPlayerOfColor(PlayerColor.BLUE));
    blueCardPanel.setBackground(new Color(96, 103, 158, 255));
    JScrollPane blueCardPanelScrollable = new JScrollPane(blueCardPanel);
    blueCardPanelScrollable.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
    blueCardPanelScrollable.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
    blueCardPanelScrollable.setBounds(850, 0, 150, 1000);
    this.add(blueCardPanelScrollable, BorderLayout.EAST);

    //Initializes the grid layout panel
    gridPanel = new GridLayoutPanel(
            model.getGrid().length, model.getGrid()[0].length, model);
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
   * Updates the graphical frame by updating the title to the next player's turn.
   */
  private void updateFrame() {
    this.setTitle("Current Player: " + model.getCurrentTurnPlayer().getPlayersColor().toString());
  }

  /**
   * Gets the panel holding all the red player's cards.
   *
   * @return a PlayerCardsLayoutPanel object
   */
  @Override
  public PlayerCardsLayoutPanel getRedCardPanel() {
    return redCardPanel;
  }

  /**
   * Gets the panel holding all the blue player's cards.
   *
   * @return a PlayerCardsLayoutPanel object
   */
  @Override
  public PlayerCardsLayoutPanel getBlueCardPanel() {
    return blueCardPanel;
  }

  /**
   * Gets the panel holding all the grid tile panels.
   *
   * @return a GridLayoutPanel object
   */
  @Override
  public GridLayoutPanel getGridPanel() {
    return gridPanel;
  }

  /**
   * Transmit an error message to the view, in case
   * the command could not be processed correctly
   *
   * @param error the error to display to the player
   */
  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);

  }
}