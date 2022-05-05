package Examen.Negocio;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.DOMImplementation;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;
import Examen.Excepciones.ContanctDoesNotExist;
import Examen.Excepciones.IllegalContactException;
import Examen.Excepciones.MaximumNumberOfContactsException;

public class AddressBook {
  //He puesto 5 como maximo para facilitar las pruebas
  private static final int NUM_MAX_CONTACTS = 5;
  int contador = 0;
  private List<Contact> AddressBook = new ArrayList<>();

  public AddressBook() {}

  public AddressBook(String fileName) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document documento = builder.parse(new File(fileName));

      documento.getDocumentElement().normalize();

      NodeList nodes = documento.getElementsByTagName("Contacto");

      for (int i = 0; i < nodes.getLength(); i++) {
        Node node = nodes.item(i);
        Element contacto = (Element) node;

        String nombre =  contacto.getElementsByTagName("Nombre").item(0).getTextContent();
        String correo =  contacto.getElementsByTagName("Correo").item(0).getTextContent();
        String direccion =  contacto.getElementsByTagName("Direccion").item(0).getTextContent();
        String telefono =  contacto.getElementsByTagName("Telefono").item(0).getTextContent();

        AddressBook.add(new Contact(nombre, correo, direccion, telefono));
      }

    } catch (ParserConfigurationException  e) {
      System.err.println("ERROR: "+e.getMessage());
    } catch (IllegalContactException e) {
      System.err.println("ERROR: "+e.getMessage());
    } catch (SAXException e) {
      System.err.println("ERROR: "+e.getMessage());
    } catch (IOException e) {
      System.err.println("ERROR: "+e.getMessage());
    }
  }

  public void add(String nombre, String correo, String direccion, String telefono) throws MaximumNumberOfContactsException, IllegalContactException {
    ComprobarNumContactos();
    AddressBook.add(new Contact(nombre, correo, direccion, telefono));
    contador++;
  }

  public void delete(String nombre) throws ContanctDoesNotExist {
    int accountant = 0;
    boolean checker = false;
    for (Contact cont : AddressBook) {
      if(cont.getNombre().equals(nombre)) {
        checker = true;
      }
      if(!checker) {
        accountant++;
      }
    }
    if(checker) {
      AddressBook.remove(accountant);
    }else {
      throw new ContanctDoesNotExist("El contacto no existe.");
    }
  }

  public Contact searchFor(String nombre) {
    int accountant = 0;
    boolean checker = false;
    for (Contact cont : AddressBook) {
      if(cont.getNombre().equals(nombre)) {
        checker = true;
      }
      if(!checker) {
        accountant++;
      }
    }
    if(checker) {
      return AddressBook.get(accountant);
    }else {
      return null;
    }
  }

  public void exportToXml(String fileName) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      DOMImplementation implementation =builder.getDOMImplementation();

      Document documento = implementation.createDocument(null, "Contactos", null);
      documento.setXmlVersion("1.0");

      Element contactos = documento.createElement("contactos");

      for (Contact art : AddressBook) {
        Element contacto = documento.createElement("Contacto");

        Element nombre = documento.createElement("Nombre");
        Text textNombre = documento.createTextNode(art.getNombre());
        nombre.appendChild(textNombre);
        contacto.appendChild(nombre);

        Element correo = documento.createElement("Correo");
        Text textCorreo = documento.createTextNode(art.getCorreo());
        correo.appendChild(textCorreo);
        contacto.appendChild(correo);

        Element direccion = documento.createElement("Direccion");
        Text textDireccion = documento.createTextNode(""+art.getDireccion());
        direccion.appendChild(textDireccion);
        contacto.appendChild(direccion);

        Element telefono = documento.createElement("Telefono");
        Text textTelefono = documento.createTextNode(""+art.getTelefono());
        telefono.appendChild(textTelefono);
        contacto.appendChild(telefono);

        contactos.appendChild(contacto);

      }
      documento.getDocumentElement().appendChild(contactos);

      Source source = new DOMSource(documento);
      Result result = new StreamResult(new File(fileName));

      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.transform(source, result);
    } catch (ParserConfigurationException | TransformerException e) {
      e.printStackTrace();
    }
  }

  public void moveFromXmlToJava(String fileName) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document documento = builder.parse(new File(fileName));

      documento.getDocumentElement().normalize();

      NodeList nodes = documento.getElementsByTagName("Contacto");

      for (int i = 0; i < nodes.getLength(); i++) {
        Node node = nodes.item(i);
        Element contacto = (Element) node;

        String nombre =  contacto.getElementsByTagName("Nombre").item(0).getTextContent();
        String correo =  contacto.getElementsByTagName("Correo").item(0).getTextContent();
        String direccion =  contacto.getElementsByTagName("Direccion").item(0).getTextContent();
        String telefono =  contacto.getElementsByTagName("Telefono").item(0).getTextContent();

        AddressBook.add(new Contact(nombre, correo, direccion, telefono));
      }

    } catch (ParserConfigurationException  e) {
      System.err.println("ERROR: "+e.getMessage());
    } catch (IllegalContactException e) {
      System.err.println("ERROR: "+e.getMessage());
    } catch (SAXException e) {
      System.err.println("ERROR: "+e.getMessage());
    } catch (IOException e) {
      System.err.println("ERROR: "+e.getMessage());
    }
  }

  private void ComprobarNumContactos() throws MaximumNumberOfContactsException {
    if(contador == NUM_MAX_CONTACTS) {
      throw new MaximumNumberOfContactsException("No se pueden añadir mas contactos. Espacio completo.");
    }
  }

  @Override
  public String toString() {
    return "AddressBook [AddressBook=" + AddressBook + "]";
  }


}
