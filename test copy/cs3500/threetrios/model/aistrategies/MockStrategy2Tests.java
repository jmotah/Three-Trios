package cs3500.threetrios.model.aistrategies;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.Point;
import java.util.HashMap;

import cs3500.threetrios.model.ThreeTriosModel;

/**
 * Tests the expected corner is played to when given the mock model MockModelEmpty class.
 */
public class MockStrategy2Tests {

  ThreeTriosModel model;

  Strategies strategy;

  @Before
  public void setup() {
    model = new MockModelEmptyCorners();

    strategy = new Strategy2(model);
  }

  @Test
  public void testEmptyCornersAllElseFilled() {
    HashMap<Point, Integer> expected = new HashMap<>();
    expected.put(new Point(0, 0), 0);

    Assert.assertEquals(expected, strategy.runStrategy());
  }
}