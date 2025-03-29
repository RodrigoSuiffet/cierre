package com.ecolucos.cierre.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para recibir los datos del cierre de caja desde el cliente React
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CashRegisterSubmissionDTO {
    private String shift;
    private double total;
    private GastosDTO gastos;
    private double cardTotal;
    private double cardClose;
    private double tips;
    private double cardDiscrepancy;
    private double initialValue;
    private double sales;
    private double cashIncome;
    private double totalExpenses;
    private double recuento;
    private double discrepancy;
    private List<Long> attachmentIds;
}