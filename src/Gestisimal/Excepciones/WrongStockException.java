package Gestisimal.Excepciones;

public class WrongStockException extends Exception {

  private static final long serialVersionUID = 1L;
  /**
   * <p>
   * Excepción que salta al ser mayor el stock de seguridad al stock máximo y que el stock máximo no sea 0
   * 
   */
  public WrongStockException() {
    super();
  }

}
