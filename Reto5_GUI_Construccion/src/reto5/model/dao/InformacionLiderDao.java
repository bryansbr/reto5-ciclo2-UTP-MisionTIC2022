/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reto5.model.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import reto5.model.vo.InformacionLiderVo;
import reto5.util.JDBCUtilities;

/**
 *
 * @author Bryan
 */
public class InformacionLiderDao {
    public List<InformacionLiderVo> listar() throws SQLException {      
        List<InformacionLiderVo> respuesta = new ArrayList<>();
        Connection conn = JDBCUtilities.getConnection();
        Statement stm = null;
        ResultSet rs = null;
        
        String consulta = """
                        SELECT
                            l.ID_Lider,
                            l.Nombre,
                            l.Primer_Apellido,
                            l.Ciudad_Residencia
                        FROM
                            Lider l
                        ORDER BY
                            Ciudad_Residencia;
                        """;
        
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(consulta);
            
            while(rs.next()) {
                InformacionLiderVo vo = new InformacionLiderVo();
                vo.setIdLider(rs.getInt("ID_Lider"));
                vo.setNombre(rs.getString("Nombre"));
                vo.setPrimerApellido(rs.getString("Primer_Apellido"));
                vo.setCiudadResidencia(rs.getString("Ciudad_Residencia"));
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
