package Ficheros.Tanda1.CuentaCorriente;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class CuentaCorriente {
	private long numeroCuenta;
	private int saldo;
	ArrayList<String> movimientos = new ArrayList<>();
	FileWriter fichero = null;
	PrintWriter pw = null;

	public CuentaCorriente() {
		crearNumeroCuenta();
		saldo = 0;
	}

	public CuentaCorriente(int saldo) {
		crearNumeroCuenta();
		this.saldo = saldo;
	}

	private void crearNumeroCuenta() {
		numeroCuenta = (long)(Math.random()*9999999999L+1);
		try {
			fichero = new FileWriter("prueba.txt");
			pw = new PrintWriter(fichero);

            pw.println("Nº de cuenta:  " + numeroCuenta);

		} catch (IOException e) {
			System.err.println(e.getMessage());
		}finally {
            try {
				fichero.close();
			} catch (IOException e) {

			}
		}
		
	}
	public void ingreso(int dinero) {
		saldo += dinero;
		movimientos.add("Ingreso: "+dinero+"€ saldo: "+saldo+"€");
	}

	public void cargo(int dinero) {
		saldo -= dinero;
		movimientos.add("Cargo: "+dinero+"€ saldo: "+saldo+"€");
	}

	public void transferencia(CuentaCorriente cuenta, int dinero) {
		saldo -= dinero;
		cuenta.saldo += dinero;
		movimientos.add("Transferencia de "+dinero+"€ a la cuenta "+cuenta.numeroCuenta+", saldo "+saldo+"€");
		cuenta.movimientos.add("Trasferencia recibida de "+dinero+"€ por la cuenta "+numeroCuenta+", saldo: "+cuenta.saldo+"€");
	}

	public int getSaldo() {
		return saldo;
	}

	@Override
	public String toString() {
		return "CuentaCorriente [numeroCuenta=" + String.format("%010d", numeroCuenta) + ", saldo=" + saldo + "€]";
	}

	public void movimientos() {
		try {
			fichero = new FileWriter("prueba.txt", true);
			pw = new PrintWriter(fichero);

			for(String i :movimientos) {
                pw.println(i);
				System.out.println(i);
			}
		} catch (IOException e) {
			System.err.println(e.getMessage());
		}finally {
            try {
				fichero.close();
			} catch (IOException e) {

			}
		}

	}


}
