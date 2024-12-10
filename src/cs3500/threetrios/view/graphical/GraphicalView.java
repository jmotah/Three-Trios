package cs3500.threetrios.view.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JButton;
import javax.swing.JOptionPane;


import cs3500.threetrios.controller.HintsToggleListener;
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

  private int currentlyClickedCardIndex;

  private boolean hintsEnabled;

  private HintsToggleListener hintsToggleListener;

  /**
   * Represents a constructor for the GraphicalView class. Also initializes all the components
   * to be displayed in the view.
   *
   * @param model the read only model to gain access to immutable data from
   */
  public GraphicalView(ReadonlyThreeTriosModel model) {
    super();

    this.model = model;
    this.currentlyClickedCardIndex = -1;

    hintsEnabled = false;

    //Basic initializes for the window
    this.updateFrame();
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
            model.getGrid().length, model.getGrid()[0].length, model, this);
    gridPanel.setBackground(new Color(188, 176, 112, 255));
    gridPanel.setPreferredSize(new Dimension(400, 400));
    this.add(gridPanel, BorderLayout.CENTER);

    JButton hintToggle = new JButton("Toggle Hints");
    hintToggle.addActionListener(this::toggleHintsListener);
    this.add(hintToggle, BorderLayout.SOUTH);
  }

  /**
   * Action listener for the toggle hints button. Updates the grid panel components and delegates
   * to the HintsToggleListener object to re-setup our mouse click listeners in the controller.
   *
   * @param e the action event; what happened to the button
   */
  private void toggleHintsListener(ActionEvent e) {
    hintsEnabled = !hintsEnabled;

    this.getGridPanel().updateComponents();
    this.repaint();
    this.revalidate();
    hintsToggleListener.hintsToggled();
  }

  /**
   * Displays the graphical view.
   */
  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  /**
   * Sets a HintsToggleListener object to be called whenever the hints button is toggled.
   *
   * @param listener the controller listener to attach to here to be called whenever the hints
   *                 button is toggled
   */
  public void setHintsToggleListener(HintsToggleListener listener) {
    if (listener == null) {
      throw new IllegalArgumentException("Listener cannot be null!");
    }

    this.hintsToggleListener = listener;
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
   * Transmit a message to the views.
   *
   * @param title   the title of the display box
   * @param message the message to display to the player
   */
  @Override
  public void showMessage(String title, String message) {
    JOptionPane.showMessageDialog(this,
            message,
            title,
            JOptionPane.INFORMATION_MESSAGE);
  }

  /**
   * Transmit an error message to the view, in case the command could not be processed correctly.
   *
   * @param error the error to display to the player
   */
  @Override
  public void showErrorMessage(String error) {
    JOptionPane.showMessageDialog(this, error, "Error",
            JOptionPane.ERROR_MESSAGE);
  }

  /**
   * Returns the currently clicked card index.
   *
   * @return the currently clicked card index
   */
  public int getClickedCardIndex() {
    return currentlyClickedCardIndex;
  }

  /**
   * Returns whether hints are enabled or not.
   *
   * @return true if hints are enabled, false if otherwise
   */
  public boolean getHintsEnabled() {
    return hintsEnabled;
  }

  /**
   * Sets the currently clicked card index.
   *
   * @param currentlyClickedCardIndex the card index to set the currently clicked card index to
   */
  public void setCurrentlyClickedCardIndex(int currentlyClickedCardIndex) {
    this.currentlyClickedCardIndex = currentlyClickedCardIndex;
  }
}