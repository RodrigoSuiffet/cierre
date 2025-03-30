package com.ecolucos.cierre.util;

import com.ecolucos.cierre.entities.DTO.CajaDTO;

import com.ecolucos.cierre.entities.db.Caja;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Component
public class CajaDTOMapper {

    public Caja cajaDTOToCaja(CajaDTO submission) {
        // Convert the DTO to an entity
        Caja caja = new Caja();
        caja.setTurno(submission.getShift());
        caja.setTotal(String.valueOf(submission.getTotal()));
        caja.setTotaltarjeta(String.valueOf(submission.getCardTotal()));
        caja.setCierretarjeta(String.valueOf(submission.getCardClose()));
        caja.setPropina(String.valueOf(submission.getTips()));
        caja.setDescuadretarjeta(String.valueOf(submission.getCardDiscrepancy()));
        caja.setInicial(String.valueOf(submission.getInitialValue()));
        caja.setVentas(String.valueOf(submission.getSales()));
        caja.setIngreso(String.valueOf(submission.getCashIncome()));
        caja.setTotalGastos(String.valueOf(submission.getTotalExpenses()));
        caja.setRecuento(String.valueOf(submission.getRecuento()));
        caja.setDescuadre(String.valueOf(submission.getDiscrepancy()));
        return caja;
    }
}
