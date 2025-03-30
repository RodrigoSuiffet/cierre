package com.ecolucos.cierre.util;

import com.ecolucos.cierre.entities.DTO.CajaDTO;
import com.ecolucos.cierre.entities.DTO.ExpenseDTO;
import com.ecolucos.cierre.entities.db.Caja;
import com.ecolucos.cierre.entities.db.Gasto;
import org.springframework.stereotype.Component;

@Component
public class GastosDTOMapper {

    public Gasto expenseDTOToGasto(ExpenseDTO submission) {
        // Convert the DTO to an entity
        Gasto gasto = new Gasto();
        gasto.setType(submission.getType());
        gasto.setAmount(submission.getAmount());
        gasto.setDetails(submission.getDetails());

        return gasto;
    }
}
