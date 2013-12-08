package org.lework.core.service.auditor;

import org.lework.core.persistence.dao.auditor.OperatingRecordDao;
import org.lework.core.persistence.entity.auditor.OperatingRecord;
import org.lework.runner.orm.support.SearchFilter;
import org.lework.runner.orm.support.Specifications;
import org.lework.runner.utils.Collections3;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * 操作记录 Service
 * User: Gongle
 * Date: 13-12-7
 */
@Service
@Transactional
public class OperatingRecordManager {
    private static Logger logger = LoggerFactory.getLogger(OperatingRecordManager.class);
    @Autowired
    private OperatingRecordDao operatingRecordDao;

    public OperatingRecord getOperatingRecord(String id) {
        return operatingRecordDao.findOne(id);
    }

    public List<OperatingRecord> getOperatingRecordsByIds(List<String> ids) {
        if (Collections3.isEmpty(ids)) {
            return new ArrayList<OperatingRecord>();
        }
        return (List<OperatingRecord>) operatingRecordDao.findAll(ids);
    }

    public void saveOperatingRecord(OperatingRecord entity) {
        operatingRecordDao.save(entity);
    }

    public void deleteOperatingRecord(String id) {
        operatingRecordDao.delete(id);
    }

    public void deleteOperatingRecord(OperatingRecord entity) {
        operatingRecordDao.delete(entity);
    }

    public void deleteOperatingRecords(List<OperatingRecord> entities) {
        operatingRecordDao.delete(entities);
    }

    /**
     * 获取一个分页
     *
     * @param pageable 分页信息
     * @param filters  属性过滤器
     * @return Page<OperatingRecord>
     * @see org.springframework.data.domain.Page
     * @see org.springframework.data.domain.Pageable
     * @see org.lework.runner.orm.support.SearchFilter
     */
    public Page<OperatingRecord> searchPageOperatingRecord(Pageable pageable, List<SearchFilter> filters) {

        Specification<OperatingRecord> spec = Specifications.build(filters, OperatingRecord.class);
        return operatingRecordDao.findAll(spec, pageable);
    }

}
