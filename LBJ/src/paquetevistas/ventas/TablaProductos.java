/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package paquetevistas.ventas;

import java.util.ArrayList;
import java.util.List;
import javax.swing.table.AbstractTableModel;
import modelos.Producto;

/**
 *
 * @author Andres
 */
class TablaProductos extends AbstractTableModel {

        List<ProductoC> productos = new ArrayList<ProductoC>();
        String[] titles = {"Codigo", "Producto", "Cantidad"};

        public TablaProductos() {
            
        }
        public void refresh() {
            this.fireTableChanged(null);
        }
        
        public int getRowCount() {
            return productos.size();
        }

        public int getColumnCount() {
            return 3;
        }

        public Object getValueAt(int rowIndex, int columnIndex) {
            ProductoC productoC = productos.get(rowIndex);
            switch (columnIndex) {
                case 0:
                    return productoC.getIdProducto();
                case 1:
                    return productoC.getNombre();
                case 2:
                    return productoC.getCantidad();
            }
            return null;
        }

        public String getColumnName(int col) {
            return titles[col];
        }
        public void modify(Producto p, float cantidad, float precio) {
            ProductoC productoC = find(p);
            if (productoC != null) {
                productoC.setCantidad(cantidad);
                productoC.setPrecioUnd(precio);
            } else {
                productoC = new ProductoC(p,cantidad,precio);
                productos.add(productoC);
            }
            refresh();
        }
        public ProductoC find(Producto p) {
            int j = productos.size();
            for (int i=0; i<j; i++) {
                ProductoC productoC = productos.get(i);
                if (productoC.getIdProducto() == p.getIdproducto()) {
                    return productoC;
                }
            }
            return null;
        }
        public float getMontoTotal() {
            float total = 0;
            int j = productos.size();
            for (int i=0; i<j; i++) {
                ProductoC productoC = productos.get(i);
                total = total + productoC.getCantidad() * productoC.getPrecioUnd();
            }
            return total;
        }
        public void cotizar() {
            int j = productos.size();
            for (int i=0; i<j; i++) {
                ProductoC productoC = productos.get(i);
                productoC.cotizar(2);
            }
            refresh();
        }
        public List<ProductoC> asList() {
            return productos;
        }
        
    }
