package org.mq.jpa.source_log;

import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SourceRepository extends CrudRepository<SourceLogEntity, Long> {

    List<SourceLogEntity> findBySyncFlag(Boolean bool);

    @Query(value = "select * from authlog where mod(second(time_stamp),10)  = :modIndex and sync_flag= false", nativeQuery = true)
    List<SourceLogEntity> findBySecondIsOne(@Param("modIndex") int modIndex);

}
