import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList; 
/**
 *
 * @author Bryan Biojó
 */

// CONEXIÓN A LA BASE DE DATOS
public class JDBCUtilities {
    // Atributos de clase para gestión de conexión con la base de datos
    private static final String UBICACION_BD = "ProyectosConstruccion.db";
    
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:sqlite:" + UBICACION_BD;
        return DriverManager.getConnection(url);
    }  
}

// MODELOS

// DAO's (DATA ACCESS OBJECTS)
public class ProyectoBancoDao {
    public List<ProyectoBancoVo> listar(String banco) throws SQLException {      
        List<ProyectoBancoVo> respuesta = new ArrayList<>();
        Connection conn = JDBCUtilities.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        String consulta = "SELECT p.ID_Proyecto AS ID, p.Constructora, p.Ciudad, p.Clasificacion, t.Estrato, l.Nombre || ' ' || l.Primer_Apellido || ' ' || l.Segundo_Apellido AS LIDER FROM Proyecto p INNER JOIN Tipo t ON (p.ID_Tipo = t.ID_Tipo) INNER JOIN Lider l ON (p.ID_Lider = l.ID_Lider) WHERE p.Banco_Vinculado = ? ORDER BY Fecha_Inicio DESC, Ciudad, Constructora;";
        
        try {
            stm = conn.prepareStatement(consulta);
            stm.setString(1, banco);
            rs = stm.executeQuery();
            
            while(rs.next()) {
                ProyectoBancoVo vo = new ProyectoBancoVo();
                vo.setId(rs.getInt("ID"));
                vo.setConstructora(rs.getString("Constructora"));
                vo.setCiudad(rs.getString("Ciudad"));
                vo.setClasificacion(rs.getString("Clasificacion"));
                vo.setEstrato(rs.getInt("Estrato"));
                vo.setLider(rs.getString("LIDER"));
                respuesta.add(vo);
            }
        } catch(SQLException e) {
            System.err.println("Error: " + e);
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

public class DeudasPorProyectoDao {
    public List<DeudasPorProyectoVo> listar(Double limiteInferior) throws SQLException {      
        List<DeudasPorProyectoVo> respuesta = new ArrayList<>();
        Connection conn = JDBCUtilities.getConnection();
        PreparedStatement stm = null;
        ResultSet rs = null;
        
        String consulta = "SELECT c.ID_Proyecto, SUM(mc.Precio_Unidad * c.Cantidad) AS VALOR FROM Compra c INNER JOIN MaterialConstruccion mc ON (c.ID_MaterialConstruccion = mc.ID_MaterialConstruccion) WHERE c.Pagado = 'No' GROUP BY ID_Proyecto HAVING VALOR > ? ORDER BY VALOR DESC;";
        
        try {
            stm = conn.prepareStatement(consulta);
            stm.setDouble(1, limiteInferior);
            rs = stm.executeQuery();
            
            while(rs.next()) {
                DeudasPorProyectoVo vo = new DeudasPorProyectoVo();
                vo.setId(rs.getInt("ID_Proyecto"));
                vo.setValor(rs.getDouble("VALOR"));
                respuesta.add(vo);
            }
        } catch(SQLException e) {
            System.err.println("Error: " + e);
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

public class ComprasDeLiderDao {
    public List<ComprasDeLiderVo> listar() throws SQLException {      
        List<ComprasDeLiderVo> respuesta = new ArrayList<>();
        Connection conn = JDBCUtilities.getConnection();
        Statement stm = null;
        ResultSet rs = null;
        
        String consulta = "SELECT l.Nombre || ' ' || l.Primer_Apellido || ' ' || l.Segundo_Apellido AS LIDER, SUM(mc.Precio_Unidad * c.Cantidad) AS VALOR FROM Proyecto p INNER JOIN Lider l ON (p.ID_Lider = l.ID_Lider) INNER JOIN Compra c ON (p.ID_Proyecto = c.ID_Proyecto) INNER JOIN MaterialConstruccion mc ON (c.ID_MaterialConstruccion = mc.ID_MaterialConstruccion) GROUP BY LIDER ORDER BY VALOR DESC LIMIT 10;";
        
        try {
            stm = conn.createStatement();
            rs = stm.executeQuery(consulta);
            
            while(rs.next()) {
                ComprasDeLiderVo vo = new ComprasDeLiderVo();
                vo.setLider(rs.getString("LIDER"));
                vo.setValor(rs.getDouble("VALOR"));
                respuesta.add(vo);
            }
        } catch(SQLException e) {
            System.err.println("Error: " + e);
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

// VO's (VIEW OBJECTS)
public class ProyectoBancoVo {
    private Integer id;
    private String constructora;
    private String ciudad;
    private String clasificacion;
    private Integer estrato;
    private String lider;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getConstructora() {
        return constructora;
    }

    public void setConstructora(String constructora) {
        this.constructora = constructora;
    }

    public String getCiudad() {
        return ciudad;
    }

    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public int getEstrato() {
        return estrato;
    }

    public void setEstrato(int estrato) {
        this.estrato = estrato;
    }

    public String getLider() {
        return lider;
    }

    public void setLider(String lider) {
        this.lider = lider;
    }

    public String darFormato() {
        return String.format("%3d %-25s %-20s %-15s %7d %-30s", id, constructora, ciudad, clasificacion, estrato, lider);
    }    
}

public class DeudasPorProyectoVo {
    private Integer id;
    private Double valor;
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String darFormato() {
        return String.format("%3d %,15.1f", id, valor);
    }
}

public class ComprasDeLiderVo {
    private String lider;
    private Double valor;
    
    public String getLider() {
        return lider;
    }

    public void setLider(String lider) {
        this.lider = lider;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }
    
    public String darFormato() {
        return String.format("%-25s %,15.1f", lider, valor);
    }
}

// VISTA
public class ReportesView {
    private static ReportesController controladorReportes;
    
    public ReportesView() {
        controladorReportes = new ReportesController();
    }
    
    private String repitaCaracter(Character caracter, Integer veces) {
        String respuesta = "";
        
        for (int i = 0; i < veces; i++) {
            respuesta += caracter;
        }
        return respuesta;
    }
    
    public void proyectosFinanciadosPorBanco(String banco) {
        System.out.println(repitaCaracter('=', 36) + " LISTADO DE PROYECTOS POR BANCO "
                + repitaCaracter('=', 37));
        
        if (banco != null && !banco.isBlank()) {
            System.out.println(String.format("%3s %-25s %-20s %-15s %-7s %-30s", "ID", "CONSTRUCTORA", "CIUDAD", "CLASIFICACION", "ESTRATO", "LIDER"));
            System.out.println(repitaCaracter('-', 105));
            
            // Imprimir en pantalla la información del proyecto
            try {
                List<ProyectoBancoVo> proyectos = controladorReportes.listarProyectosPorBanco(banco);

                for(ProyectoBancoVo proyecto : proyectos) {
                    System.out.println(proyecto.darFormato());
                }
            } catch(SQLException e) {
                System.err.println("Error: " + e);
            }
        }
    }
    
    public void totalAdeudadoPorProyectosSuperioresALimite(Double limiteInferior) {
        System.out.println(repitaCaracter('=', 1) + " TOTAL DEUDAS POR PROYECTO "
                + repitaCaracter('=', 1));
        
        if (limiteInferior != null) {
            System.out.println(String.format("%3s %14s", "ID", "VALOR "));
            System.out.println(repitaCaracter('-', 29));

            // Imprimir en pantalla la información del total adeudado
            try {
                List<DeudasPorProyectoVo> deudas = controladorReportes.listarDeudasPorProyecto(limiteInferior);

                for(DeudasPorProyectoVo deuda : deudas) {
                    System.out.println(deuda.darFormato());
                }
            } catch(SQLException e) {
                System.err.println("Error: " + e);
            }
        }
    }
    
    public void lideresQueMasGastan() {
        System.out.println(repitaCaracter('=', 6) + " 10 LIDERES MAS COMPRADORES "
                + repitaCaracter('=', 7));
        System.out.println(String.format("%-25s %14s", "LIDER", "VALOR "));
        System.out.println(repitaCaracter('-', 41));
        
        // Imprimir en pantalla la información de los líderes
        try {
            List<ComprasDeLiderVo> compras = controladorReportes.listarComprasDeLider();
            
            for(ComprasDeLiderVo compra : compras) {
                System.out.println(compra.darFormato());
            }
        } catch(SQLException e) {
            System.err.println("Error: " + e);
        }
    }
}

// CONTROLADOR
public class ReportesController {
    private final ProyectoBancoDao proyectoBancoDao;
    private final ComprasDeLiderDao comprasDeLiderDao;
    private final DeudasPorProyectoDao deudasPorProyectoDao;
    
    public ReportesController() {
        proyectoBancoDao = new ProyectoBancoDao();
        comprasDeLiderDao = new ComprasDeLiderDao();
        deudasPorProyectoDao = new DeudasPorProyectoDao();
    }

    public List<ProyectoBancoVo> listarProyectosPorBanco(String banco) throws SQLException {
        return proyectoBancoDao.listar(banco);
    }
    
    public List<DeudasPorProyectoVo> listarDeudasPorProyecto(Double limiteInferior) throws SQLException {
        return deudasPorProyectoDao.listar(limiteInferior);
    }
    
    public List<ComprasDeLiderVo> listarComprasDeLider() throws SQLException {
        return comprasDeLiderDao.listar();
    }
}

// APP (MAIN)
public class App {
    /**
     * @param args the command line arguments
     * @throws java.sql.SQLException
     */
    public static void main(String[] args) throws SQLException {
        // Reporte 1: Información de los proyectos financiados por un banco determinado.
        var reportesView = new ReportesView();
        var banco = "Conavi";
        reportesView.proyectosFinanciadosPorBanco(banco);
        
        // Reporte 2: Listado del total adeudado en cada proyecto, filtrado por un límite inferior dado.
        var limiteInferior = 50_000d;
        reportesView.totalAdeudadoPorProyectosSuperioresALimite(limiteInferior);
        
        // Reporte 3: Listado del top 10 de los líderes que más dinero gastan en sus proyectos para la compra de materiales.
        reportesView.lideresQueMasGastan();
    }
}