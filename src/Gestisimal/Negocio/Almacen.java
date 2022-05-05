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
import Gestisimal.Excepciones.ArticleIllegalErrorArgumentException;

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
 * 
 * @author Andrés Rodríguez Machado
 *
 */
public class Almacen {
  private List<Articulo> stock = new ArrayList<>();

  /**
   * <p>
   * Añade un articulo
   * @param nombre
   * @param marca
   * @param precioDeCompra
   * @param precioDeVenta
   * @param numUnidades
   * @param stockSeguridad
   * @param stockMaximo
   * @throws ArticleIllegalErrorArgumentException
   * @throws ContainsElementException
   */
  public void add(String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades, int stockSeguridad, int stockMaximo) throws ArticleIllegalErrorArgumentException, ContainsElementException {
    nullNameOrBrandException(nombre, marca);
    excepcionIfItemExist(nombre, marca);
    stock.add(new Articulo(nombre, marca, precioDeCompra, precioDeVenta, numUnidades, stockSeguridad, stockMaximo));
  }

  /**
   * <p>
   * Añade un articulo
   * @param nombre
   * @param marca
   * @param precioDeCompra
   * @param precioDeVenta
   * @param numUnidades
   * @param stockSeguridad
   * @throws ArticleIllegalErrorArgumentException
   * @throws ContainsElementException
   */
  public void add(String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades, int stockSeguridad) throws ArticleIllegalErrorArgumentException, ContainsElementException {
    excepcionIfItemExist(nombre, marca);
    stock.add(new Articulo(nombre, marca, precioDeCompra, precioDeVenta, numUnidades, stockSeguridad));

  }

  /**
   * <p>
   * Añade un articulo
   * @param nombre
   * @param marca
   * @param precioDeCompra
   * @param precioDeVenta
   * @param numUnidades
   * @throws ArticleIllegalErrorArgumentException
   * @throws ContainsElementException
   */
  public void add(String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades) throws ArticleIllegalErrorArgumentException, ContainsElementException {
    excepcionIfItemExist(nombre, marca);
    stock.add(new Articulo(nombre, marca, precioDeCompra, precioDeVenta, numUnidades));
  }

  /**
   * <p>
   * Borra un articulo
   * @param name
   * @param brand
   * @throws ArticleDoesNotExist
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
   * @param codigo
   * @throws ArticleDoesNotExist
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
   * @param code
   * @param numerousStocks
   * @throws ArticleDoesNotExist
   * @throws ArticleIllegalErrorArgumentException
   */
  public void addNumbersOfUnits(int code, int numerousStocks) throws ArticleDoesNotExist, ArticleIllegalErrorArgumentException {
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
   * @param code
   * @param numerousStocks
   * @throws ArticleIllegalErrorArgumentException
   * @throws ArticleDoesNotExist
   */
  public void DeleteNumbersOfUnits(int code, int numerousStocks) throws ArticleIllegalErrorArgumentException, ArticleDoesNotExist {
    if(numerousStocks > stock.get(code - 1).getNumUnidades()) {
      throw new ArticleIllegalErrorArgumentException("No hay suficientes existencias en el almacen.");
    }else {
      exceptionIfBalanceIsNegative(code, numerousStocks);
      exceptionIfItemNoExist(code);
      stock.get(code - 1).setNumUnidades(stock.get(code - 1).getNumUnidades() - numerousStocks);
    }
  }

  /**
   * <p>
   * Muestra el artículo seleccionado
   * @param code
   * @throws ArticleDoesNotExist
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
   * @param codigo
   * @param nombre
   * @param marca
   * @param precioDeCompra
   * @param precioDeVenta
   * @param numUnidades
   * @param stockSeguridad
   * @param stockMaximo
   * @throws ContainsElementException
   * @throws ArticleDoesNotExist
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
   * @param codigo
   * @return
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
   * @param archive
   * @param format
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
   * @param archive
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
   * @param filename
   * @throws IOException
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
   * @param archive
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
   * @param filename
   * @throws IOException
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

    } catch (ParserConfigurationException | SAXException | ArticleIllegalErrorArgumentException e) {
      System.err.println(e.getMessage());
    }
  }

  /**
   * 
   * @param nombre
   * @param marca
   * @throws ArticleIllegalErrorArgumentException
   */
  private void nullNameOrBrandException(String nombre, String marca) throws ArticleIllegalErrorArgumentException {
    if(nombre.equals("") || marca.equals("")) {
      throw new ArticleIllegalErrorArgumentException("El nombre o la marca no pueden estar vacios.");
    }

  }

  /**
   * 
   * @param code
   * @throws ArticleDoesNotExist
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
   * 
   * @param code
   * @param numerousStocks
   * @throws ArticleIllegalErrorArgumentException
   */
  private void exceptionIfBalanceIsNegative(int code, int numerousStocks) throws ArticleIllegalErrorArgumentException {
    if(code <= 0 || numerousStocks<= 0) {
      throw new ArticleIllegalErrorArgumentException("No puede haber numeros negativos.");
    }
  }

  /**
   * 
   * @param name
   * @param brand
   * @throws ContainsElementException
   */
  private void excepcionIfItemExist(String name, String brand) throws ContainsElementException {
    if(StockContainsItem(name, brand)) {
      throw new ContainsElementException("El árticulo ya existe");
    }
  }

  /**
   * 
   * @param name
   * @param brand
   * @return
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
