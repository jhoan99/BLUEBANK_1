package models;

public class Users {
    String Nombre;
    String Valor;
    String NumeroCuenta;

    public Users() {
    }

    public Users(String nombre, String valor, String numeroCuenta) {
        Nombre = nombre;
        Valor = valor;
        NumeroCuenta = numeroCuenta;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getValor() {
        return Valor;
    }

    public void setValor(String valor) {
        Valor = valor;
    }

    public String getNumeroCuenta() {
        return NumeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        NumeroCuenta = numeroCuenta;
    }
}
