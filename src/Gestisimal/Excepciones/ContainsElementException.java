package Gestisimal.Excepciones;

public class ContainsElementException extends Exception {

  private static final long serialVersionUID = 1L;
  /**
   * <p>
   * Es una excepción que salta cuando ese artículo ya existe
   * 
   * @param string Cadena que se muestra al lanzar la exceción
   */
  public ContainsElementException(String string) {
    super();
  }

}
