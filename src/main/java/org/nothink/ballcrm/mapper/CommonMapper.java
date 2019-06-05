package org.nothink.ballcrm.mapper;

import org.nothink.ballcrm.entity.CodeDefEntity;
import org.nothink.ballcrm.entity.EmpInfoEntity;

import java.util.List;

public interface CommonMapper {
     List<CodeDefEntity> getCodeDef();
     List<EmpInfoEntity> getEmpInfo();

}
