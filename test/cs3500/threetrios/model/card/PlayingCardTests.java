package cs3500.threetrios.model.card;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.model.cards.PlayingCard;

/**
 * Represents tests for the PlayingCard class. Verifies the PlayingCard class works properly,
 * including any implemented methods.
 */
public class PlayingCardTests {

  PlayingCard card;

  @Before
  public void setup() {
    card = new PlayingCard("Card", CardNumbers.ONE,
            CardNumbers.TWO, CardNumbers.THREE, CardNumbers.FOUR);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayingCardConstructorNullParameters() {
    PlayingCard card = new PlayingCard(null, null, null, null, null);
  }

  @Test
  public void testPlayingCardConstructorValid() {
    PlayingCard card = new PlayingCard("Card", CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE, CardNumbers.ONE);

    Assert.assertEquals("Card", card.getName());
  }

  @Test
  public void testPlayingCardToString() {
    String expected = "Card 1 2 3 4";

    Assert.assertEquals(expected, card.toString());
  }

  @Test
  public void testPlayingCardGetName() {
    String expected = "Card";

    Assert.assertEquals(expected, card.getName());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testPlayingCardGetValueNullDirection() {
    card.getValue(null);
  }

  @Test
  public void testPlayingCardGetValueValid() {
    int expected = 3;

    Assert.assertEquals(expected, card.getValue(CardCompass.EAST_VALUE));
  }
}
