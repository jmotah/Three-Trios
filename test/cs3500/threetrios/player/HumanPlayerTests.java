package cs3500.threetrios.player;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.threetrios.cards.CardNumbers;
import cs3500.threetrios.cards.PlayingCard;

/**
 * Represents tests for the HumanPlayer class. Verifies the constructor works properly as well as
 * any of its implemented methods.
 */
public class HumanPlayerTests {

  Player human;

  @Before
  public void setup() {
    human = new HumanPlayer(PlayerColor.RED, List.of(
            new PlayingCard("Card1", CardNumbers.ONE, CardNumbers.ONE,
                    CardNumbers.ONE, CardNumbers.ONE),
            new PlayingCard("Card1", CardNumbers.TWO, CardNumbers.TWO,
                    CardNumbers.TWO, CardNumbers.TWO)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHumanPlayerConstructorNullColor() {
    human = new HumanPlayer(null, List.of());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testHumanPlayerConstructorNullHand() {
    human = new HumanPlayer(PlayerColor.RED, null);
  }

  @Test
  public void testHumanPlayerGetPlayersColor() {
    PlayerColor expected = PlayerColor.RED;

    Assert.assertEquals(expected, human.getPlayersColor());
  }

  @Test
  public void testHumanPlayerGetHand() {
    List<PlayingCard> expected = List.of(
            new PlayingCard("Card1", CardNumbers.ONE, CardNumbers.ONE,
                    CardNumbers.ONE, CardNumbers.ONE),
            new PlayingCard("Card1", CardNumbers.TWO, CardNumbers.TWO,
                    CardNumbers.TWO, CardNumbers.TWO));

    Assert.assertEquals(expected, human.getHand());
  }

  @Test (expected = IllegalArgumentException.class)
  public void testHumanPlayerRemoveCardAtIndexLessThanZero() {
    human.removeCardAtIndex(-1);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testHumanPlayerRemoveCardAtIndexGreaterThanHandSize() {
    human.removeCardAtIndex(5);
  }
}
