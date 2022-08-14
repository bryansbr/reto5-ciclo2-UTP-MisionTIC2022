/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reto5.view;

import java.awt.*;
import java.sql.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.util.List;
import reto5.model.vo.InformacionLiderVo;
import reto5.model.vo.InformacionComprasVo;
import reto5.model.vo.InformacionProyectoVo;
import reto5.controller.ReportesController;

/**
 *
 * @author Bryan
 */
public final class ReportesView extends JFrame implements ActionListener {
    // Elementos para el diseño y visualización de los informes.
    private JMenuBar barraMenu;
    private JMenu menu;
    private JMenuItem primerInforme, segundoInforme, tercerInforme;
    private JTable tabla;
    private DefaultTableModel modelo;
    private JLabel labelTitulo, labelConsulta;
    private static ReportesController controladorReportes; // Controlador de reportes.
    
    // Constructor
    public ReportesView() {
        controladorReportes = new ReportesController();
        menu(); // Se llama al menu para mostrarlo en pantalla.
        etiquetaTitulos();
        etiquetaConsultas();
        visualizarTabla();
    }
    
    // VISUALIZACIÓN DE LA INFORMACIÓN GRÁFICAMENTE.
    public void menu() {
        barraMenu = new JMenuBar(); // Barra de menu (superior).
        setJMenuBar(barraMenu);
        menu = new JMenu("LISTA DE INFORMES");
        barraMenu.add(menu);
        
        // ITEMS
        // Items que se añaden al menú. Al dar clic aquí se desplegarán las diferentes tablas con la información.
        primerInforme = new JMenuItem("Primer Informe");
        menu.add(primerInforme);
        primerInforme.addActionListener(this);
        
        segundoInforme = new JMenuItem("Segundo Informe");
        menu.add(segundoInforme);
        segundoInforme.addActionListener(this);
        
        tercerInforme = new JMenuItem("Tercer Informe");
        menu.add(tercerInforme);
        tercerInforme.addActionListener(this);
    }
    
    public void etiquetaTitulos() {
        labelTitulo = new JLabel("INFORMES - MINISTERIO DE VIVIENDA");
        labelTitulo.setPreferredSize(new Dimension(500, 30));
        labelTitulo.setFont(new Font("Arial", Font.BOLD, 20));
        add(labelTitulo);
    }
    
    public void etiquetaConsultas() {
        labelConsulta = new JLabel();
        labelConsulta.setPreferredSize(new Dimension(500, 50));
        labelConsulta.setFont(new Font("Arial", Font.BOLD, 14));
        add(labelConsulta);        
    }
    
    public void visualizarTabla() {
        tabla = new JTable();
        tabla.setPreferredScrollableViewportSize(new Dimension(500, 300)); // Tabla escroleable
        add(tabla);
        JScrollPane panel = new JScrollPane(tabla);
        add(panel);
        
    }
    
