package com.yudian.www.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yudian.www.entity.sys.SysOperLog;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface SysOperLogMapper extends BaseMapper<SysOperLog> {

    @Update("truncate table sys_oper_log")
    void truncateTable();
}
