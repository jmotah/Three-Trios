package cs3500.threetrios.providers.model.enumadapters;

/**
 * An interface for converting from one enum to another.
 *
 * @param <T> the type of enum we are converting to
 */
public interface ConvertEnums<T> {

  /**
   * Converts from one enum to another.
   *
   * @return the enum equivalent of that we converted from
   */
  T convertEnums();

}
