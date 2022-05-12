package Gestisimal.Excepciones;

public class ArticleDoesNotExist extends Exception {
  private static final long serialVersionUID = 1L;

  public ArticleDoesNotExist() {
    super();
  }
 /**
  * Excepción que salta al no existir el artículo
  * @param string Cadena que se muestra al lanzar la excepción
  */
  public ArticleDoesNotExist(String string) {
    super(string);
  }
}
