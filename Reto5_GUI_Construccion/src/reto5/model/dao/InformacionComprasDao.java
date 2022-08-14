/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reto5.model.dao;

import java.sql.*;
import java.util.List;
import java.util.ArrayList;
import reto5.model.vo.InformacionComprasVo;
import reto5.util.JDBCUtilities;

/**
 *
 * @author Bryan
 */
public class InformacionComprasDao {
    public List<InformacionComprasVo> listar() throws SQLException {      
        List<InformacionComprasVo> respuesta = new ArrayList<>();
        Connection conn = JDBCUtilities.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        String consulta = """
                        SELECT
                            c.ID_Compra,
                            p.Constructora,
                            p.Banco_Vinculado
                        FROM
                            Compra c
                        INNER JOIN Proyecto p ON
                            (c.ID_Proyecto = p.ID_Proyecto)
                        WHERE
                            c.Proveedor = 'Homecenter'
                            AND p.Ciudad = 'Salento';
                        """;
        
        try {
            stm = conn.prepareStatement(consulta);
            rs = stm.executeQuery();
            
            while(rs.next()) {
                InformacionComprasVo vo = new InformacionComprasVo();
                vo.setIdCompra(rs.getInt("ID_Compra"));
                vo.setConstructora(rs.getString("Constructora"));
                vo.setBancoVinculado(rs.getString("Banco_Vinculado"));
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