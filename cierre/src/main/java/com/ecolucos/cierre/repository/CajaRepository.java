package com.ecolucos.cierre.repository;

import com.ecolucos.cierre.entities.Caja;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CajaRepository extends CrudRepository<Caja, Integer> {

    Caja findRecuentoById(int id);

    @Query(value = "SELECT recuento FROM caja u WHERE u.turno = :turno order by fecha desc limit 1",
            nativeQuery = true)
    String findLastRecuentoByTurno(String turno);
}
