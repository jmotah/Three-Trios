package cs3500.threetrios.view.graphical;

import java.awt.*;

import javax.swing.*;

import cs3500.threetrios.cards.CardCompass;
import cs3500.threetrios.cards.PlayingCard;

public class CardPanel extends JPanel {

  private PlayingCard card;

  CardPanel(PlayingCard card) {
    this.card = card;
    this.setLayout(new BorderLayout());
    setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.GRAY));
    JLabel north = new JLabel(card.getValueAsString(CardCompass.NORTH_VALUE));
    JLabel south = new JLabel(card.getValueAsString(CardCompass.SOUTH_VALUE));
    JLabel east = new JLabel(card.getValueAsString(CardCompass.EAST_VALUE));
    JLabel west = new JLabel(card.getValueAsString(CardCompass.WEST_VALUE));

    north.setHorizontalAlignment(SwingConstants.CENTER);
    north.setForeground(Color.BLACK);
    north.setFont(new Font("Arial", Font.PLAIN, 20));
    //properly centers the number and adds a padding due to scrollbar
    north.setBorder(BorderFactory.createEmptyBorder(5, 0, 0, 15));

    south.setHorizontalAlignment(SwingConstants.CENTER);
    south.setForeground(Color.BLACK);
    south.setFont(new Font("Arial", Font.PLAIN, 20));
    //properly centers the number and adds a padding due to scrollbar
    south.setBorder(BorderFactory.createEmptyBorder(0, 0, 5, 15));

    east.setForeground(Color.BLACK);
    east.setFont(new Font("Arial", Font.PLAIN, 20));
    //allows the east attack number to not be hidden by the scroll bar
    east.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 21));

    west.setForeground(Color.BLACK);
    west.setFont(new Font("Arial", Font.PLAIN, 20));
    //adds a padding due to scrollbar
    west.setBorder(BorderFactory.createEmptyBorder(0, 5, 0, 0));

    this.add(north, BorderLayout.NORTH);
    this.add(south, BorderLayout.SOUTH);
    this.add(east, BorderLayout.EAST);
    this.add(west, BorderLayout.WEST);
  }

  public PlayingCard getCardPanelCard() {
    return card;
  }
}