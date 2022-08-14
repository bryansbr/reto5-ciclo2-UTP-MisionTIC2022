/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reto5.model.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import reto5.model.vo.InformacionProyectoVo;
import reto5.util.JDBCUtilities;

/**
 *
 * @author Bryan
 */
public class InformacionProyectoDao {
    public List<InformacionProyectoVo> listar() throws SQLException {      
        List<InformacionProyectoVo> respuesta = new ArrayList<>();
        Connection conn = JDBCUtilities.getConnection();
        Statement stm = null;
        ResultSet rs = null;
        
        String consulta = """
                        SELECT
                            p.ID_Proyecto,
                            p.Constructora,
                            p.Numero_Habitaciones,
                            p.Ciudad
                        FROM
                            Proyecto p
                        WHERE
                            p.Clasificacion = 'Casa Campestre'
                            AND Ciudad IN ('Santa Marta', 'Cartagena', 'Barranquilla');
                        """;
        
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(consulta);
            
            while(rs.next()) {
                InformacionProyectoVo vo = new InformacionProyectoVo();
                vo.setIdProyecto(rs.getInt("ID_Proyecto"));
                vo.setConstructora(rs.getString("Constructora"));
                vo.setNumeroHabitaciones(rs.getInt("Numero_Habitaciones"));
                vo.setCiudad(rs.getString("Ciudad"));
                respuesta.add(vo);
            }
        } catch(SQLException e) {
            System.err.print("Error: " + e.getMessage());
        } finally {
            if(rs != null) {
                rs.close();
            }            
            if(stm != null) {
               stm.close();
            } 
            if(conn != null) {
                conn.close();
            }
        }       
        return respuesta;
    }
}
