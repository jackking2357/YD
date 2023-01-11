package com.yudian.www.mapper.sys;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yudian.www.entity.sys.SysLogininfor;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

@Repository
public interface SysLogininforMapper extends BaseMapper<SysLogininfor> {

    @Update("truncate table sys_logininfor")
    void truncateTable();
}
