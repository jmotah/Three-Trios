package cs3500.threetrios.controller.filereader;

/**
 * An interface for reading configuration data and converting it to given object T.
 *
 * @param <T> the given object to return
 */
public interface ConfigurationReader<T> {
  T readConfiguration();
}
