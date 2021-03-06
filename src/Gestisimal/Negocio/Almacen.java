package Gestisimal.Negocio;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
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

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Gestisimal.Excepciones.ArticleDoesNotExist;
import Gestisimal.Excepciones.ContainsElementException;
import Gestisimal.Excepciones.ArticleArgumentException;

/**
 * Elaborar una aplicación para el control de artículos de un almacén.
 * 
 * <p>
 * Clase Almacén que realice el alta, baja, modificación, entrada de mercancía (incrementa
 * unidades), salida de mercancía (decrementa unidades).
 * <ul>
 * <li>
 * El estado será un ArrayList de artículos (pero la clase no será un ArrayList) 
 * <li>
 * Su comportamiento será: añadir artículos (no puede haber dos artículos con el mismo nombre y marca), eliminar
 * artículos, incrementar las existencias de un artículo (se delega en la clase Artículo),
 * decrementar las existencias de un artículo (nunca por debajo de cero, se delega en la clase
 * Artículo), devolver un artículo (para mostrarlo). Para listar el almacén podría devolverse una
 * cadena con todos los artículos del almacén (toString).
 * </ul>
 * @author Andrés Rodríguez Machado
 *
 */
public class Almacen {
  private List<Articulo> stock = new ArrayList<>();

  /**
   * <p>
   * Añade un articulo
   * @param nombre Es el nombre del artículo
   * @param marca Es la marca del artículo
   * @param precioDeCompra Es el precio por el que han comprado el artículo
   * @param precioDeVenta Es el precio por el que lo van a vender
   * @param numUnidades Es el número de unidades de ese artículo
   * @param stockSeguridad Es el número de artículos mínimos para que no haya problemas de abastecimiento
   * @param stockMaximo Es el número máximo que puede tener de un artículo
   * @throws ArticleArgumentException Es una excepción que salta cuando el nombre o la marca son erróneos
   * @throws ContainsElementException Es una excepción que salta cuando ese artículo ya existe
   */
  public void add(String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades, int stockSeguridad, int stockMaximo) throws ArticleArgumentException, ContainsElementException {
    nullNameOrBrandException(nombre, marca);
    excepcionIfItemExist(nombre, marca);
    stock.add(new Articulo(nombre, marca, precioDeCompra, precioDeVenta, numUnidades, stockSeguridad, stockMaximo));
  }

  /**
   * <p>
   * Añade un articulo
   * @param nombre Es el nombre del artículo
   * @param marca Es la marca del artículo
   * @param precioDeCompra Es el precio por el que han comprado el artículo
   * @param precioDeVenta Es el precio por el que lo van a vender
   * @param numUnidades Es el número de unidades de ese artículo
   * @param stockSeguridad Es el número de artículos mínimos para que no haya problemas de abastecimiento
   * @throws ArticleArgumentException Es una excepción que salta cuando el nombre o la marca son erróneos
   * @throws ContainsElementException Es una excepción que salta cuando ese artículo ya existe
   */
  public void add(String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades, int stockSeguridad) throws ArticleArgumentException, ContainsElementException {
    excepcionIfItemExist(nombre, marca);
    stock.add(new Articulo(nombre, marca, precioDeCompra, precioDeVenta, numUnidades, stockSeguridad));

  }

  /**
   * <p>
   * Añade un articulo
   * @param nombre Es el nombre del artículo
   * @param marca Es la marca del artículo
   * @param precioDeCompra Es el precio por el que han comprado el artículo
   * @param precioDeVenta Es el precio por el que lo van a vender
   * @param numUnidades Es el número de unidades de ese artículo
   * @throws ArticleArgumentException Es una excepción que salta cuando el nombre o la marca son erróneos
   * @throws ContainsElementException Es una excepción que salta cuando ese artículo ya existe
   */
  public void add(String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades) throws ArticleArgumentException, ContainsElementException {
    excepcionIfItemExist(nombre, marca);
    stock.add(new Articulo(nombre, marca, precioDeCompra, precioDeVenta, numUnidades));
  }

  /**
   * <p>
   * Borra un articulo
   * @param name Es el nombre del artículo a borrar
   * @param brand Es la marca del artículo a borrar
   * @throws ArticleDoesNotExist Es una excepción que salta cuando no existe el artículo
   */
  public void delete(String name, String brand) throws ArticleDoesNotExist {
    int accountant = 0;
    boolean checker = false;
    for (Articulo art : stock) {
      if(art.getNombre().equals(name) && art.getMarca().equals(brand)) {
        checker = true;
      }
      if(!checker) {
        accountant++;
      }
    }
    if(checker) {
      stock.remove(accountant);
    }else {
      throw new ArticleDoesNotExist();
    }
  }

