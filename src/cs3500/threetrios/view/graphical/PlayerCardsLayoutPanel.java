package cs3500.threetrios.view.graphical;

import java.awt.*;

import javax.swing.*;

import cs3500.threetrios.model.player.Players;

/**
 * Represents the grid layout for the display of the player hands for a graphical view. Manages the
 * creation and updating of all the individual card tiles.
 */
public class PlayerCardsLayoutPanel extends JPanel {

  private final Players player;
  private final GraphicalView graphicalView;

  CardPanel currentlyClickedCardPanel;

  /**
   * Represents the PlayerCardsLayoutPanel constructor.
   *
   * @param player        the Player object to get information from
   * @param graphicalView the GraphicalView class object that oversees the grid layout
   */
  PlayerCardsLayoutPanel(Players player, GraphicalView graphicalView) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null!");
    }

    currentlyClickedCardPanel = null;

    this.player = player;
    this.graphicalView = graphicalView;

    int numOfCards = player.getHand().size();

    this.setLayout(new GridLayout(numOfCards, 1));
    setPreferredSize(new Dimension(110, 125 * numOfCards));

    for (int cardIdx = 0; cardIdx < numOfCards; cardIdx++) {
      CardPanel card = new CardPanel(player.getHand().get(cardIdx));
      card.setBackground(player.getPlayersColor().getColor());
      card.setPreferredSize(new Dimension(150, 125 * numOfCards));
      card.addMouseListener(graphicalView);
      this.add(card);
    }
  }

  /**
   * Updates all the card components within the grid layout of the player's hand. Manages this by
   * removing the current card components and re-adding the updated versions of the components.
   */
  public void updateComponents() {
    this.removeAll();
    this.revalidate();
    this.repaint();

    int numOfCards = player.getHand().size();

    for (int cardIdx = 0; cardIdx < numOfCards; cardIdx++) {
      CardPanel card = new CardPanel(player.getHand().get(cardIdx));
      card.setBackground(player.getPlayersColor().getColor());
      card.setPreferredSize(new Dimension(150, 125 * numOfCards));
      card.addMouseListener(graphicalView);
      card.repaint();
      card.revalidate();
      this.add(card);
    }
    this.repaint();
    this.revalidate();
  }
}