    // VISUALIZACIÓN DE LA INFORMACIÓN POR CONSOLA Y GRÁFICAMENTE
    private String repitaCaracter(Character caracter, Integer veces) {
        String respuesta = "";
        
        for (int i = 0; i < veces; i++) {
            respuesta += caracter;
        }
        return respuesta;
    }
    // INFORME 1: Información de los líderes ordenada por ciudad de residencia de forma alfabética.
    public void infoLideres() {
        System.out.println(repitaCaracter('=', 7) + " INFORMACIÓN DE LÍDERES "
                + repitaCaracter('=', 7));
        System.out.println(String.format("%-7s %-7s %-7s %-2s", "ID Lider", "Nombre", "Primer Apellido", "Ciudad Residencia"));
        System.out.println(repitaCaracter('-', 41));
        
        // Imprimir en pantalla la información de los líderes
        try {
            List<InformacionLiderVo> lideres = controladorReportes.listarInformacionLider();
            // Modelo para visualizar los datos.
            modelo = new DefaultTableModel();
            modelo.addColumn("ID Lider");
            modelo.addColumn("Nombre");
            modelo.addColumn("Apellido");
            modelo.addColumn("Ciudad Residencia");
            
            for(InformacionLiderVo lider : lideres) {
                // Objeto de tipo arrray para mostrar las filas.
                Object[] fila = new Object[4];
                fila[0] = lider.getIdLider();
                fila[1] = lider.getNombre();
                fila[2] = lider.getPrimerApellido();
                fila[3] = lider.getCiudadResidencia();
                modelo.addRow(fila); // Se añaden las filas a la tabla.
                
                System.out.println(lider.darFormato()); // Muestra información por consola.
            }
            tabla.setModel(modelo);// Se setea el modelo cada vez que se hace una consulta. 
            modelo.fireTableDataChanged(); // Actualización del modelo de la tabla.
            
        } catch(SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // INFORME 2: Información de los proyectos "Casa Campestre" en las ciudades de Santa Marta, Cartagena y Barranquilla.
    public void infoProyectos() {
        System.out.println(repitaCaracter('=', 7) + " INFORMACIÓN DE PROYECTOS "
                + repitaCaracter('=', 7));
        
        System.out.println(String.format("%-7s %-7s %7s %-2s", "ID Proyecto", "Constructora", "Numero Habitaciones", "Ciudad"));
        System.out.println(repitaCaracter('-', 60));

        // Imprimir en pantalla la información del total adeudado
        try {
            List<InformacionProyectoVo> proyectos = controladorReportes.listarInformacionProyecto();
            // Modelo para visualizar los datos.
            modelo = new DefaultTableModel();
            modelo.addColumn("ID Proyecto");
            modelo.addColumn("Constructora");
            modelo.addColumn("N° Habitaciones");
            modelo.addColumn("Ciudad");

            for(InformacionProyectoVo proyecto : proyectos) {
                // Objeto de tipo arrray para mostrar las filas.
                Object[] fila = new Object[4];
                fila[0] = proyecto.getIdProyecto();
                fila[1] = proyecto.getConstructora();
                fila[2] = proyecto.getNumeroHabitaciones();
                fila[3] = proyecto.getCiudad();
                modelo.addRow(fila); // Se añaden las filas a la tabla.
                
                System.out.println(proyecto.darFormato()); // Muestra la información por consola.
            }
            tabla.setModel(modelo);// Se setea el modelo cada vez que se hace una consulta. 
            modelo.fireTableDataChanged(); // Actualización del modelo de la tabla.         
        } catch(SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    // INFORME 3: Información de las compras realizadas por los proyectos con el proveedor "Homecenter" para la ciudad de Salento.
    public void infoCompras() {
        System.out.println(repitaCaracter('=', 7) + " LISTADO DE COMPRAS "
                + repitaCaracter('=', 7));
        
        //System.out.println(String.format("%3d %-25s %-20s", "ID Compra", "Constructora", "Banco Vinculado"));
        System.out.println(repitaCaracter('-', 60));

        // Imprimir en pantalla la información del proyecto
        try {
            List<InformacionComprasVo> compras = controladorReportes.listarInformacionCompras();
            // Modelo para visualizar los datos.
            modelo = new DefaultTableModel();
            modelo.addColumn("ID Compra");
            modelo.addColumn("Constructora");
            modelo.addColumn("Banco Vinculado");          

            for(InformacionComprasVo compra : compras) {
                // Objeto de tipo arrray para mostrar las filas.
                Object[] fila = new Object[3];
                fila[0] = compra.getIdCompra();
                fila[1] = compra.getConstructora();
                fila[2] = compra.getBancoVinculado();

                modelo.addRow(fila); // Se añaden las filas a la tabla.                
                System.out.println(compra.darFormato());
            }
            tabla.setModel(modelo);// Se setea el modelo cada vez que se hace una consulta. 
            modelo.fireTableDataChanged(); // Actualización del modelo de la tabla.  
            
        } catch(SQLException e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == primerInforme) {
            infoLideres();
            labelConsulta.setText("Informe de lideres");
        }
        if (e.getSource() == segundoInforme) {
            infoProyectos();
            labelConsulta.setText("Informe de proyectos");
        }
        if (e.getSource() == tercerInforme) {
            infoCompras();
            labelConsulta.setText("Informe de compras");
        }
    }
}