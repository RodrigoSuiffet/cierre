package com.ecolucos.cierre.entities.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para recibir los datos de gastos
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GastosDTO {
    private double withdrawal;
    private List<ExpenseDTO> expenses;
}