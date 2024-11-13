package cs3500.threetrios.model.strategies;

import java.awt.*;
import java.util.HashMap;

import cs3500.threetrios.model.ReadonlyThreeTriosModel;
import cs3500.threetrios.model.ThreeTriosModel;

public interface Strategies {

  HashMap<Point, Integer> runStrategies();

}
