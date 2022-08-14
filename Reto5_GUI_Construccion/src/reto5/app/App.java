/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package reto5.app;

import java.awt.FlowLayout;
import javax.swing.JFrame;
import reto5.view.ReportesView;

/**
 *
 * @author Bryan
 */
public class App {
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // VISUALIZACIÓN DE LA INTERFAZ
        var reportesView = new ReportesView(); // Objeto de tipo ReportesView.
        
        reportesView.setLayout(new FlowLayout()); // Se agregan los componentes uno al lado del otro (horizontalmente).
        reportesView.setTitle("Reto 5 (UTP) - Ministerio de Vivienda");
        reportesView.setSize(700, 600); // Tamaño de la ventana.
        reportesView.setVisible(true); // Ventana visible.
        reportesView.setResizable(false); // Redimensionamiento de la ventana.
        reportesView.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        reportesView.setLocationRelativeTo(null); // Ventana en el centro de la pantalla.
    }
}
