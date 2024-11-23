package cs3500.threetrios.controller.filereader;

/**
 * An interface for reading configuration data and converting it to given object T.
 *
 * @param <T> the given object to return
 */
public interface ConfigurationReader<T> {
  /**
   * Reads the configuration class and gathers a list of playing cards if it is a card reader.
   * Reads the grid configuration file to determine number of rows, columns, and grid layout if it
   * is a grid reader.
   *
   * @return the object being retrieved
   */
  T readConfiguration();
}
