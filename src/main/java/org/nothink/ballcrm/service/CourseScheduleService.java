package org.nothink.ballcrm.service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.nothink.ballcrm.entity.CourseScheduleEntity;
import org.nothink.ballcrm.entity.PagedResult;
import org.nothink.ballcrm.entity.StuEntity;
import org.nothink.ballcrm.mapper.CourseScheduleMapper;
import org.nothink.ballcrm.util.CodeDef;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;;

import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

@Service
public class CourseScheduleService {
    org.slf4j.Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    StuService stuService;
    @Autowired
    CourseScheduleMapper csMapper;
    @Autowired
    CacheService cache;

    /**
     * 学员约课
     * @param book
     * @return
     */
    @Transactional
    public int bookCourse(CourseScheduleEntity book){
        int sid=book.getSid();
        StuEntity stu=stuService.findById(sid);
        if (stu!=null && CodeDef.STU_BOOKED.equals(stu.getStatus())){
            //已预约课
            return -1;
        }

        //新增约课记录
        book.setCreateDate(new Date());
        book.setNotifyStatus(CodeDef.HANDLE_WAITING);
        book.setSignStatus(CodeDef.SIGN_WAITING);
        book.setTraceStatus(CodeDef.HANDLE_WAITING);
        System.out.println(book);
        int i=csMapper.insertSelective(book);

        //学员状态改变
        stuService.updateStuStatus(stu,CodeDef.STU_BOOKED,"");
        return i;
    }

    /**
     * 学员上课流水列表
     */
    public PagedResult<CourseScheduleEntity> CourseScheduleList(CourseScheduleEntity cs) {
        Page p = PageHelper.startPage(cs.getCurrentPage(), cs.getPageSize());

        //执行查询
        List<CourseScheduleEntity> list = csMapper.getCourseScheduleList(cs);
        PagedResult<CourseScheduleEntity> result = new PagedResult<>(cs.getCurrentPage(), cs.getPageSize(), (int) p.getTotal());
        //翻译代码值
        if (list != null)
            for (CourseScheduleEntity item : list)
                transCode(item);
        result.setItems(list);
        return result;
    }

    /**
     * 明日上课通知处理
     * @param cs
     */
    @Transactional
    public int handleScheduleNotify(CourseScheduleEntity cs){
        CourseScheduleEntity relCs=csMapper.selectByPrimaryKey(cs.getPkid());
        if (relCs==null)
            return 0;
        relCs.setNotifyStatus(cs.getNotifyStatus());
        relCs.setNotifyNote(cs.getNotifyNote());
        relCs.setSignStatus(cs.getSignStatus());
        int r=csMapper.updateByPrimaryKeySelective(relCs);
        return r;
    }


    private void transCode(CourseScheduleEntity cs){
        if (cs!=null){
            cs.setNotifyStatusDef(cache.CodeDefCache().get(cs.getNotifyStatus()));
            cs.setSignStatusDef(cache.CodeDefCache().get(cs.getSignStatus()));
            cs.setTraceStatusDef(cache.CodeDefCache().get(cs.getTraceStatus()));
        }
    }

}
