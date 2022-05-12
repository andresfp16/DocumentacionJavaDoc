package Gestisimal.Excepciones;

public class ArticleArgumentException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * Excepción que salta cuando ocurre un error al trabajar con un artículo
   * @param message Cadena que se muestra al lanzar la excepción
   */
  public ArticleArgumentException(String message) {
    super(message);
  }


}
