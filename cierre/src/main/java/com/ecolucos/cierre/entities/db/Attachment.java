package com.ecolucos.cierre.entities.db;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "attachments")
public class Attachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fileName;
    private String storedFileName;
    private String fileType;
    private Long size;

    @ManyToOne
    @JoinColumn(name="caja_id", nullable=false)
    private Caja caja;

}