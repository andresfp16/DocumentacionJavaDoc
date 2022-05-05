package Gestisimal.Excepciones;

public class ArticleIllegalErrorArgumentException extends Exception {
  private static final long serialVersionUID = 1L;

  /**
   * 
   * @param string
   */
  public ArticleIllegalErrorArgumentException(String message) {
    super(message);
  }


}
