package cs3500.threetrios.controller.filereader;

import org.junit.Assert;
import org.junit.Test;

import java.io.File;

import cs3500.threetrios.model.grid.Grid;
import cs3500.threetrios.model.grid.GridTile;
import cs3500.threetrios.model.CellType;

/**
 * A test class for the GridReader class. Reads and performs various initializations based on
 * the grid configuration file. Verifies everything works properly.
 */
public class GridReaderTests {

  private GridReader createGridReader(File gridConfig) {
    return new GridReader(gridConfig);
  }

  @Test
  public void testGridReaderValid() {
    File gridConfig = new File("/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/" +
            "ThreeTriosBetter/src/cs3500/threetrios/gridconfigs/board_with_holes_two_groups.txt");
    GridReader gridReader = createGridReader(gridConfig);

    GridTile[][] grid = gridReader.readConfiguration();

    Assert.assertEquals(4, grid.length);
    Assert.assertEquals(4, grid[0].length);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameNullGridConfig() {
    GridReader gridReaderNull = createGridReader(null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameGridConfigFileNotFound() {
    File gridConfigNotFound = new File("this_should_not_exist");
    GridReader gridReaderFileNotFound = createGridReader(gridConfigNotFound);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameUnreadableRowAndColumnConfiguration() {
    File unreadableRowColumnConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_unreadable_row_col.txt");
    GridReader gridReaderUnreadableRowColumnConfig = createGridReader(unreadableRowColumnConfig);

    gridReaderUnreadableRowColumnConfig.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameUnreadableGridConfiguration() {
    File unreadableGridConfig = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_unreadable_grid.txt");
    GridReader gridReaderUnreadableGridConfig = createGridReader(unreadableGridConfig);

    gridReaderUnreadableGridConfig.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameDoesGridContainNullField() {
    File nullFieldGrid = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_null_grid_field.txt");
    GridReader gridReaderNullField = createGridReader(nullFieldGrid);

    gridReaderNullField.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameGridConfigErrorTooManyRows() {
    File gridConfigTooManyRows = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/incorrect_grid_config_rows_too_many.txt");
    GridReader gridReaderTooManyRows = createGridReader(gridConfigTooManyRows);

    gridReaderTooManyRows.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameGridConfigErrorTooManyColumns() {
    File gridConfigTooManyColumns = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/incorrect_grid_config_columns_too_many.txt");
    GridReader gridReaderTooManyColumns = createGridReader(gridConfigTooManyColumns);

    gridReaderTooManyColumns.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameGridConfigErrorNotEnoughRows() {
    File gridConfigTooManyRows = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/incorrect_grid_config_rows_too_little.txt");
    GridReader gridReaderTooManyRows = createGridReader(gridConfigTooManyRows);

    gridReaderTooManyRows.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameGridConfigErrorNotEnoughColumns() {
    File gridConfigTooManyColumns = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/incorrect_grid_config_columns_too_little.txt");
    GridReader gridReaderTooManyColumns = createGridReader(gridConfigTooManyColumns);

    gridReaderTooManyColumns.readConfiguration();
  }


  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameGridConfigEmpty() {
    File gridConfigEmpty = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/incorrect_grid_config_empty.txt");
    GridReader gridReaderEmpty = createGridReader(gridConfigEmpty);

    gridReaderEmpty.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameGridConfigNonXOrCForGrid() {
    File gridConfigNoXCForGrid = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/incorrect_grid_config_non_xc_for_grid.txt");
    GridReader gridReaderNoXCForGrid = createGridReader(gridConfigNoXCForGrid);

    gridReaderNoXCForGrid.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameGridConfigStringsForIntRow() {
    File gridConfigStringsForIntRow = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/incorrect_grid_config_strings_for_int_row.txt");
    GridReader gridReaderStringsForIntRow = createGridReader(gridConfigStringsForIntRow);

    gridReaderStringsForIntRow.readConfiguration();
  }

  @Test(expected = IllegalArgumentException.class)
  public void testModelStartGameGridConfigIntegersForGrid() {
    File gridConfigIntegersForGrid = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/ThreeTrios/test/" +
                    "cs3500/threetrios/gridconfigs/incorrect_grid_config_integers_for_grid.txt");
    GridReader gridReaderIntegersForGrid = createGridReader(gridConfigIntegersForGrid);

    gridReaderIntegersForGrid.readConfiguration();
  }

  @Test
  public void testModelStartGameGridConfigLowerCaseLettersForGrid() {
    File gridConfigLowercaseLetters = new File(
            "/Users/julienmotaharian/Desktop/OOD Projects/Group Projects/ThreeTriosBetter/test/" +
                    "cs3500/threetrios/gridconfigs/grid_config_lowercase_letters.txt");
    GridReader gridReaderLowercaseLetters = createGridReader(gridConfigLowercaseLetters);

    Grid[][] grid = gridReaderLowercaseLetters.readConfiguration();

    CellType expectedHole = CellType.HOLE;
    CellType expectedCardCell = CellType.CARD_CELL;

    Assert.assertEquals(grid[0][0].getCellType(), expectedCardCell);
    Assert.assertEquals(grid[0][1].getCellType(), expectedCardCell);
    Assert.assertEquals(grid[0][2].getCellType(), expectedCardCell);
    Assert.assertEquals(grid[1][0].getCellType(), expectedCardCell);
    Assert.assertEquals(grid[1][1].getCellType(), expectedCardCell);
    Assert.assertEquals(grid[1][2].getCellType(), expectedHole);
  }
}