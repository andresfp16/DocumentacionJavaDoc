package Gestisimal.Excepciones;

public class ArticleDoesNotExist extends Exception {
  private static final long serialVersionUID = 1L;

  public ArticleDoesNotExist() {
    super();
  }
 /**
  * 
  * @param string
  */
  public ArticleDoesNotExist(String string) {
    super(string);
  }
}
