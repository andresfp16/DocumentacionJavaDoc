package Examen.Negocio;

import Examen.Excepciones.IllegalContactException;

public class Contact {
  String nombre, correo, direccion, telefono;

  public Contact(String nombre, String correo, String telefono) throws IllegalContactException {
    validarContacto(nombre, correo, telefono);
    this.nombre = nombre;
    this.correo = correo;
    this.telefono = telefono;
  }

  public Contact(String nombre, String correo,String direccion, String telefono) throws IllegalContactException {
    validarContacto(nombre, correo, telefono);
    this.nombre = nombre;
    this.correo = correo;
    this.direccion = direccion;
    this.telefono = telefono;
  }


  private void validarContacto(String nombre, String correo, String telefono) throws IllegalContactException {
    if(nombre.equals("") || !CorreoValido(correo) || !TelefonoValidotelefono(telefono)) {
      throw new IllegalContactException("El contacto es incorrecto.");
    }
  }

  private boolean TelefonoValidotelefono(String telefono) {
    if(telefono.length() != 9) {
      return false;
    }
    String digito1 = telefono.substring(0, 1);
    if(digito1.equals("6") || digito1.equals("7") || digito1.equals("9")) {
      return true;
    }
    return false;
  }

  private boolean CorreoValido(String correo) {
    for (int i = 0; i < correo.length(); i++) {
      if(i != 0 && i!= correo.length() - 1) {
        if(correo.charAt(i) == '@') {
          if(correo.charAt(i + 1) == '.') {
            return true;
          }
        }
      }
    }
    return false;
  }

  // Getters and Setters
  public String getCorreo() {
    return correo;
  }

  public void setCorreo(String correo) {
    this.correo = correo;
  }

  public String getDireccion() {
    return direccion;
  }

  public void setDireccion(String direccion) {
    this.direccion = direccion;
  }

  public String getTelefono() {
    return telefono;
  }

  public void setTelefono(String telefono) {
    this.telefono = telefono;
  }

  public String getNombre() {
    return nombre;
  }

  @Override
  public String toString() {
    return "Contact [nombre=" + nombre + ", correo=" + correo + ", direccion=" + direccion
        + ", telefono=" + telefono + "]";
  }




}
