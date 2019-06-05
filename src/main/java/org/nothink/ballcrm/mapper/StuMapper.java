package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.StuCriteria;
import org.nothink.ballcrm.entity.StuEntity;

import java.util.List;

public interface StuMapper {
    List<StuEntity> getAll(StuCriteria criteria);
    StuEntity findById(int sid);
    int insertAndGetSid(StuCriteria criteria);

}
