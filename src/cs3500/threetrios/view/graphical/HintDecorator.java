package cs3500.threetrios.view.graphical;

import java.awt.*;
import java.util.HashMap;

import javax.swing.*;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.aistrategies.Strategy1;

/**
 * Decorates a GridPanel object with the text of a hint of how many cards can be flipped if the
 * currently selected card is placed to that cell.
 */
public class HintDecorator extends GridPanel {

  private final GridPanel baseGridPanel;

  private final ReadonlyThreeTriosModel model;

  private final int row;
  private final int column;

  private final GraphicalView view;

  /**
   * HintDecorator class constructor.
   *
   * @param baseGridPanel the base GridPanel object to decorate
   * @param model         the model to consult for immutable data
   * @param row           the row index of the grid panel
   * @param column        the column index of the grid panel
   * @param view          the overseeing view component
   */
  public HintDecorator(GridPanel baseGridPanel,
                       ReadonlyThreeTriosModel model,
                       int row, int column,
                       GraphicalView view) {

    if (baseGridPanel == null) {
      throw new IllegalArgumentException("Base GridPanel cannot be null.");
    } else if (model == null) {
      throw new IllegalArgumentException("Model cannot be null.");
    } else if (view == null) {
      throw new IllegalArgumentException("View cannot be null.");
    } else if (row < 0 || column < 0 ||
            row >= model.getGrid().length || column >= model.getGrid()[0].length) {
      throw new IllegalArgumentException("Row or column index is out of bounds!");
    }

    this.model = model;
    this.view = view;
    this.baseGridPanel = baseGridPanel;

    this.row = row;
    this.column = column;
  }

  /**
   * Gets the newly decorated grid panel with a hint score added on it.
   *
   * @return a newly decorated GridPanel object with a hint added on it
   */
  public GridPanel getDecoratedGridPanel() {
    JLabel hintScoreValue = new JLabel("" + getScoreIfPlayedToThisPositionForClickedCard(
            view.getClickedCardIndex()));
    hintScoreValue.setHorizontalAlignment(SwingConstants.CENTER);
    hintScoreValue.setVerticalAlignment(SwingConstants.CENTER);
    hintScoreValue.setForeground(Color.BLACK);
    hintScoreValue.setFont(new Font("Arial", Font.PLAIN, 20));
    baseGridPanel.add(hintScoreValue);

    return baseGridPanel;
  }

  /**
   * Gets what the score would be if the user played the provided card index to the row and column
   * index of the given grid panel.
   *
   * @param currentlyClickedCardIndex the card index to emulate battle with at the grid panel's row
   *                                  and column index
   * @return an integer of the score representing how many cards would be flipped
   */
  private int getScoreIfPlayedToThisPositionForClickedCard(int currentlyClickedCardIndex) {
    if (currentlyClickedCardIndex < 0) {
      return 0;
    }

    Strategy1 strategy1 = new Strategy1(model);

    HashMap<Point, Integer> allPossibleMovesWithScores =
            strategy1.emulateBattleToFindScoreForOneCardInAllPossibleSpaces(
                    currentlyClickedCardIndex);

    Point gridPoint = new Point(row, column);

    return allPossibleMovesWithScores.get(gridPoint);
  }
}