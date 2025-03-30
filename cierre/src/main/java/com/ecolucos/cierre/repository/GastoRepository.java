package com.ecolucos.cierre.repository;

import com.ecolucos.cierre.entities.db.Attachment;
import com.ecolucos.cierre.entities.db.Gasto;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GastoRepository extends CrudRepository<Gasto, Long> {



}
