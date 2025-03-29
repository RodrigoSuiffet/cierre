package com.ecolucos.cierre.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "caja", schema = "cierre")
@Data
@NoArgsConstructor
public class Caja {
    @Id
    private int id;
    private String inicial;
    private String recuento;
    private Timestamp fecha;
    private  String totaltarjeta;
    private String cierretarjeta ;
    private String propina  ;
    private String descuadretarjeta ;
    private String ventas  ;
    private String ingreso ;
    private String gastos ;
    private String total ;
    private String descuadre ;
    private String turno ;


}
