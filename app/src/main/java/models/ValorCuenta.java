package models;

public class ValorCuenta {
    String valor;
    String nombre;
    String numeroCuenta;

    public ValorCuenta() {
    }

    public ValorCuenta(String valor, String nombre, String numeroCuenta) {
        this.valor = valor;
        this.nombre = nombre;
        this.numeroCuenta = numeroCuenta;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getNumeroCuenta() {
        return numeroCuenta;
    }

    public void setNumeroCuenta(String numeroCuenta) {
        this.numeroCuenta = numeroCuenta;
    }
}
