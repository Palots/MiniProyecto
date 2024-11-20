/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miniproyectocorte3;

/**
 *
 * @author osori
 */
import org.w3c.dom.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GestorProductos {
    private static final String ARCHIVO_XML = "productos.xml";

    public List<Producto> cargarProductos() {
        List<Producto> productos = new ArrayList<>();
        try {
            File archivo = new File(ARCHIVO_XML);
            if (!archivo.exists()) {
                return productos;
            }

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(archivo);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("producto");
            for (int i = 0; i < nList.getLength(); i++) {
                Node nodo = nList.item(i);
                if (nodo.getNodeType() == Node.ELEMENT_NODE) {
                    Element elemento = (Element) nodo;
                    Producto producto = new Producto(
                            elemento.getElementsByTagName("codigo").item(0).getTextContent(),
                            elemento.getElementsByTagName("nombre").item(0).getTextContent(),
                            Double.parseDouble(elemento.getElementsByTagName("precio").item(0).getTextContent()),
                            elemento.getElementsByTagName("categoria").item(0).getTextContent(),
                            elemento.getElementsByTagName("imagen").item(0).getTextContent()
                    );
                    productos.add(producto);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productos;
    }

    public void guardarProductos(List<Producto> productos) {
        try {
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.newDocument();

            Element rootElement = doc.createElement("productos");
            doc.appendChild(rootElement);

            for (Producto producto : productos) {
                Element nuevoProducto = doc.createElement("producto");

                Element codigo = doc.createElement("codigo");
                codigo.appendChild(doc.createTextNode(producto.getCodigo()));
                nuevoProducto.appendChild(codigo);

                Element nombre = doc.createElement("nombre");
                nombre.appendChild(doc.createTextNode(producto.getNombre()));
                nuevoProducto.appendChild(nombre);

                Element precio = doc.createElement("precio");
                precio.appendChild(doc.createTextNode(String.valueOf(producto.getPrecio())));
                nuevoProducto.appendChild(precio);

                Element categoria = doc.createElement("categoria");
                categoria.appendChild(doc.createTextNode(producto.getCategoria()));
                nuevoProducto.appendChild(categoria);

                Element imagen = doc.createElement("imagen");
                imagen.appendChild(doc.createTextNode(producto.getRutaImagen()));
                nuevoProducto.appendChild(imagen);

                rootElement.appendChild(nuevoProducto);
            }

            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(ARCHIVO_XML));
            transformer.transform(source, result);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


