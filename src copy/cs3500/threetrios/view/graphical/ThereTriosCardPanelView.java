package cs3500.threetrios.view.graphical;

import cs3500.threetrios.model.cards.Cards;

/**
 * An interface representing the view for a card panel within a card panel layout with necessary
 * methods to retrieve the PlayingCard object displayed in the panel.
 */
public interface ThereTriosCardPanelView {
  /**
   * Gets the PlayingCard object associated with this card panel.
   *
   * @return the PlayingCard object associated with this card panel
   */
  Cards getCardPanelCard();
}
