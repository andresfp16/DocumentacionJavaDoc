package Excepciones.Ejercicio1;

public class WrongInputValueException extends Exception {

	private static final long serialVersionUID = 1L;

	public WrongInputValueException(String message) {
		super(message);
	}

}
