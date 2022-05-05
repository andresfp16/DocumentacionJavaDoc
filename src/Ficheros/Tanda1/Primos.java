package Ficheros.Tanda1;

import java.io.FileWriter;
import java.io.IOException; 

public class Primos {
	private static final int NUM_PRIMOS = 500;
	public static void main(String[] args) {
		try (var file = new FileWriter("primos.txt")) {
			int numeroPrimo = 0;
			
			for(int i = 0; i < NUM_PRIMOS; i++) {
				if (numeroPrimo%2 != 0 || numeroPrimo == 2) {
					file.write(numeroPrimo+"\n");
					numeroPrimo++;
				}else {
					numeroPrimo++;
				}
			}
		} catch (IOException e) {
			System.err.println("EL fichero no ha sido escrito");
		}
	}
}
