package com.ecolucos.cierre.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Entity
@Table(name = "caja", schema = "cierre")
@Data
@NoArgsConstructor
public class Caja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
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
