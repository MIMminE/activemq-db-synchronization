package org.mq.jpa.dest_log;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DestRepository extends CrudRepository<DestLogEntity, Long> {
}
