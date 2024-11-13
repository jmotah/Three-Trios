package cs3500.threetrios.model.strategies;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.PlayingCard;
import cs3500.threetrios.model.grid.GridTile;

//Go for corners
public class Strategy2 extends AbstractStrategies implements Strategies {
  ReadonlyThreeTriosModel model;

  public Strategy2(ReadonlyThreeTriosModel model) {
    this.model = model;
  }

  /**
   * Runs strategy 2 and looks for the best position as well as card to play at that position in
   * respect to the strategy. The Point object in the HashMap represents the position on the grid
   * while the Integer object in the Hashmap represents the card index to play from the current
   * player's hand.
   *
   * @return a HashMap object of a Point object and an Integer object where the Point object
   * represents a tile on the grid to play the card at and the Integer object represents the card
   * index to play from the current player's hand on that grid tile
   */
  @Override
  public HashMap<Point, Integer> runStrategy() {
    HashMap<Point, Integer> optimalMove = new HashMap<>();
    optimalMove.put(tieBreakCorner(), findBestCardIdxToPlay());
    return optimalMove;
  }

  public int findBestCardIdxToPlay() {
    List<PlayingCard> hand = model.getCurrentTurnPlayer().getHand();
    Point bestCorner = tieBreakCorner();
    int bestCardIdx = 0;
    int highestDirectionalScore = 0;
    //INVARIANCE: only one item in hash map

    if (bestCorner.getX() == 0 && bestCorner.getY() == 0) {
      //East, South
      for (int i = 0; i < hand.size(); i++) {
        PlayingCard card = hand.get(i);
        int cardDirectionalScore = card.getValue(CardCompass.EAST_VALUE) +
                card.getValue(CardCompass.SOUTH_VALUE);

        if (cardDirectionalScore > highestDirectionalScore) { //accounts for lower card index too
          highestDirectionalScore = cardDirectionalScore;
          bestCardIdx = i;
        }
      }

    } else if (bestCorner.getX() == 0 && bestCorner.getY() == model.getGrid()[0].length - 1) {
      //West, South case

      for (int i = 0; i < hand.size(); i++) {
        PlayingCard card = hand.get(i);
        int cardDirectionalScore = card.getValue(CardCompass.WEST_VALUE) +
                card.getValue(CardCompass.SOUTH_VALUE);

        if (cardDirectionalScore > highestDirectionalScore) { //accounts for lower card index too
          highestDirectionalScore = cardDirectionalScore;
          bestCardIdx = i;
        }
      }
    } else if (bestCorner.getX() == model.getGrid().length - 1 && bestCorner.getY() == 0) {
      //North, east case

      for (int i = 0; i < hand.size(); i++) {
        PlayingCard card = hand.get(i);
        int cardDirectionalScore = card.getValue(CardCompass.NORTH_VALUE) +
                card.getValue(CardCompass.EAST_VALUE);

        if (cardDirectionalScore > highestDirectionalScore) { //accounts for lower card index too
          highestDirectionalScore = cardDirectionalScore;
          bestCardIdx = i;
        }
      }
    } else if (bestCorner.getX() == model.getGrid().length - 1 && bestCorner.getY() == model.getGrid()[0].length - 1) {
      //North, west case

      for (int i = 0; i < hand.size(); i++) {
        PlayingCard card = hand.get(i);
        int cardDirectionalScore = card.getValue(CardCompass.NORTH_VALUE) +
                card.getValue(CardCompass.WEST_VALUE);

        if (cardDirectionalScore > highestDirectionalScore) { //accounts for lower card index too
          highestDirectionalScore = cardDirectionalScore;
          bestCardIdx = i;
        }
      }
    }
    //base case is index = 0
    return bestCardIdx;
  }

  public Point tieBreakCorner() {
    List<Point> allCorners = findAllCorners();
    Point upperMostLeftMostOpenCorner = null;

    if (!allCorners.isEmpty()) {
      for (int corner = 0; corner < allCorners.size(); corner++) {
        if (upperMostLeftMostOpenCorner == null) {
          upperMostLeftMostOpenCorner = allCorners.get(corner);
        } else {
          upperMostLeftMostOpenCorner = comparePositions(
                  upperMostLeftMostOpenCorner, allCorners.get(corner));
        }
      }
    } else {
      List<Point> allPossibilities = getAllPossibleMoves(model);
      for (int possibility = 0; possibility < allPossibilities.size(); possibility++) {
        if (upperMostLeftMostOpenCorner == null) {
          upperMostLeftMostOpenCorner = allPossibilities.get(possibility);
        } else {
          upperMostLeftMostOpenCorner = comparePositions(upperMostLeftMostOpenCorner,
                  allPossibilities.get(possibility));
        }
      }
    }
    return upperMostLeftMostOpenCorner;
  }

  public List<Point> findAllCorners() {
    List<Point> allCorners = new ArrayList<>();

    List<Point> allPossibilities = getAllPossibleMoves(model);

    for (int possibility = 0; possibility < allPossibilities.size(); possibility++) {
      if (isACornerPosition(allPossibilities.get(possibility))) {
        allCorners.add(allPossibilities.get(possibility));
      }
    }
    return allCorners;
  }

  /**
   * Checks to see if the position is the corner position on the game grid.
   *
   * @param position the position on the grid; a Point object represents the position on the game
   *                 grid
   * @return true if the Point object is a corner position, false otherwise
   */
  public boolean isACornerPosition(Point position) {
    GridTile[][] grid = model.getGrid();

    if (position == null) {
      return false;
    } else if (position.getX() < 0 ||
            position.getX() >= grid.length ||
            position.getY() < 0 ||
            position.getY() >= grid[0].length) {
      throw new IllegalArgumentException("Given position is out of bounds for the grid!");
    }

    return (position.getX() == 0 &&
            position.getY() == 0) ||

            (position.getX() == 0 &&
                    position.getY() == grid[0].length - 1) ||

            (position.getX() == grid.length - 1 &&
                    position.getY() == 0) ||

            (position.getX() == grid.length - 1 &&
                    position.getY() == grid[0].length - 1);
  }

}
