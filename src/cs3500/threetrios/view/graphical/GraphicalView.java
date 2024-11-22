package cs3500.threetrios.view.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.BorderFactory;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModelListener;
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

  public PlayerCardsLayoutPanel getRedCardPanel() {
    return redCardPanel;
  }

  public PlayerCardsLayoutPanel getBlueCardPanel() {
    return blueCardPanel;
  }

  public GridLayoutPanel getGridPanel() {
    return gridPanel;
  }
}