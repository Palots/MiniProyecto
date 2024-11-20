/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miniproyectocorte3;

/**
 *
 * @author osori
 */
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestorFacturas {
    private static final String ARCHIVO_JSON = "facturas.json";

    // Método para cargar facturas desde un archivo JSON
    public List<Factura> cargarFacturas() {
        List<Factura> facturas = new ArrayList<>();
        File archivo = new File(ARCHIVO_JSON);

        if (!archivo.exists()) {
            return facturas; // Si no existe el archivo, retornamos una lista vacía
        }

        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            StringBuilder contenido = new StringBuilder();
            String linea;

            while ((linea = br.readLine()) != null) {
                contenido.append(linea);
            }

            // Deserialización manual desde el contenido JSON
            String json = contenido.toString();
            if (!json.isEmpty()) {
                String[] bloques = json.split("},\\{"); // Separar por cada factura
                for (String bloque : bloques) {
                    bloque = bloque.replace("[", "").replace("]", ""); // Limpiar bordes de lista
                    bloque = bloque.replace("{", "").replace("}", ""); // Limpiar bordes de objetos

                    String[] campos = bloque.split(",");
                    Map<String, String> datos = new HashMap<>();

                    for (String campo : campos) {
                        String[] claveValor = campo.split(":");
                        datos.put(claveValor[0].replace("\"", "").trim(),
                                  claveValor[1].replace("\"", "").trim());
                    }

                    // Convertir el mapa en un objeto Factura
                    List<Producto> productos = new ArrayList<>();
                    String[] productosJson = datos.get("productosComprados").split(";");

                    for (String productoJson : productosJson) {
                        String[] atributos = productoJson.split("\\|");
                        Producto producto = new Producto(
                                atributos[0], atributos[1], Double.parseDouble(atributos[2]),
                                atributos[3], atributos[4]
                        );
                        productos.add(producto);
                    }

                    Factura factura = new Factura(
                            datos.get("nombreCliente"), datos.get("identificacion"),
                            datos.get("direccion"), productos,
                            Double.parseDouble(datos.get("impuesto")),
                            Double.parseDouble(datos.get("total"))
                    );

                    facturas.add(factura);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return facturas;
    }

    // Método para guardar una factura en el archivo JSON
    public void guardarFactura(Factura factura) {
        List<Factura> facturasExistentes = cargarFacturas();
        facturasExistentes.add(factura);

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ARCHIVO_JSON))) {
            bw.write("[");
            for (int i = 0; i < facturasExistentes.size(); i++) {
                Factura f = facturasExistentes.get(i);
                StringBuilder productosJson = new StringBuilder();

                for (Producto p : f.getProductosComprados()) {
                    productosJson.append(p.getCodigo()).append("|")
                                 .append(p.getNombre()).append("|")
                                 .append(p.getPrecio()).append("|")
                                 .append(p.getCategoria()).append("|")
                                 .append(p.getRutaImagen()).append(";");
                }

                String jsonFactura = String.format(
                        "{\"nombreCliente\":\"%s\",\"identificacion\":\"%s\",\"direccion\":\"%s\",\"productosComprados\":\"%s\",\"impuesto\":%.2f,\"total\":%.2f}",
                        f.getNombreCliente(), f.getIdentificacion(), f.getDireccion(),
                        productosJson.toString(), f.getImpuesto(), f.getTotal()
                );

                bw.write(jsonFactura);
                if (i < facturasExistentes.size() - 1) {
                    bw.write(",");
                }
            }
            bw.write("]");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
