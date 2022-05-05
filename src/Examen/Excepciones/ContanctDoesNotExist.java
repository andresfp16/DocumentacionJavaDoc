package Examen.Excepciones;

public class ContanctDoesNotExist extends Exception {
  private static final long serialVersionUID = 1L;

  public ContanctDoesNotExist(String message) {
    super(message);
  }  
}
