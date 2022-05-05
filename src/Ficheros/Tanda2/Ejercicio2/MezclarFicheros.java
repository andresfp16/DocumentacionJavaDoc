package Ficheros.Tanda2.Ejercicio2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Escribe un programa que guarde en un fichero el contenido de otros dos ficheros, de tal forma que en el fichero resultante 
 * aparezcan las líneas de los primeros dos ficheros mezcladas, es decir, la primera línea será del primer fichero, la segunda 
 * será del segundo fichero, la tercera será la siguiente del primer fichero, etc.
 * 
 * @author Andres Rodriguez Machado
 *
 */

public class MezclarFicheros {

	public static void main(String[] args) {
		List<String> fichero1 = new ArrayList<>();
		List<String> fichero2 = new ArrayList<>();
		List<String> ficheroFinal = new ArrayList<>();

		try {
			BufferedReader br1 = new BufferedReader(new FileReader("POO\\Ficheros\\Tanda2\\Ejercicio2\\fichero1.txt"));
			BufferedReader br2 = new BufferedReader(new FileReader("POO\\Ficheros\\Tanda2\\Ejercicio2\\fichero2.txt"));
			BufferedWriter bw = new BufferedWriter(new FileWriter("POO\\Ficheros\\Tanda2\\Ejercicio2\\ficheroFinal.txt"));

			fichero1 = addToArray(br1);
			fichero2 = addToArray(br2);

			ficheroFinal = mixFiles(fichero1, fichero2);
			
			for (String s : ficheroFinal) {
				System.out.println(s);
				bw.write(s);
				bw.newLine();
			}
			
			br1.close();
			br2.close();
			bw.close();
		} catch (IOException e) {
			System.err.println("El archivo no existe.");
		} catch (IndexOutOfBoundsException e) {
			System.err.println("El archivo está vacio.");
		}

	}

	private static List<String> addToArray(BufferedReader br1) throws IOException {
		List<String> fichero = new ArrayList<>();
		String linea;
		while ((linea = br1.readLine()) != null) {
			fichero.add(linea);
		}

		return fichero;
	}

	private static List<String> mixFiles(List<String> fichero1, List<String> fichero2) {
		List<String> ficheroFinal = new ArrayList<>();

		for (int i = 0; i < ficheroMayor(fichero1, fichero2); i++) {
			if (fichero1.size()>i) {
				ficheroFinal.add(fichero1.get(i));
			}
			if (fichero2.size()>i) {
				ficheroFinal.add(fichero2.get(i));
			}			
		}
		return ficheroFinal;
	}

	private static int ficheroMayor(List<String> fichero1, List<String> fichero2) {
		if(fichero1.size() > fichero2.size()) {
			return fichero1.size();
		}else {
			return fichero2.size();
		}
	}

}






