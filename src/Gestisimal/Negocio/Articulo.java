package Gestisimal.Negocio;

import Gestisimal.Excepciones.ArticleArgumentException;
/**
 * <p>
 * Clase Artículo que representa a los artículos del almacén. 
 * <ul>
 * <li>
 * Su estado será: código, nombre, marca, precio de compra, precio de venta, número de unidades (nunca negativas), stock de seguridad y 
 * stock máximo (si no proporcionamos los valores de stock valen cero, igualmente si no proporcionamos el del stock máximo).
 * <li>
 * Como comportamiento: Consideramos que el código va a generarse de forma automática en el constructor, así no puede haber dos artículos 
 * con el mismo código. Esto implica que no puede modificarse el código de un artículo, sí el resto de las propiedades. 
 * Podremos mostrar el artículo, por lo que necesito una representación del artículo en forma de cadena (toString). 
 * </ul>
 * @author Andrés Rodríguez Machado
 *
 */
public class Articulo {
  private static int contador = 1;

  String nombre, marca; 
  double precioDeCompra, precioDeVenta; 
  int numUnidades, codigo, stockSeguridad, stokMaximo;

  /**
   * <p>
   * Crea un artículo con sus respectivos parámetros
   *  
   * @param nombre Nombre del artículo
   * @param marca Marca del artículo
   * @param precioDeCompra Precio de compra del artículo
   * @param precioDeVenta Precio de venta del artículo
   * @param numUnidades número de unidades del artículo
   * @param stockSeguridad Stock de seguridad del artículo
   * @param stockMaximo Stock máximo del artículo
   * @throws ArticleArgumentException Es una excepción que salta cuando hay algún error al crear un artículo
   */
  public Articulo(String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades, int stockSeguridad, int stockMaximo) throws ArticleArgumentException {
    nullNameOrBrandException(nombre, marca);
    balanceIsNegativeException(precioDeVenta, precioDeCompra, numUnidades,stockSeguridad, stockMaximo);
    WrongStockException(stockSeguridad, stockMaximo);
    codigo = contador++;
    this.nombre = nombre;
    this.marca = marca;
    this.precioDeCompra = precioDeCompra;
    this.precioDeVenta = precioDeVenta;
    this.numUnidades = numUnidades;
    this.stockSeguridad = stockSeguridad;
    this.stokMaximo = stockMaximo;
  }

  /**
   * <p>
   * Crea un artículo con sus respectivos parámetros
   *  
   * @param nombre Nombre del artículo
   * @param marca Marca del artículo
   * @param precioDeCompra Precio de compra del artículo
   * @param precioDeVenta Precio de venta del artículo
   * @param numUnidades número de unidades del artículo
   * @param stockSeguridad Stock de seguridad del artículo
   * @throws ArticleArgumentException Es una excepción que salta cuando hay algún error al crear un artículo
   */
  public Articulo(String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades, int stockSeguridad) throws ArticleArgumentException {
    this(nombre, marca, precioDeCompra, precioDeVenta, numUnidades, stockSeguridad, 0);
  }

  /**
   * <p>
   * Crea un artículo con sus respectivos parámetros
   *  
   * @param nombre Nombre del artículo
   * @param marca Marca del artículo
   * @param precioDeCompra Precio de compra del artículo
   * @param precioDeVenta Precio de venta del artículo
   * @param numUnidades número de unidades del artículo
   * @throws ArticleArgumentException Es una excepción que salta cuando hay algún error al crear un artículo
   */
  public Articulo(String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades) throws ArticleArgumentException {
    this(nombre, marca, precioDeCompra, precioDeVenta, numUnidades, 0, 0);
  }

  /**
   * <p>
   * Excepción que salta cuando el nombre o la marca son nulas
   * 
   * @param nombre Nombre del artículo
   * @param marca Marca del artículo
   * @throws ArticleArgumentException Es una excepción que salta cuando hay algún error al crear un artículo
   */
  private void nullNameOrBrandException(String nombre, String marca) throws ArticleArgumentException {
    if(nombre.equals("") || marca.equals("")) {
      throw new ArticleArgumentException("El nombre o la marca no pueden estar vacios.");
    }

  }

