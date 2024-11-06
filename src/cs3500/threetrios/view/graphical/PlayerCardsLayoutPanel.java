package cs3500.threetrios.view.graphical;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.threetrios.player.Player;

public class PlayerCardsLayoutPanel extends JPanel implements MouseListener {

  private final Player player;
  private final GraphicalView graphicalView;

  CardPanel currentlyClickedCardPanel;

  PlayerCardsLayoutPanel(Player player, GraphicalView graphicalView) {
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

  @Override
  public void mouseClicked(MouseEvent e) {

  }

  @Override
  public void mousePressed(MouseEvent e) {
    CardPanel clickedCard = (CardPanel) e.getSource();

    if (clickedCard == null) {
      return;
    }

    if (currentlyClickedCardPanel == clickedCard) {
      clickedCard.setBorder(
              BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
      currentlyClickedCardPanel = null;
      return;
    }

    if (currentlyClickedCardPanel != null) {
      currentlyClickedCardPanel.setBorder(
              BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    }

    clickedCard.setBorder(BorderFactory.createLineBorder(Color.BLACK, 4));
    currentlyClickedCardPanel = clickedCard;
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