/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reto5.app;

import java.awt.FlowLayout;
import java.sql.SQLException;
import javax.swing.JFrame;
import reto5.view.ReportesView;

/**
 *
 * @author Bryan
 */
public class App {
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        // VISUALIZACIÓN DE LA INTERFAZ
        var reportesView = new ReportesView();
        reportesView.setLayout(new FlowLayout()); // Se agregan los componentes uno al lado del otro (horizontalmente).
        reportesView.setTitle("Reto 5 (UTP) - Ministerio de Vivienda");
        reportesView.setSize(700, 600); // Tamaño de la ventana.
        reportesView.setVisible(true); // Ventana visible.
        reportesView.setResizable(true); // Redimensionamiento de la ventana.
        reportesView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reportesView.setLocationRelativeTo(null); // Ventana en el centro de la pantalla.
        
        
        // Informe 1: Información de los líderes ordenada por ciudad de residencia de forma alfabética.
        
        reportesView.infoLideres();
        
        // Informe 2: Información de los proyectos "Casa Campestre" en las ciudades de Santa Marta, Cartagena y Barranquilla.
        reportesView.infoProyectos();
        
        // Informe 3: Información de las compras realizadas por los proyectos con el proveedor "Homecenter" para la ciudad de Salento.
        reportesView.infoCompras();
    }
}