  /**
   * Excepción que salta cuando hay algún número negativo
   * @param precioDeVenta Precio de venta del artículo
   * @param precioDeCompra Precio de compra del artículo
   * @param numUnidades Número de unidades del artículo
   * @param stockSeguridad Stock de seguridad del artículo
   * @param stockMaximo Stock máximo del artículo
   * @throws ArticleArgumentException Es una excepción que salta cuando hay algún error al crear un artículo
   */
  private void balanceIsNegativeException(double precioDeVenta, double precioDeCompra, int numUnidades, int stockSeguridad, int stockMaximo) throws ArticleArgumentException {
    if(numUnidades <= 0 || precioDeCompra <= 0 || precioDeVenta <=0 || stockSeguridad<=0 || stockMaximo < 0) {
      throw new ArticleArgumentException("No puede haber valores negativos.");
    }
  }

  /**
   * <p>
   * Excepción que salta cuando el stock de seguridad es mayor al stock máximo y no es 0 el stock máximo
   * 
   * @param stockSeguridad Stock de seguridad del artículo
   * @param stockMaximo Stock máximo del artículo
   * @throws ArticleArgumentException Es una excepción que salta cuando hay algún error al crear un artículo
   */
  private void WrongStockException(int stockSeguridad, int stockMaximo) throws ArticleArgumentException {
    if(stockSeguridad > stockMaximo && stockMaximo != 0) {
      throw new ArticleArgumentException("El stock máximo no puede ser menor al de seguridad.");
    }
  }

  /**
   * 
   * @return Devuelve el código del artículo 
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * 
   * @return Devuelve el nombre del artículo 
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * <p>
   * Modifica el nombre del artículo
   * 
   * @param nombre Nuevo nombre del artículo
   */
  void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * 
   * @return Devuelve la marca del artículo 
   */
  public String getMarca() {
    return marca;
  }

  /**
   * <p>
   * Modifica la marca del artículo
   * 
   * @param marca Nueva marca del artículo
   */
  void setMarca(String marca) {
    this.marca = marca;
  }

  /**
   * 
   * @return Devuelve el precio de compra
   */
  public double getPrecioDeCompra() {
    return precioDeCompra;
  }

  /**
   * <p>
   * Modifica el precio de compra del artículo
   * 
   * @param precioDeCompra Nuevo precio de compra
   */
  void setPrecioDeCompra(double precioDeCompra) {
    this.precioDeCompra = precioDeCompra;
  }

  /**
   * 
   * @return Devuelve el precio de venta
   */
  public double getPrecioDeVenta() {
    return precioDeVenta;
  }

  /**
   * <p>
   * Modifica el precio de venta
   * 
   * @param precioDeVenta Nuevo precio de venta del artículo
   */
  void setPrecioDeVenta(double precioDeVenta) {
    this.precioDeVenta = precioDeVenta;
  }

  /**
   * 
   * @return Devuelve el número de unidades
   */
  public int getNumUnidades() {
    return numUnidades;
  }

  /**
   * <p>
   * Modifica el número de unidades del artículo
   * 
   * @param numUnidades
   */
  void setNumUnidades(int numUnidades) {
    this.numUnidades = numUnidades;
  }

  /**
   * 
   * @return Devuelve el stock de seguridad
   */
  public int getStockSeguridad() {
    return stockSeguridad;
  }

  /**
   * <p>
   * Modifica el stock de seguridad del artículo
   * 
   * @param stockSeguridad Nuevo stock de seguridad del artículo
   */
  void setStockSeguridad(int stockSeguridad) {
    this.stockSeguridad = stockSeguridad;
  }

  /**
   * 
   * @return Devuelve el stock máximo del artículo
   */
  public int getStokMaximo() {
    return stokMaximo;
  }

  /**<p>
   * Modifica el stock máximo del artículo
   * 
   * @param stokMaximo
   */
  void setStokMaximo(int stokMaximo) {
    this.stokMaximo = stokMaximo;
  }

  @Override
  public String toString() {
    return "nombre=" + nombre + ", marca=" + marca + ", precioDeCompra=" + precioDeCompra
        + ", precioDeVenta=" + precioDeVenta + ", numUnidades=" + numUnidades + ", codigo=" + codigo
        + ", stockSeguridad=" + stockSeguridad + ", stokMaximo=" + stokMaximo + "\n";
  }

}
