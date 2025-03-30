package com.ecolucos.cierre.repository;

import com.ecolucos.cierre.entities.db.Attachment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRepository extends CrudRepository<Attachment, Long> {



}
