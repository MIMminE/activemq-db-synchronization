package org.mq.jpa.consumer;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumerRepository extends CrudRepository<ConsumerEntity, Long> {
}
