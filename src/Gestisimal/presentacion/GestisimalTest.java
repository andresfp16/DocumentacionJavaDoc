package Gestisimal.presentacion;

import Gestisimal.Excepciones.ArticleDoesNotExist;
import Gestisimal.Excepciones.ArticleIllegalErrorArgumentException;
import Gestisimal.Excepciones.ContainsElementException;
import Gestisimal.Negocio.Almacen;
import Util.Menu;
import static Util.Util.*;

import java.io.IOException;

/**
 * <p>
 * Clase TestAlmacén, donde se realiza la comunicación con el usuario (mostrar menú y recuperar opción del menú, mostrar errores, listar) 
 * y se manipula el almacén.  Debes organizarla en métodos que deleguen en la clase Almacén (listar, añadir, eliminar... al menos uno por cada 
 * una de las opciones del menú).
 * 
 * @author Andrés Rodríguez Machado
 *
 */
public class GestisimalTest {

	private static Almacen warehouse = new Almacen();

	public static void main(String[] args) {
		Menu menu = createMenu();    

		int option;
		do {
			option = menu.choose();
			switch (option) {
			case 1 -> showWarehouse();
			case 2 -> addArticle();
			case 3 -> addWarehouse();
			case 4 -> addArticlesXml();
			case 5 -> removeArticle();
			case 6 -> modifyArticle();
			case 7 -> increaseStock();
			case 8 -> decreaseStock();
			case 9 -> exportWithJsonOrXML();
			}
		} while (option != menu.lastOption());

		System.out.println("¡Hasta la próxima! ;-)");
	}

	/**
	 * 
	 * @return
	 */
	private static Menu createMenu() {
		return new Menu("\nMenú de opciones",
				"Listado", "Alta de artículo", "Alta mediante archivo json","Alta mediante archivo xml","Baja de artículo", "Modificación de artículo",
				"Entrada de mercancía", "Salida de mercancía", "Codificar co Json o XML","Terminar");
	}

	private static void showWarehouse() {
		System.out.println(warehouse);
	}

	private static void addArticle() {
		try {
			warehouse.add(readStr("Nombre de artículo a dar de alta"), readStr("Marca"), 
					readDouble("Precio compra"), readDouble("Precio venta"), readInt("Unidades"),
					readInt("Stock de seguridad"), readInt("Stock máximo"));
		} 
		catch (ContainsElementException e) {
			System.err.println("ERROR: El artículo que has intentado añadir ya existe en el almacén.");
		} 
		catch (ArticleIllegalErrorArgumentException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}

	private static void addWarehouse() {
		String archive = readStr("Dime el nombre del archivo al que exportar");
		String format = archive.substring(archive.indexOf(".") + 1);
		if(correctFormat(format)) {
			try {
				warehouse.load(archive);
			} catch (IOException e) {
				System.err.println("Archivo erroneo.");
			}
		}
	}

	private static void addArticlesXml() {
		String archive = readStr("Dime el nombre del archivo al que exportar");
		String format = archive.substring(archive.indexOf(".") + 1);
		if(correctFormat(format)) {
			try {
				warehouse.loadFromXml(archive);
			} catch (IOException e) {
				System.err.println("Archivo erroneo.");
			}
		}
	}

	private static void removeArticle() {
		try {
			warehouse.delete(readInt("Código de artículo a dar de baja"));
		} 
		catch (ArticleDoesNotExist e) {
			printCodeError();
		} 
	}

	private static void modifyArticle() {
		try {
			warehouse.changeItem(readInt("Código de artículo a modificar"), readStr("Nombre"), readStr("Marca"),
					readDouble("Precio compra"), readDouble("Precio venta"), readInt("Unidades"),
					readInt("Stock de seguridad"), readInt("Stock máximo")); 
		} 
		catch (ContainsElementException e) {
			System.err.println("ERROR: El nombre y marca de artículo ya existen en el almacén.");
		} catch (ArticleDoesNotExist e) {
			System.err.println("ERROR: "+e.getMessage());
		} 
	}

	private static void increaseStock() {
		try {
			warehouse.addNumbersOfUnits(readInt("Código del artículo a incrementar stock"), 
					readInt("Unidades"));
		} 
		catch (ArticleDoesNotExist e) {
			printCodeError();
		}
		catch (ArticleIllegalErrorArgumentException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}

	private static void decreaseStock() {
		try {
			warehouse.DeleteNumbersOfUnits(readInt("Código de artículo de decrementar stock"), 
					readInt("Unidades"));
		} 
		catch (ArticleDoesNotExist e) {
			printCodeError();
		}
		catch (ArticleIllegalErrorArgumentException e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}

	private static void exportWithJsonOrXML() {
		String archive = readStr("Dime el nombre del archivo al que exportar");
		String format = archive.substring(archive.indexOf(".") + 1);

		if(correctFormat(format)) {
			warehouse.changeFormatToJsonOrXml(archive, format);
		}
		return;
	}

	/**
	 * <p>
	 * Comprueba si el formato del archivo es correcto
	 * @param format
	 * @return
	 */
	private static boolean correctFormat(String format) {
		if(format.equals("json") || format.equals("xml")) {
			return true;
		}else {
			System.err.println("El formato del archivo es erroneo");
			return false;
		}
	}

	private static void printCodeError() {
		System.err.println("ERROR: Ese código no corresponde a ningún artículo.");
	}
}
