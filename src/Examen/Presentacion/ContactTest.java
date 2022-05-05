package Examen.Presentacion;

import static Util.Util.readStr;
import Examen.Excepciones.ContanctDoesNotExist;
import Examen.Excepciones.IllegalContactException;
import Examen.Excepciones.MaximumNumberOfContactsException;
import Examen.Negocio.AddressBook;
import Util.Menu;

public class ContactTest {

  private static AddressBook AddressBook = new AddressBook("prueba.xml");

  public static void main(String[] args) {
    Menu menu = createMenu();    

    int option;
    do {
      option = menu.choose();
      switch (option) {
        case 1 -> showAddressBook();
        case 2 -> addContact();
        case 3 -> removeContact();
        case 4 -> searchContact();
        case 5 -> exportWithXML();
        case 6 -> exportToJava();
      }
    } while (option != menu.lastOption());

    System.out.println("¡Hasta la próxima! ;-)");
  }

  private static Menu createMenu() {
    return new Menu("\nMenú de opciones",
        "Listado de contactos", "Alta de contacto", "Baja de contacto", 
        "Buscar un contacto", "Exportar a fichero XML","Recibir contactos desde fichero xml", "Terminar");
  }

  private static void showAddressBook() {
    System.out.println(AddressBook);
  }

  private static void addContact() {
    try {
      AddressBook.add(readStr("Nombre del contacto"), readStr("Dime el correo"), 
          readStr("Dime la direccion"), readStr("Dime el numero de telefono"));
    } catch (MaximumNumberOfContactsException | IllegalContactException e) {
      System.err.println(e.getMessage());
    }
  }

  private static void removeContact() {
    try {
      AddressBook.delete(readStr("Dime el nombre del contacto"));
    } catch (ContanctDoesNotExist e) {
      System.err.println(e.getMessage());    
    }
  }

  private static void searchContact() {
    System.out.println(AddressBook.searchFor(readStr("Dime el nombre del contacto")));
  }

  private static void exportWithXML() {
    String archive = readStr("Dime el nombre del archivo al que exportar");
    String format = archive.substring(archive.indexOf(".") + 1);

    if(correctFormat(format)) {
      AddressBook.exportToXml(archive);
    }
  }

  private static void exportToJava() {
    String archive = readStr("Dime el nombre del archivo para coger");
    String format = archive.substring(archive.indexOf(".") + 1);
    if(correctFormat(format)) {
            AddressBook.moveFromXmlToJava(archive);

    }
  }
  
  private static boolean correctFormat(String format) {
    if(format.equals("xml")) {
      return true;
    }else {
      System.err.println("El formato del archivo es erroneo");
      return false;
    }
  }
}
