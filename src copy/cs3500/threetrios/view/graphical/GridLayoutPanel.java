package cs3500.threetrios.view.graphical;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Font;

import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.BorderFactory;
import javax.swing.SwingConstants;

import cs3500.threetrios.model.cards.CardCompass;
import cs3500.threetrios.model.cards.Cards;
import cs3500.threetrios.model.grid.CellType;
import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;

/**
 * Represents the grid layout for the display of the grid for a graphical view. Manages the creation
 * and updating of all the individual grid tiles.
 */
public class GridLayoutPanel extends JPanel implements ThreeTriosLayoutView {

  private final int rows;
  private final int columns;
  private final ReadonlyThreeTriosModel model;
  private Grid[][] grid;
  private final GraphicalView view;

  /**
   * Represents a constructor for the GridLayoutPanel class. Sets up the initial visual view for
   * the grid layout.
   *
   * @param rows    the number of rows for the grid layout
   * @param columns the number of columns for the grid layout
   * @param model   a read only version of the model to gain immutable data from
   */
  public GridLayoutPanel(int rows, int columns, ReadonlyThreeTriosModel model, GraphicalView view) {
    if (rows < 0 || columns < 0) {
      throw new IllegalArgumentException("Rows and columns cannot be negative!");
    } else if (model == null) {
      throw new IllegalArgumentException("Model cannot be null!");
    }

    this.view = view;
    this.rows = rows;
    this.columns = columns;
    this.model = model;
    this.grid = model.getGrid();

    this.setLayout(new GridLayout(rows, columns));
    this.setPreferredSize(new Dimension(400, 400));

    this.updateComponents();
  }

  /**
   * Updates all the components within the grid layout. Manages this by removing the current
   * components and re-adding the updated versions of the components. Revalidates after removal and
   * re-addition of components. Accounts for decorators.
   */
  @Override
  public void updateComponents() {
    this.removeAll();
    this.revalidate();
    this.repaint();

    grid = model.getGrid();

    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        GridPanel panelTile = new GridPanel();

        Grid tile = grid[row][column];

        performOperationBasedOnCellType(tile, panelTile);

        if (view.getHintsEnabled() && tile.getCellType() == CellType.CARD_CELL) {
          GridPanel decoratedTile = new HintDecorator(panelTile, model,
                  row, column, view).getDecoratedGridPanel();
          this.add(decoratedTile);
        } else {
          this.add(panelTile);
        }
      }
    }
    this.revalidate();
    this.repaint();
  }

  /**
   * Performs various operations depending on cell type including possibilities like setting
   * background colors, applying mouse listeners, and adding layouts to the given panelTile.
   *
   * @param tile      the tile to grab the cell type from
   * @param panelTile the panel tile to apply the operations to
   */
  private void performOperationBasedOnCellType(Grid tile, GridPanel panelTile) {
    if (tile == null) {
      throw new IllegalArgumentException("Tile cannot be null!");
    } else if (panelTile == null) {
      throw new IllegalArgumentException("Panel tile cannot be null!");
    }

    switch (tile.getCellType()) {
      case HOLE:
        panelTile.setBackground(Color.GRAY);
        break;
      case CARD_CELL:
        panelTile.setBackground(Color.YELLOW);
        break;
      case PLAYER_CELL:
        panelTile.setBackground(tile.getWhichPlayersTile().getPlayersColor().getColor());
        panelTile.setLayout(new BorderLayout());

        Cards card = tile.getPlayingCard();

        JLabel north = new JLabel(card.getValueAsString(CardCompass.NORTH_VALUE));
        JLabel south = new JLabel(card.getValueAsString(CardCompass.SOUTH_VALUE));
        JLabel east = new JLabel(card.getValueAsString(CardCompass.EAST_VALUE));
        JLabel west = new JLabel(card.getValueAsString(CardCompass.WEST_VALUE));

        north.setHorizontalAlignment(SwingConstants.CENTER);
        north.setForeground(Color.BLACK);
        north.setFont(new Font("Arial", Font.PLAIN, 30));
        //properly centers the number and adds a padding due to scrollbar
        north.setBorder(BorderFactory.createEmptyBorder(15, 0, 0, 15));

        south.setHorizontalAlignment(SwingConstants.CENTER);
        south.setForeground(Color.BLACK);
        south.setFont(new Font("Arial", Font.PLAIN, 30));
        //properly centers the number and adds a padding due to scrollbar
        south.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 15));

        east.setForeground(Color.BLACK);
        east.setFont(new Font("Arial", Font.PLAIN, 30));
        //allows the east attack number to not be hidden by the scroll bar
        east.setBorder(BorderFactory.createEmptyBorder(0, 0, 0, 15));

        west.setForeground(Color.BLACK);
        west.setFont(new Font("Arial", Font.PLAIN, 30));
        //adds a padding due to scrollbar
        west.setBorder(BorderFactory.createEmptyBorder(0, 15, 0, 0));

        panelTile.add(north, BorderLayout.NORTH);
        panelTile.add(south, BorderLayout.SOUTH);
        panelTile.add(east, BorderLayout.EAST);
        panelTile.add(west, BorderLayout.WEST);
        break;
      default:
        break;
    }
  }
}