/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package miniproyectocorte3;

/**
 *
 * @author osori
 */
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;


public class FormularioProductos extends JFrame {
    private JTextField txtCodigo, txtNombre, txtPrecio, txtCategoria, txtRutaImagen;
    private JTable tableProductos;
    private DefaultTableModel tableModel;
    private GestorProductos gestorProductos;

    public FormularioProductos() {
        gestorProductos = new GestorProductos();
        setTitle("Gestión de Productos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel lblCodigo = new JLabel("Código:");
        lblCodigo.setBounds(20, 20, 100, 25);
        add(lblCodigo);
        txtCodigo = new JTextField();
        txtCodigo.setBounds(100, 20, 150, 25);
        add(txtCodigo);

        JLabel lblNombre = new JLabel("Nombre:");
        lblNombre.setBounds(20, 60, 100, 25);
        add(lblNombre);
        txtNombre = new JTextField();
        txtNombre.setBounds(100, 60, 150, 25);
        add(txtNombre);

        JLabel lblPrecio = new JLabel("Precio:");
        lblPrecio.setBounds(20, 100, 100, 25);
        add(lblPrecio);
        txtPrecio = new JTextField();
        txtPrecio.setBounds(100, 100, 150, 25);
        add(txtPrecio);

        JLabel lblCategoria = new JLabel("Categoría:");
        lblCategoria.setBounds(20, 140, 100, 25);
        add(lblCategoria);
        txtCategoria = new JTextField();
        txtCategoria.setBounds(100, 140, 150, 25);
        add(txtCategoria);

        JLabel lblRutaImagen = new JLabel("Ruta Imagen:");
        lblRutaImagen.setBounds(20, 180, 100, 25);
        add(lblRutaImagen);
        txtRutaImagen = new JTextField();
        txtRutaImagen.setBounds(100, 180, 150, 25);
        add(txtRutaImagen);

        JButton btnAgregar = new JButton("Agregar");
        btnAgregar.setBounds(20, 220, 100, 25);
        btnAgregar.addActionListener(this::agregarProducto);
        add(btnAgregar);

        JButton btnEliminar = new JButton("Eliminar");
        btnEliminar.setBounds(130, 220, 100, 25);
        btnEliminar.addActionListener(this::eliminarProducto);
        add(btnEliminar);

        JButton btnActualizar = new JButton("Actualizar");
        btnActualizar.setBounds(240, 220, 100, 25);
        btnActualizar.addActionListener(this::actualizarProducto);
        add(btnActualizar);

        tableModel = new DefaultTableModel(new Object[]{"Código", "Nombre", "Precio", "Categoría", "Imagen"}, 0);
        tableProductos = new JTable(tableModel) {
            @Override
            public Class<?> getColumnClass(int column) {
                return column == 4 ? ImageIcon.class : Object.class;
            }
        };

        JScrollPane scrollPane = new JScrollPane(tableProductos);
        scrollPane.setBounds(300, 20, 450, 300);
        add(scrollPane);

        cargarProductos();
    }

    private void agregarProducto(ActionEvent e) {
        String codigo = txtCodigo.getText();
        String nombre = txtNombre.getText();
        double precio = Double.parseDouble(txtPrecio.getText());
        String categoria = txtCategoria.getText();
        String rutaImagen = txtRutaImagen.getText();

        Producto producto = new Producto(codigo, nombre, precio, categoria, rutaImagen);
        List<Producto> productos = gestorProductos.cargarProductos();
        productos.add(producto);
        gestorProductos.guardarProductos(productos);

        cargarProductos();
        limpiarCampos();
    }

    private void eliminarProducto(ActionEvent e) {
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow >= 0) {
            String codigo = (String) tableModel.getValueAt(selectedRow, 0);
            List<Producto> productos = gestorProductos.cargarProductos();
            productos.removeIf(p -> p.getCodigo().equals(codigo));
            gestorProductos.guardarProductos(productos);

            cargarProductos();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para eliminar.");
        }
    }

    private void actualizarProducto(ActionEvent e) {
        int selectedRow = tableProductos.getSelectedRow();
        if (selectedRow >= 0) {
            String codigo = (String) tableModel.getValueAt(selectedRow, 0);
            List<Producto> productos = gestorProductos.cargarProductos();

            for (Producto producto : productos) {
                if (producto.getCodigo().equals(codigo)) {
                    producto.setNombre(txtNombre.getText());
                    producto.setPrecio(Double.parseDouble(txtPrecio.getText()));
                    producto.setCategoria(txtCategoria.getText());
                    producto.setRutaImagen(txtRutaImagen.getText());
                }
            }

            gestorProductos.guardarProductos(productos);
            cargarProductos();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione un producto para actualizar.");
        }
    }

    private void cargarProductos() {
        List<Producto> productos = gestorProductos.cargarProductos();
        tableModel.setRowCount(0);
        for (Producto producto : productos) {
            ImageIcon imagen = new ImageIcon(producto.getRutaImagen());
            tableModel.addRow(new Object[]{producto.getCodigo(), producto.getNombre(), producto.getPrecio(), producto.getCategoria(), imagen});
        }
    }

    private void limpiarCampos() {
        txtCodigo.setText("");
        txtNombre.setText("");
        txtPrecio.setText("");
        txtCategoria.setText("");
        txtRutaImagen.setText("");
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new FormularioProductos().setVisible(true));
    }
}

