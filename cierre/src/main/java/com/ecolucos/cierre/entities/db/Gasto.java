package com.ecolucos.cierre.entities.db;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "gastos", schema = "cierre")
public class Gasto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private double amount;
    private String details;
    @ManyToOne
    @JoinColumn(name="caja_id", nullable=false)
    private Caja caja;


}