package Excepciones.CuentaCorriente;

public class BalanceNegativeException extends Exception {

  private static final long serialVersionUID = 1L;

  public BalanceNegativeException(String message) {
    super(message);
  }
}
