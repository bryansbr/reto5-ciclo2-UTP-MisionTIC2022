/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reto5.controller;

import reto5.model.vo.InformacionLiderVo;
import reto5.model.vo.InformacionComprasVo;
import reto5.model.vo.InformacionProyectoVo;
import reto5.model.dao.InformacionComprasDao;
import reto5.model.dao.InformacionProyectoDao;
import reto5.model.dao.InformacionLiderDao;
import java.util.List;
import java.sql.SQLException;

/**
 *
 * @author Bryan
 */
public class ReportesController {
    private final InformacionLiderDao informacionLiderDao;
    private final InformacionProyectoDao informacionProyectoDao;
    private final InformacionComprasDao informacionComprasDao;
    
    public ReportesController() {
        informacionLiderDao = new InformacionLiderDao();
        informacionProyectoDao = new InformacionProyectoDao();
        informacionComprasDao = new InformacionComprasDao();
    }
    
    public List<InformacionLiderVo> listarInformacionLider() throws SQLException {
        return informacionLiderDao.listar();
    }
    public List<InformacionProyectoVo> listarInformacionProyecto() throws SQLException {
        return informacionProyectoDao.listar();
    }
    public List<InformacionComprasVo> listarInformacionCompras() throws SQLException {
        return informacionComprasDao.listar();
    }
}
