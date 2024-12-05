package cs3500.threetrios.providers.model.objectadapters;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Grid;

/**
 * Adapts the project's main class implementation from a 2D-array to the provider's Grid interface.
 * Adapts the primary implementation to a provider's interface.
 */
public class MainGridToProviderGrid implements Grid {

  private final cs3500.threetrios.model.grid.Grid[][] mainGrid;

  /**
   * MainGridToProviderGrid class constructor.
   *
   * @param mainGrid the main Grid interface into a 2D array implementation class to delegate to
   */
  public MainGridToProviderGrid(cs3500.threetrios.model.grid.Grid[][] mainGrid) {
    if (mainGrid == null) {
      throw new IllegalArgumentException("mainGrid cannot be null");
    }

    this.mainGrid = mainGrid;
  }

  /**
   * Retrieves the number of rows in the grid by delegating.
   *
   * @return The number of rows.
   */
  @Override
  public int getRows() {
    return mainGrid.length;
  }

  /**
   * Retrieves the number of columns in the grid by delegating.
   *
   * @return The number of columns.
   */
  @Override
  public int getCols() {
    return mainGrid[0].length;
  }

  /**
   * Retrieves a specific cell in the grid by delegating.
   *
   * @param row The row index.
   * @param col The column index.
   * @return The cell at the specified location.
   */
  @Override
  public Cell getCell(int row, int col) {
    if (row < 0 || row >= getRows() || col < 0 || col >= getCols()) {
      throw new IllegalArgumentException("Invalid row or col index!");
    }

    return new MainGridTileToProviderCell(mainGrid[row][col]);
  }

  /**
   * Retrieves a list of cells that are adjacent to the specified cell by delegating.
   *
   * @param row The row index of the target cell.
   * @param col The column index of the target cell.
   * @return A list of adjacent cells.
   * @throws IndexOutOfBoundsException if the row or column index is out of range.
   */
  @Override
  public List<Cell> getAdjacentCells(int row, int col) {
    if (row < 0 || row >= getRows() || col < 0 || col >= getCols()) {
      throw new IllegalArgumentException("Invalid row or col index!");
    }

    List<Cell> adjacentCells = new ArrayList<>();

    if (row - 1 >= 0) {
      adjacentCells.add(getCell(row - 1, col));
    }

    if (row + 1 < getRows()) {
      adjacentCells.add(getCell(row + 1, col));
    }

    if (col - 1 >= 0) {
      adjacentCells.add(getCell(row, col - 1));
    }

    if (col + 1 < getCols()) {
      adjacentCells.add(getCell(row, col + 1));
    }

    return adjacentCells;
  }

  /**
   * Checks whether a move to the specified cell is valid by delegating.
   *
   * @param row The row index of the target cell.
   * @param col The column index of the target cell.
   * @return true if the move is valid; false otherwise.
   * @throws IndexOutOfBoundsException if the row or column index is out of range.
   */
  @Override
  public boolean isValidMove(int row, int col) {
    if (row < 0 || row >= getRows() || col < 0 || col >= getCols()) {
      throw new IllegalArgumentException("Invalid row or col index!");
    }

    return mainGrid[row][col] == null;
  }

  /**
   * Creates a deep copy of the current grid, including all its cells by delegating.
   *
   * @return a new Grid instance that is a copy of the current grid, with the same
   *     dimensions and cells (but with independent copies of the cells).
   */
  @Override
  public Grid copy() {
    return new MainGridToProviderGrid(this.mainGrid);
  }
}
