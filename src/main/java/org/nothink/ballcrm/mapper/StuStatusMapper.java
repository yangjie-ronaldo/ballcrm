package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.StuStatusEntity;

import java.util.List;

public interface StuStatusMapper {
    int insert(StuStatusEntity record);
    List<StuStatusEntity> getStuStatusListBySid(Integer sid);

}