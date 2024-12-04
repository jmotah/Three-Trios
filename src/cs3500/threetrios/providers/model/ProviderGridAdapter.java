package cs3500.threetrios.providers.model;

import java.util.ArrayList;
import java.util.List;

import cs3500.threetrios.providers.Utilities;

public class ProviderGridAdapter implements Grid {

  private Cell[][] grid;
  private Utilities utilities;

  public ProviderGridAdapter(Cell[][] grid) {
    if (grid == null) {
      throw new IllegalArgumentException("Grid cannot be null");
    }

    this.utilities = new Utilities();

    this.grid = grid;
  }

  @Override
  public int getRows() {
    return grid.length;
  }

  @Override
  public int getCols() {
    return grid[0].length;
  }

  @Override
  public Cell getCell(int row, int col) {
    if (row < 0 || row >= getRows() || col < 0 || col >= getCols()) {
      throw new IllegalArgumentException("Invalid row or col index!");
    }

    return grid[row][col];
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

    if (grid[row][col] == null) {
      return true;
    } else {
      return false;
    }
  }

  @Override
  public Grid copy() {
    return new ProviderGridAdapter(this.grid);
  }
}