  /**
   * <p>
   * Borra un articulo
   * @param codigo Es el código del artículo a borrar
   * @throws ArticleDoesNotExist Es una excepción que salta cuando no existe el artículo
   */
  public void delete(int codigo) throws ArticleDoesNotExist {
    boolean checker = false;
    for (Articulo art : stock) {
      if(art.getCodigo() == codigo) {
        checker = true;
      }
    }
    if(checker) {
      stock.remove(codigo - 1);
    }else {
      throw new ArticleDoesNotExist();
    }
  }

  /**
   * <p>
   * Añade número de unidades al artículo 
   * @param code Es el código del artículo al que le vas a añadir unidades
   * @param numerousStocks Es el número de unidades que le vas a sumar al artículo
   * @throws ArticleDoesNotExist Es una excepción que salta cuando no existe el artículo
   * @throws ArticleArgumentException Es una excepción que salta cuando el número introducido es negativo
   */
  public void addNumbersOfUnits(int code, int numerousStocks) throws ArticleDoesNotExist, ArticleArgumentException {
    exceptionIfBalanceIsNegative(code, numerousStocks);
    int accountant = 0;
    for (Articulo art : stock) {
      if(art.getCodigo() == code) {
        stock.get(accountant).setNumUnidades(stock.get(accountant).getNumUnidades() + numerousStocks);
        return;
      }
      accountant++;
    }
    throw new ArticleDoesNotExist();
  }

  /**
   * <p>
   * Borra número de unidades al artículo 
   * @param code Es el código del artículo al que le vas a restar unidades
   * @param numerousStocks Es el número de unidades que le vas a restar al artículo
   * @throws ArticleDoesNotExist Es una excepción que salta cuando no existe el artículo
   * @throws ArticleArgumentException Es una excepción que salta cuando el número introducido es negativo
   */
  public void DeleteNumbersOfUnits(int code, int numerousStocks) throws ArticleArgumentException, ArticleDoesNotExist {
    if(numerousStocks > stock.get(code - 1).getNumUnidades()) {
      throw new ArticleArgumentException("No hay suficientes existencias en el almacen.");
    }else {
      exceptionIfBalanceIsNegative(code, numerousStocks);
      exceptionIfItemNoExist(code);
      stock.get(code - 1).setNumUnidades(stock.get(code - 1).getNumUnidades() - numerousStocks);
    }
  }

  /**
   * <p>
   * Muestra el artículo seleccionado
   * @param code Es el código del artículo a mostrar
   * @throws ArticleDoesNotExist Es una excepción que salta cuando no existe el artículo
   */
  public void showItem(int code) throws ArticleDoesNotExist {
    boolean checker = false;
    for (Articulo art : stock) {
      if(art.getCodigo() == code) {
        System.out.println(art.toString());
        checker = true;
      }
    }
    if(!checker) {
      throw new ArticleDoesNotExist();
    }
  }

  /**
   * <p>
   * Cambia los datos del artículo que quieras
   * @param codigo Es el código del artículo que vas a modificar
   * @param nombre Es el nuevo nombre que va a tener tu artículo
   * @param marca Es la nueva marca que va a tener tu artículo
   * @param precioDeCompra Es el nuevo precio de compra que va a tener tu artículo
   * @param precioDeVenta Es el nuevo precio de venta que va a tener tu artículo
   * @param numUnidades Es el nuevo número de unidades que va a tener tu artículo
   * @param stockSeguridad Es el nuevo stock de seguridad que va a tener tu artículo
   * @param stockMaximo Es el nuevo stock máximo que va a tener tu artículo
   * @throws ContainsElementException Es una excepción que salta cuando el nuevoo nombre y marca son iguales a las de otro artículo
   * @throws ArticleDoesNotExist Es una excepción que salta cuando no existe el artículo
   */
  public void changeItem(int codigo, String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades, int stockSeguridad, int stockMaximo) throws ContainsElementException, ArticleDoesNotExist {
    for (Articulo art : stock) {
      if (art.getNombre().equals(nombre) && art.getMarca().equals(marca) && !containsCod(codigo)) {
        throw new ContainsElementException("Ya existe un articulo con ese nombre y marca");
      }
      if (codigo == art.getCodigo()) {
        art.setNombre(nombre);
        art.setMarca(marca);
        art.setNumUnidades(numUnidades);
        art.setPrecioDeCompra(precioDeCompra);
        art.setPrecioDeVenta(precioDeVenta);
        art.setStockSeguridad(stockSeguridad);
        art.setStokMaximo(stockMaximo);
        return;
      }
    }
    throw new ArticleDoesNotExist("El código " + codigo + " no existe en el almacén");
  }

