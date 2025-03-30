package com.ecolucos.cierre.entities.db;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.sql.Timestamp;
import java.util.Set;

@Entity
@Table(name = "caja", schema = "cierre")
@Data
@NoArgsConstructor
public class Caja {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @OneToMany(mappedBy="caja")
    private Set<Gasto> gastos;
    @OneToMany(mappedBy="caja")
    private Set<Attachment> attachments;
    private String inicial;
    private String recuento;
    private Timestamp fecha;
    private  String totaltarjeta;
    private String cierretarjeta ;
    private String propina  ;
    private String descuadretarjeta ;
    private String ventas  ;
    private String ingreso ;
    private String totalGastos;
    private String total ;
    private String descuadre ;
    private String turno ;


}
