package Excepciones.Ejercicio1;

import java.util.ArrayList;
import java.util.Scanner;

public class Ejercicio1 {
	Scanner sc = new Scanner(System.in);
	private static final int REPETICIONES = 6;
	
	ArrayList<Integer> numeros = new ArrayList<>();

	public void pedirNumeros() {
		for(int i = 0; i < REPETICIONES; i++) {
			System.out.print("Dime un número: ");
			int numero = sc.nextInt();
			numeros.add(numero);
		}
	}
	
	public void numeroMayor() {
		int numeroMayor = Integer.MIN_VALUE;
		
		for (Integer i : numeros) {
			if(i > numeroMayor) {
				numeroMayor = i;
			}
		}
		System.out.println("El número mayor es "+numeroMayor);
	}
}