  /**
   * <p>
   * Te devuelve verdadero si el código introducido existe o falso si no existe
   * @param codigo Es el código del artículo a buscar
   * @return Devuelve true si existe ese artículo y false si no
   */
  private boolean containsCod(int codigo) {
    for (Articulo art : stock) {
      if(art.getCodigo() == codigo) {
        return true;
      }
    }
    return false;	
  }

  /**
   * <p>
   * Dependiendo de la extensión del archivo que le des, te da tus artículos en formato json o xml
   * @param archive Es el archivo que te pasa tu usuario para pasarlo a xml o a json
   * @param format Es el formato al que se va a pasar el archivo, xml o json.
   */
  public void changeFormatToJsonOrXml(String archive, String format) {
    if(format.equals("json")) {
      changeFormatToJson(archive);
    }
    changeFormatToXml(archive);
  }

  /**
   * <p>
   * Te da tus artículos en formato json
   * @param archive ES el archivo a cambiar a json
   */
  private void changeFormatToJson(String archive) {
    try (BufferedWriter bw = new BufferedWriter(new FileWriter(archive))){
      Gson gson = new GsonBuilder().setPrettyPrinting().create();
      String stringGson = gson.toJson(this);      			
      bw.write(stringGson);
      bw.close();
    } catch (IOException e) {
      e.printStackTrace();
    }	      
  }	

  /**
   * Te coge un archivo json y te mete los artículos que tenga dentro
   * @param filename Es un archivo json que introduce el usuario para introducirlos al programa
   * @throws IOException Es una excepción que se da al trabajar con estos archivos
   */
  public void load(String filename) throws IOException {

    BufferedReader lector = new BufferedReader(new FileReader(filename));
    StringBuilder cadena = new StringBuilder();
    String line = null;

    while ((line = lector.readLine()) != null) {
      cadena.append(line);

    }
    lector.close();
    Almacen data = new Gson().fromJson(cadena.toString(), Almacen.class);
    this.stock = data.stock;
  }

