/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miniproyectocorte3;

/**
 *
 * @author osori
 */
import java.util.List;

public class Factura {

    private String nombreCliente;
    private String identificacion;
    private String direccion;
    private List<Producto> productosComprados;
    private double impuesto;
    private double total;

    public Factura(String nombreCliente, String identificacion, String direccion, List<Producto> productosComprados, double impuesto, double total) {
        this.nombreCliente = nombreCliente;
        this.identificacion = identificacion;
        this.direccion = direccion;
        this.productosComprados = productosComprados;
        this.impuesto = impuesto;
        this.total = total;
    }

    public String getNombreCliente() {
        return nombreCliente;
    }

    public void setNombreCliente(String nombreCliente) {
        this.nombreCliente = nombreCliente;
    }

    public String getIdentificacion() {
        return identificacion;
    }

    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public List<Producto> getProductosComprados() {
        return productosComprados;
    }

    public void setProductosComprados(List<Producto> productosComprados) {
        this.productosComprados = productosComprados;
    }

    public double getImpuesto() {
        return impuesto;
    }

    public void setImpuesto(double impuesto) {
        this.impuesto = impuesto;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }
}
