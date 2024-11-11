package cs3500.threetrios.view.graphical;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.*;

import cs3500.threetrios.cards.CardCompass;
import cs3500.threetrios.cards.PlayingCard;
import cs3500.threetrios.grid.GridTile;
import cs3500.threetrios.model.ReadonlyThreeTriosModel;

public class GridLayoutPanel extends JPanel {

  private final int rows;
  private final int columns;
  private final GraphicalView graphicalView;
  private final ReadonlyThreeTriosModel model;
  private GridTile[][] grid;

  private GridPanel currentClickedGridCell;

  GridLayoutPanel(int rows, int columns, ReadonlyThreeTriosModel model, GraphicalView graphicalView) {
    currentClickedGridCell = null;

    this.rows = rows;
    this.columns = columns;
    this.model = model;
    this.grid = model.getGrid();
    this.graphicalView = graphicalView;

    this.setLayout(new GridLayout(rows, columns));
    this.setPreferredSize(new Dimension(400, 400));

    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        GridPanel panelTile = new GridPanel();

        GridTile tile = grid[row][column];

        switch (tile.getCellType()) {
          case HOLE:
            panelTile.setBackground(Color.GRAY);
            break;
          case CARD_CELL:
            panelTile.setBackground(Color.YELLOW);
            panelTile.addMouseListener(graphicalView);
            break;
          case PLAYER_CELL:
            panelTile.setBackground(tile.getWhichPlayersTile().getPlayersColor().getColor());
            panelTile.setLayout(new BorderLayout());

            PlayingCard card = tile.getPlayingCard();

            JLabel north =
                    new JLabel(card.getValueAsString(CardCompass.NORTH_VALUE));
            JLabel south =
                    new JLabel(card.getValueAsString(CardCompass.SOUTH_VALUE));
            JLabel east =
                    new JLabel(card.getValueAsString(CardCompass.EAST_VALUE));
            JLabel west =
                    new JLabel(card.getValueAsString(CardCompass.WEST_VALUE));

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
        }
        this.add(panelTile);
      }
    }
  }

  public void updateComponents() {
    this.removeAll();
    this.revalidate();
    this.repaint();

    grid = model.getGrid();

    for (int row = 0; row < rows; row++) {
      for (int column = 0; column < columns; column++) {
        GridPanel panelTile = new GridPanel();

        GridTile tile = grid[row][column];

        switch (tile.getCellType()) {
          case HOLE:
            panelTile.setBackground(Color.GRAY);
            break;
          case CARD_CELL:
            panelTile.setBackground(Color.YELLOW);
            panelTile.addMouseListener(graphicalView);
            break;
          case PLAYER_CELL:
            panelTile.setBackground(tile.getWhichPlayersTile().getPlayersColor().getColor());
            panelTile.setLayout(new BorderLayout());

            PlayingCard card = tile.getPlayingCard();

            JLabel north =
                    new JLabel(card.getValueAsString(CardCompass.NORTH_VALUE));
            JLabel south =
                    new JLabel(card.getValueAsString(CardCompass.SOUTH_VALUE));
            JLabel east =
                    new JLabel(card.getValueAsString(CardCompass.EAST_VALUE));
            JLabel west =
                    new JLabel(card.getValueAsString(CardCompass.WEST_VALUE));

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
        }
        this.add(panelTile);
      }
    }
    this.revalidate();
    this.repaint();
  }
}