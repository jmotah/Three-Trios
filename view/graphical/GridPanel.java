package cs3500.threetrios.view.graphical;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.BorderFactory;

/**
 * Represents an individual grid tile on top of the grids grid layout.
 */
public class GridPanel extends JPanel {

  /**
   * Represents the GridPanel class constructor.
   */
  GridPanel() {
    this.setBackground(Color.GRAY);
    setBorder(BorderFactory.createLineBorder(Color.GRAY, 1));
  }
}