package Excepciones.CuentaCorriente;

import java.util.ArrayList;

public class CuentaCorriente {
  private long numeroCuenta;
  private double saldo;
  ArrayList<String> movimientos = new ArrayList<>();

  public CuentaCorriente() {
    crearNumeroCuenta();
    saldo = 0;
  }

  public CuentaCorriente(double saldo) {
    crearNumeroCuenta();
    this.saldo = saldo;
  }

  private void crearNumeroCuenta() {
    numeroCuenta = (long)(Math.random()*9999999999L+1);
  }
  public void ingreso(double dinero) {
    saldo += dinero;
    movimientos.add("Ingreso: "+dinero+"€ saldo: "+saldo+"€");
  }

  public void cargo(double dinero) throws BalanceNegativeException {
    exceptionIfBalanceIsNegative(dinero);
    saldo -= dinero;
    movimientos.add("Cargo: "+dinero+"€ saldo: "+saldo+"€");
  }

  public void transferencia(CuentaCorriente cuenta, double dinero) throws BalanceNegativeException {
    exceptionIfBalanceIsNegative(dinero);
    saldo -= dinero;
    cuenta.saldo += dinero;
    movimientos.add("Transferencia de "+dinero+"€ a la cuenta "+cuenta.numeroCuenta+", saldo "+saldo+"€");
    cuenta.movimientos.add("Trasferencia recibida de "+dinero+"€ por la cuenta "+numeroCuenta+", saldo: "+cuenta.saldo+"€");
  }

  private void exceptionIfBalanceIsNegative(double money) throws BalanceNegativeException {
    if(saldo - money < 0) {
      throw new BalanceNegativeException("No se puede hacer la acción. El saldo se quedaría negativo");
    }
  }
  
  public double getSaldo() {
    return saldo;
  }

  public void movimientos() {
    for(String i :movimientos) {
      System.out.println(i);
    }
  }
  
  @Override
  public String toString() {
    return "CuentaCorriente [numeroCuenta=" + String.format("%010d", numeroCuenta) + ", saldo=" + saldo + "€]";
  }



}
