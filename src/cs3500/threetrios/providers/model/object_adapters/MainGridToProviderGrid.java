package cs3500.threetrios.providers.model.object_adapters;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.providers.model.Cell;
import cs3500.threetrios.providers.model.Grid;

public class MainGridToProviderGrid implements Grid {

  private cs3500.threetrios.model.grid.Grid[][] mainGrid;

  public MainGridToProviderGrid(cs3500.threetrios.model.grid.Grid[][] mainGrid) {
    if (mainGrid == null) {
      throw new IllegalArgumentException("mainGrid cannot be null");
    }

    this.mainGrid = mainGrid;
  }

  @Override
  public int getRows() {
    return mainGrid.length;
  }

  @Override
  public int getCols() {
    return mainGrid[0].length;
  }

  @Override
  public Cell getCell(int row, int col) {
    if (row < 0 || row >= getRows() || col < 0 || col >= getCols()) {
      throw new IllegalArgumentException("Invalid row or col index!");
    }

    return new MainGridTileToProviderCell(mainGrid[row][col]);
  }

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

  @Override
  public boolean isValidMove(int row, int col) {
    if (row < 0 || row >= getRows() || col < 0 || col >= getCols()) {
      throw new IllegalArgumentException("Invalid row or col index!");
    }

    if (mainGrid[row][col] == null) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Grid copy() {

    return new MainGridToProviderGrid(this.mainGrid);
  }
}
