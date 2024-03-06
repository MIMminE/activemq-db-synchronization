package org.mq.jpa.publisher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PublisherRepository extends CrudRepository<PublisherEntity, Long> {
    @Query(value = "select * from authlog where mod(second(time_stamp),10)  = :modIndex and sync_flag= false", nativeQuery = true)
    List<PublisherEntity> findByPublishingTarget(@Param("modIndex") int modIndex);
}
