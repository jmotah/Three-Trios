package cs3500.threetrios.view.graphical;

import java.awt.GridLayout;
import java.awt.Dimension;

import javax.swing.JPanel;

import cs3500.threetrios.model.player.Players;

/**
 * Represents the grid layout for the display of the player hands for a graphical view. Manages the
 * creation and updating of all the individual card tiles.
 */
public class PlayerCardsLayoutPanel extends JPanel implements ThreeTriosLayoutView {

  private final Players player;

  /**
   * Represents the PlayerCardsLayoutPanel constructor. Sets up the initial visual view for the
   * hand layout.
   *
   * @param player the Player object to get information from
   */
  public PlayerCardsLayoutPanel(Players player) {
    if (player == null) {
      throw new IllegalArgumentException("Player cannot be null!");
    }

    this.player = player;

    int numOfCards = player.getHand().size();

    this.setLayout(new GridLayout(numOfCards, 1));
    setPreferredSize(new Dimension(110, numOfCards * 125));

    updateComponents();
  }

  /**
   * Updates all the card components within the grid layout of the player's hand. Manages this by
   * removing the current card components and re-adding the updated versions of the components.
   */
  @Override
  public void updateComponents() {
    this.removeAll();
    this.revalidate();
    this.repaint();

    int numOfCards = player.getHand().size();

    for (int cardIdx = 0; cardIdx < numOfCards; cardIdx++) {
      CardPanel card = new CardPanel(player.getHand().get(cardIdx));
      card.setBackground(player.getPlayersColor().getColor());
      card.setPreferredSize(new Dimension(110, 125 * numOfCards));
      card.repaint();
      card.revalidate();
      this.add(card);
    }
    this.repaint();
    this.revalidate();
  }
}