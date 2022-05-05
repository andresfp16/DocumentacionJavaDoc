package Excepciones.Ejercicio1;

public class Ejercicio1Test {

	public static void main(String[] args) throws WrongInputValueException {
		Ejercicio1 prueba = new Ejercicio1();
		
		try {
			prueba.pedirNumeros();
			prueba.numeroMayor();
		} catch (Exception e) {
			throw new WrongInputValueException("El número introducido es erroneo.");
		}
	}

}
