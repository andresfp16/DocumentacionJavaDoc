package Ficheros.Tanda2.Ejercicio1;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class EscribirPalabras {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		
		try (FileWriter fw = new FileWriter("POO\\Ficheros\\Tanda2\\Ejercicio1\\palabras.txt")){
			String palabra ="Nada";
			
			while (palabra != "") {
				System.out.print("Dime una palabra(pulsa enter para salir): ");
				palabra = sc.nextLine();
				fw.write(palabra+"\n");
			}
			fw.close();
			System.out.println("Las palabras se han introducido correctamente.");
		} catch (IOException e) {
			System.err.println("Error al crear el fichero.");
		}

	}

}
