package Gestisimal.Negocio;

import Gestisimal.Excepciones.ArticleIllegalErrorArgumentException;
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
 * 
 * @author Andrés Rodríguez Machado
 *
 */
public class Articulo {
  private static int contador = 1;

  String nombre, marca; 
  double precioDeCompra, precioDeVenta; 
  int numUnidades, codigo, stockSeguridad, stokMaximo;

  /**
   * 
   * @param nombre
   * @param marca
   * @param precioDeCompra
   * @param precioDeVenta
   * @param numUnidades
   * @param stockSeguridad
   * @param stockMaximo
   * @throws ArticleIllegalErrorArgumentException
   */
  public Articulo(String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades, int stockSeguridad, int stockMaximo) throws ArticleIllegalErrorArgumentException {
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
   * 
   * @param nombre
   * @param marca
   * @param precioDeCompra
   * @param precioDeVenta
   * @param numUnidades
   * @param stockSeguridad
   * @throws ArticleIllegalErrorArgumentException
   */
  public Articulo(String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades, int stockSeguridad) throws ArticleIllegalErrorArgumentException {
    this(nombre, marca, precioDeCompra, precioDeVenta, numUnidades, stockSeguridad, 0);
  }

  /**
   * 
   * @param nombre
   * @param marca
   * @param precioDeCompra
   * @param precioDeVenta
   * @param numUnidades
   * @throws ArticleIllegalErrorArgumentException
   */
  public Articulo(String nombre, String marca, double precioDeCompra, double precioDeVenta, int numUnidades) throws ArticleIllegalErrorArgumentException {
    this(nombre, marca, precioDeCompra, precioDeVenta, numUnidades, 0, 0);
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
   * @param precioDeVenta
   * @param precioDeCompra
   * @param numUnidades
   * @param stockSeguridad
   * @param stockMaximo
   * @throws ArticleIllegalErrorArgumentException
   */
  private void balanceIsNegativeException(double precioDeVenta, double precioDeCompra, int numUnidades, int stockSeguridad, int stockMaximo) throws ArticleIllegalErrorArgumentException {
    if(numUnidades <= 0 || precioDeCompra <= 0 || precioDeVenta <=0 || stockSeguridad<=0 || stockMaximo < 0) {
      throw new ArticleIllegalErrorArgumentException("No puede haber valores negativos.");
    }
  }

  /**
   * 
   * @param stockSeguridad
   * @param stockMaximo
   * @throws ArticleIllegalErrorArgumentException
   */
  private void WrongStockException(int stockSeguridad, int stockMaximo) throws ArticleIllegalErrorArgumentException {
    if(stockSeguridad > stockMaximo && stockMaximo != 0) {
      throw new ArticleIllegalErrorArgumentException("El stock máximo no puede ser menor al de seguridad.");
    }
  }

  /**
   * 
   * @return
   */
  public int getCodigo() {
    return codigo;
  }

  /**
   * 
   * @return
   */
  public String getNombre() {
    return nombre;
  }

  /**
   * 
   * @param nombre
   */
  void setNombre(String nombre) {
    this.nombre = nombre;
  }

  /**
   * 
   * @return
   */
  public String getMarca() {
    return marca;
  }

  /**
   * 
   * @param marca
   */
  void setMarca(String marca) {
    this.marca = marca;
  }

  /**
   * 
   * @return
   */
  public double getPrecioDeCompra() {
    return precioDeCompra;
  }

  /**
   * 
   * @param precioDeCompra
   */
  void setPrecioDeCompra(double precioDeCompra) {
    this.precioDeCompra = precioDeCompra;
  }

  /**
   * 
   * @return
   */
  public double getPrecioDeVenta() {
    return precioDeVenta;
  }

  /**
   * 
   * @param precioDeVenta
   */
  void setPrecioDeVenta(double precioDeVenta) {
    this.precioDeVenta = precioDeVenta;
  }

  /**
   * 
   * @return
   */
  public int getNumUnidades() {
    return numUnidades;
  }

  /**
   * 
   * @param numUnidades
   */
  void setNumUnidades(int numUnidades) {
    this.numUnidades = numUnidades;
  }

  /**
   * 
   * @return
   */
  public int getStockSeguridad() {
    return stockSeguridad;
  }

  /**
   * 
   * @param stockSeguridad
   */
  void setStockSeguridad(int stockSeguridad) {
    this.stockSeguridad = stockSeguridad;
  }

  /**
   * 
   * @return
   */
  public int getStokMaximo() {
    return stokMaximo;
  }

  /**
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
