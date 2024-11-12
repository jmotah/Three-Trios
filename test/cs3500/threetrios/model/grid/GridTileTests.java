package cs3500.threetrios.model.grid;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.CardNumbers;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.CellType;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.model.player.Player;
import cs3500.threetrios.model.player.Players;
import cs3500.threetrios.model.player.PlayerColor;

/**
 * Represents tests for the GridTile class. Verifies GridTile objects are instantiated properly
 * as well as verifying the compareTo method.
 */
public class GridTileTests {

  Players human;
  Players human2;

  @Before
  public void setup() {
    human = new Player(PlayerColor.RED, List.of(new PlayingCard("Card",
            CardNumbers.ONE, CardNumbers.ONE, CardNumbers.ONE, CardNumbers.ONE)));

    human2 = new Player(PlayerColor.RED, List.of(new PlayingCard("Card",
            CardNumbers.TWO, CardNumbers.TWO, CardNumbers.TWO, CardNumbers.TWO)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGridTileConstructorNullCellType() {
    GridTile tile = new GridTile(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGridTileConstructorPlayerCellError() {
    GridTile tile = new GridTile(CellType.PLAYER_CELL);
  }

  @Test
  public void testGridTileConstructorHole() {
    GridTile tile = new GridTile(CellType.HOLE);

    Assert.assertEquals(tile.getCellType(), CellType.HOLE);
    Assert.assertNull(tile.getWhichPlayersTile());
    Assert.assertNull(tile.getPlayingCard());
  }

  @Test
  public void testGridTileConstructorCardCell() {
    GridTile tile = new GridTile(CellType.CARD_CELL);

    Assert.assertEquals(tile.getCellType(), CellType.CARD_CELL);
    Assert.assertNull(tile.getWhichPlayersTile());
    Assert.assertNull(tile.getPlayingCard());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGridTileConstructor2NullCellType() {
    GridTile tile = new GridTile(null, null, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGridTileConstructor2PlayerCellWithNullCellCardAndOrCellPlayer() {
    GridTile tile = new GridTile(CellType.PLAYER_CELL, null, null);
  }

  @Test
  public void testGridTileConstructor2Hole() {
    GridTile tile = new GridTile(CellType.HOLE, new PlayingCard("Card", CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE, CardNumbers.ONE), human);

    PlayingCard expectedCard = null;
    Players expectedPlayer = null;

    Assert.assertEquals(expectedCard, tile.getPlayingCard());
    Assert.assertEquals(expectedPlayer, tile.getWhichPlayersTile());
  }

  @Test
  public void testGridTileConstructor2CardCell() {
    GridTile tile = new GridTile(CellType.CARD_CELL, new PlayingCard("Card", CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE, CardNumbers.ONE), human);

    PlayingCard expectedCard = null;
    Players expectedPlayer = null;

    Assert.assertEquals(expectedCard, tile.getPlayingCard());
    Assert.assertEquals(expectedPlayer, tile.getWhichPlayersTile());
  }

  @Test
  public void testGridTileConstructor2PlayerCell() {
    GridTile tile = new GridTile(CellType.PLAYER_CELL, new PlayingCard("Card", CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE, CardNumbers.ONE), human);

    PlayingCard expectedCard = new PlayingCard("Card", CardNumbers.ONE, CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.ONE);
    Players expectedPlayer = human;

    Assert.assertEquals(expectedCard, tile.getPlayingCard());
    Assert.assertEquals(expectedPlayer, tile.getWhichPlayersTile());
  }

  @Test
  public void testGridTileGetCellType() {
    GridTile tile = new GridTile(CellType.PLAYER_CELL, new PlayingCard("Card", CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.TWO, CardNumbers.ONE), human);

    Assert.assertEquals(CellType.PLAYER_CELL, tile.getCellType());
  }

  @Test
  public void testGridTileGetPlayingCard() {
    GridTile tile = new GridTile(CellType.PLAYER_CELL, new PlayingCard("Card", CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.TWO, CardNumbers.ONE), human);

    PlayingCard expected = new PlayingCard("Card", CardNumbers.ONE,
            CardNumbers.ONE, CardNumbers.TWO, CardNumbers.ONE);

    Assert.assertEquals(expected, tile.getPlayingCard());
  }

  @Test
  public void testGridTileGetWhichPlayersTile() {
    GridTile tile = new GridTile(CellType.PLAYER_CELL, new PlayingCard("Card", CardNumbers.ONE,
            CardNumbers.TWO, CardNumbers.TWO, CardNumbers.ONE), human);

    Assert.assertEquals(human, tile.getWhichPlayersTile());
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGridTileCompareToOneCardCardCell() {
    GridTile tile = new GridTile(CellType.PLAYER_CELL, new PlayingCard("Card", CardNumbers.ONE,
            CardNumbers.TWO, CardNumbers.TWO, CardNumbers.ONE), human);
    GridTile tile2 = new GridTile(CellType.CARD_CELL);

    tile.compareTo(tile2, CardCompass.NORTH_VALUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGridTileCompareOtherTileIsNull() {
    GridTile tile = new GridTile(CellType.PLAYER_CELL, new PlayingCard("Card", CardNumbers.ONE,
            CardNumbers.TWO, CardNumbers.TWO, CardNumbers.ONE), human);

    GridTile other = null;

    tile.compareTo(other, CardCompass.NORTH_VALUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGridTileCompareBothCellCardsAreNull() {
    GridTile tile = new GridTile(CellType.HOLE);
    GridTile tile2 = new GridTile(CellType.CARD_CELL);

    tile.compareTo(tile2, CardCompass.NORTH_VALUE);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testGridTileCompareToNullCompareDirection() {
    GridTile tile = new GridTile(CellType.PLAYER_CELL, new PlayingCard("Card",
            CardNumbers.THREE, CardNumbers.THREE, CardNumbers.THREE, CardNumbers.THREE), human);
    GridTile tile2 = new GridTile(CellType.PLAYER_CELL, new PlayingCard("Card",
            CardNumbers.FOUR, CardNumbers.FOUR, CardNumbers.FOUR, CardNumbers.FOUR), human2);

    tile.compareTo(tile2, null);
  }

  @Test
  public void testGridTileCompareToValid() {
    GridTile tile = new GridTile(CellType.PLAYER_CELL, new PlayingCard("Card",
            CardNumbers.THREE, CardNumbers.THREE, CardNumbers.THREE, CardNumbers.THREE), human);
    GridTile tile2 = new GridTile(CellType.PLAYER_CELL, new PlayingCard("Card",
            CardNumbers.FOUR, CardNumbers.FOUR, CardNumbers.FOUR, CardNumbers.FOUR), human2);

    Assert.assertEquals(tile2, tile.compareTo(tile2, CardCompass.NORTH_VALUE));
  }

  @Test
  public void testGridTileCompareToValid2() {
    GridTile tile = new GridTile(CellType.PLAYER_CELL, new PlayingCard("Card",
            CardNumbers.THREE, CardNumbers.THREE, CardNumbers.THREE, CardNumbers.THREE), human);
    GridTile tile2 = new GridTile(CellType.PLAYER_CELL, new PlayingCard("Card",
            CardNumbers.FOUR, CardNumbers.TWO, CardNumbers.FOUR, CardNumbers.FOUR), human2);

    Assert.assertEquals(tile, tile.compareTo(tile2, CardCompass.NORTH_VALUE));
  }
}