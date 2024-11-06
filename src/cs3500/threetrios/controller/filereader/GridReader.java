package cs3500.threetrios.controller.filereader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import cs3500.threetrios.model.CellType;
import cs3500.threetrios.grid.GridTile;

/**
 * Reads a grid configuration file to initialize the number of rows and columns as well as the grid
 * representation as a 2D array of GridTile objects.
 * A grid configuration file must be made in a specific way. The first line represents
 * size of the grid. The first integer is the rows while the second integer, separated by a space
 * is the columns. The very next line is where you create your grid layout. This layout must follow
 * the rows and column restrictions you placed on the previous line, meaning if you set your rows
 * as 3 and columns as 2, your grid layout must have 3 rows and 2 columns where each row is
 * determined by a new line. A 'X' represents an unplayable cell in the grid while a 'C' represents
 * a playable cell in the grid.
 * GRID COORDINATE SYSTEM WITH 0-BASED INDICES:
 * 3 4
 * [0,0][0,1][0,2][0,3]
 * [1,0][1,1][1,2][1,3]
 * [2,0][2,1][2,2][3,3]
 * [3,0][3,1][3,2][3,3]
 * SAMPLE GRID CONFIGURATION FILE:
 * 3 4
 * CCXC
 * XXCC
 * CXCC
 */
public class GridReader implements ConfigurationReader<GridTile[][]> {

  private final BufferedReader gridConfigReader;

  private int numRows;
  private int numCols;

  /**
   * Constructor for a GridReader class.
   *
   * @param gridConfig the grid configuration file to read
   */
  public GridReader(File gridConfig) {
    if (gridConfig == null) {
      throw new IllegalArgumentException("Grid file cannot be null");
    }

    this.numRows = 0;
    this.numCols = 0;

    try {
      this.gridConfigReader = new BufferedReader(new FileReader(gridConfig));
    } catch (FileNotFoundException e) {
      throw new IllegalArgumentException("The grid file could not be found!");
    }
  }

  /**
   * Reads the grid configuration file to determine number of rows, columns, and grid layout.
   *
   * @return the grid layout in the form of a 2D array of GridTiles.
   */
  @Override
  public GridTile[][] readConfiguration() {
    readRowAndColumnSizeFromGridConfig();

    return readGridFromGridConfig();
  }

  /**
   * Reads the desired number of rows and column sizes from the grid configuration file.
   */
  private void readRowAndColumnSizeFromGridConfig() {
    try {
      String readLine = this.gridConfigReader.readLine();

      if (readLine == null) {
        throw new IllegalArgumentException("The row int and column int are unreadable!");
      }

      int rowValue = Integer.parseInt(
              readLine.substring(0, readLine.length() - 2));
      int columnValue = Integer.parseInt(
              readLine.substring(readLine.length() - 1));

      this.numRows = rowValue;
      this.numCols = columnValue;
    } catch (IOException e) {
      throw new IllegalArgumentException("The row int and column int are unreadable!");
    } catch (NumberFormatException e) {
      throw new IllegalArgumentException("The row int and column int must be integers!");
    }
  }

  /**
   * Reads the grid in the grid configuration file and converts it to a valid 2D array of
   * GridTile objects.
   *
   * @return the read 2D GridTile object to become the grid
   */
  private GridTile[][] readGridFromGridConfig() {
    try {
      String readLine = this.gridConfigReader.readLine();
      GridTile[][] grid = new GridTile[numRows][numCols];

      int lineRowIndex = 0;

      if (readLine == null) {
        throw new IllegalArgumentException("The grid is unreadable!");
      }

      for (int rowIndex = 0; rowIndex < numRows; rowIndex++) {
        if (readLine == null) {
          throw new IllegalArgumentException("The grid configuration has an incorrect row count!");
        } else if (readLine.length() != numCols) {
          throw new IllegalArgumentException("The grid configuration has incorrect column count!");
        }

        for (int columnIndex = 0; columnIndex < numCols; columnIndex++) {
          char nextIndexChar = readLine.charAt(columnIndex);

          if (Character.toLowerCase(nextIndexChar) == 'x') {
            grid[lineRowIndex][columnIndex] = new GridTile(CellType.HOLE);
          } else if (Character.toLowerCase(nextIndexChar) == 'c') {
            grid[lineRowIndex][columnIndex] = new GridTile(CellType.CARD_CELL);
          } else {
            throw new IllegalArgumentException("The grid configuration has an incorrect input!");
          }
        }
        readLine = this.gridConfigReader.readLine();
        lineRowIndex++;
      }
      if (readLine != null && (lineRowIndex != numRows || !readLine.isEmpty())) {
        throw new IllegalArgumentException("The grid configuration has incorrect row count.");
      }

      return grid;
    } catch (IOException e) {
      throw new IllegalArgumentException("The grid is unreadable!");
    } catch (ArrayIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("The grid configuration has an error in it! The created" +
              "grid has a mismatch with the provided rows and columns!");
    } catch (StringIndexOutOfBoundsException e) {
      throw new IllegalArgumentException("You're grid configuration layout is missing cell types" +
              "from a certain column!");
    }
  }
}