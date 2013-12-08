package org.lework.core.persistence.dao.auditor;

import org.lework.core.persistence.entity.auditor.OperatingRecord;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * 操作记录 Dao
 * User: Gongle
 * Date: 13-12-7
 */
public interface OperatingRecordDao extends PagingAndSortingRepository<OperatingRecord, String>,
        JpaSpecificationExecutor<OperatingRecord> {
}
