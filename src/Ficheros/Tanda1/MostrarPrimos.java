package Ficheros.Tanda1;

import java.io.BufferedReader;
import java.io.FileReader; 
import java.io.IOException; 

public class MostrarPrimos { 
	public static void muestraContenido(String archivo) throws  IOException { 
    	String cadena; 
        FileReader f = new FileReader(archivo); 
        BufferedReader b = new BufferedReader(f); 
        while((cadena = b.readLine())!=null) { 
        	System.out.println(cadena); 
        } 
        b.close(); 
	} 
    
    public static void main(String[] args) throws IOException {
    	try {
        	muestraContenido("primos.txt"); 
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
    }
}
