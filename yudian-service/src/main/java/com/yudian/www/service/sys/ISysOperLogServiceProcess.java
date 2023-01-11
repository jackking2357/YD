package com.yudian.www.service.sys;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.param.AeSysOperLogParam;
import com.yudian.www.service.sys.param.GetSysOperLogListParam;
import com.yudian.www.service.sys.vo.SysOperLogInfoVo;

import java.util.List;

public interface ISysOperLogServiceProcess {

    /**
     * 添加
     *
     * @param aeSysOperLogParam
     */
    void sysOperLogAdd(AeSysOperLogParam aeSysOperLogParam);

    /**
     * 批量创建
     *
     * @param aeSysOperLogParamList
     */
    void sysOperLogAddBatch(List<AeSysOperLogParam> aeSysOperLogParamList);

    /**
     * 编辑
     *
     * @param aeSysOperLogParam
     */
    void sysOperLogEdit(AeSysOperLogParam aeSysOperLogParam);

    /**
     * 删除
     *
     * @param operIds
     */
    void sysOperLogRemove(Long[] operIds);

    /**
     * 分页查询
     *
     * @param getSysOperLogListParam
     * @return
     */
    TableRecordVo<SysOperLogInfoVo> getSysOperLogList(GetSysOperLogListParam getSysOperLogListParam);

    /**
     * 明细
     *
     * @param operId
     * @return
     */
    SysOperLogInfoVo getSysOperLogDetail(Long operId);

    /**
     * 清空数据
     */
    void truncateTable();
}
