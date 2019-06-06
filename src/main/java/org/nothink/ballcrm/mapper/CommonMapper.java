package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.CodeDefEntity;

import java.util.List;

public interface CommonMapper {
     List<CodeDefEntity> getCodeDef();
     int insertCodeDef(CodeDefEntity codeDef);
}