  /**
   * <p>
   * Te introduce tus artículos a un archivo xml
   * @param archive Es el nombre del archivo para transformarlo a xml
   */
  private void changeFormatToXml(String archive) {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      DOMImplementation implementation =builder.getDOMImplementation();

      Document documento = implementation.createDocument(null, "almacen", null);
      documento.setXmlVersion("1.0");

      Element almacen = documento.createElement("Almacen");

      for (Articulo art : stock) {
        Element articulo = documento.createElement("Articulo");

        Element nombre = documento.createElement("Nombre");
        Text textNombre = documento.createTextNode(art.getNombre());
        nombre.appendChild(textNombre);
        articulo.appendChild(nombre);

        Element marca = documento.createElement("Marca");
        Text textMarca = documento.createTextNode(art.getMarca());
        marca.appendChild(textMarca);
        articulo.appendChild(marca);

        Element precioDeCompra = documento.createElement("PrecioDeCompra");
        Text textPrecioDeCompra = documento.createTextNode(""+art.getPrecioDeCompra());
        precioDeCompra.appendChild(textPrecioDeCompra);
        articulo.appendChild(precioDeCompra);

        Element precioDeVenta = documento.createElement("PrecioDeVenta");
        Text textPrecioDeVenta = documento.createTextNode(""+art.getPrecioDeVenta());
        precioDeVenta.appendChild(textPrecioDeVenta);
        articulo.appendChild(precioDeVenta);

        Element numUnidades = documento.createElement("NumeroDeUnidades");
        Text textNumUnidades = documento.createTextNode(""+art.getNumUnidades());
        numUnidades.appendChild(textNumUnidades);
        articulo.appendChild(numUnidades);

        Element stockSeguridad = documento.createElement("StockDeSeguridad");
        Text textStockSeguridad = documento.createTextNode(""+art.getStockSeguridad());
        stockSeguridad.appendChild(textStockSeguridad);
        articulo.appendChild(stockSeguridad);

        Element stockMaximo = documento.createElement("StockMaximo");
        Text textStockMaximo = documento.createTextNode(""+art.getStokMaximo());
        stockMaximo.appendChild(textStockMaximo);
        articulo.appendChild(stockMaximo);

        almacen.appendChild(articulo);

      }
      documento.getDocumentElement().appendChild(almacen);

      Source source = new DOMSource(documento);
      Result result = new StreamResult(new File(archive));

      Transformer transformer = TransformerFactory.newInstance().newTransformer();
      transformer.transform(source, result);
    } catch (ParserConfigurationException | TransformerException e) {
      e.printStackTrace();
    }
  }

  /**
   * <p>
   * Te introduce artículos a tu almacén a través de un archivo xml
   * 
   * @param filename Es el nombre del archivo formato xml para meterlo en el almacén
   * @throws IOException Es una excepción que se da al trabajar con estos archivos
   */
  public void loadFromXml(String filename) throws IOException {
    try {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document documento = builder.parse(new File(filename));

      documento.getDocumentElement().normalize();

      NodeList nodes = documento.getElementsByTagName("Artículo");

      for (int i = 0; i < nodes.getLength(); i++) {
        Node node = nodes.item(i);
        Element articulo = (Element) node;

        String nombre =  articulo.getElementsByTagName("Nombre").item(0).getTextContent();
        String marca =  articulo.getElementsByTagName("Marca").item(0).getTextContent();
        double precioDeCompra = Double.parseDouble(articulo.getElementsByTagName("Preciodecompra").item(0).getTextContent());
        double precioDeVenta = Double.parseDouble(articulo.getElementsByTagName("Preciodeventa").item(0).getTextContent());
        int numUnidades = Integer.parseInt(articulo.getElementsByTagName("Numerodeunidades").item(0).getTextContent());
        int stockSeguridad = Integer.parseInt(articulo.getElementsByTagName("Stockseguridad").item(0).getTextContent());
        int stockMaximo = Integer.parseInt(articulo.getElementsByTagName("Stockmaximo").item(0).getTextContent());

        stock.add(new Articulo(nombre, marca, precioDeCompra, precioDeVenta, numUnidades, stockSeguridad, stockMaximo));
      }

    } catch (ParserConfigurationException | SAXException | ArticleArgumentException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * <p>
   * Comprueba que el nombre y la marca que ha introducido el usuario no son nulas
   * 
   * @param nombre Es el nombre del artículo a comprobar
   * @param marca Es la marca del artículo a comprobar
   * @throws ArticleArgumentException Es la excepción que salta en caso de ser nulos
   */
  private void nullNameOrBrandException(String nombre, String marca) throws ArticleArgumentException {
    if(nombre.equals("") || marca.equals("")) {
      throw new ArticleArgumentException("El nombre o la marca no pueden estar vacios.");
    }

  }

  /**
   * <p>
   * Comprueba que el artículo exista
   * 
   * @param code Es el código a comprobar
   * @throws ArticleDoesNotExist Es la excepción que salta en caso de no existir el artículo
   */
  private void exceptionIfItemNoExist(int code) throws ArticleDoesNotExist {
    for (Articulo art : stock) {
      if(art.getCodigo() == code) {
        return;
      }
    }
    throw new ArticleDoesNotExist();
  }

  /**
   * <p>
   * Comprueba que no haya valores negativos
   * 
   * @param code El código del archivo
   * @param numerousStocks EL numero de stock
   * @throws ArticleArgumentException excepción que salta cuando hay algún error en el artículo
   */
  private void exceptionIfBalanceIsNegative(int code, int numerousStocks) throws ArticleArgumentException {
    if(code <= 0 || numerousStocks<= 0) {
      throw new ArticleArgumentException("No puede haber numeros negativos.");
    }
  }

  /**
   * <p>
   * Excepción si existe el item
   * 
   * @param name Nombre del item
   * @param brand Marca del item
   * @throws ContainsElementException excepción que salta cuando existe el item
   */
  private void excepcionIfItemExist(String name, String brand) throws ContainsElementException {
    if(StockContainsItem(name, brand)) {
      throw new ContainsElementException("El árticulo ya existe");
    }
  }

  /**
   * <p>
   * Comprueba que exista el item
   * 
   * @param name Nombre del item
   * @param brand Marca del item
   * @return Devuelve true si existe o false si no
   */
  private boolean StockContainsItem(String name, String brand) {
    for (Articulo art : stock) {
      if(art.getNombre().equals(name) && art.getMarca().equals(brand)) {
        return true;
      }
    }
    return false;
  }

  @Override
  public String toString() {
    return stock + "";
  }

}
