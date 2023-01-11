package com.yudian.www.service.sys;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.param.AeSysLogininforParam;
import com.yudian.www.service.sys.param.GetSysLogininforListParam;
import com.yudian.www.service.sys.vo.SysLogininforInfoVo;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

public interface ISysLogininforServiceProcess {

    void recordLogininfor(String username, String status, String message, HttpServletRequest request, Object... args);

    /**
     * 添加
     *
     * @param aeSysLogininforParam
     */
    void sysLogininforAdd(AeSysLogininforParam aeSysLogininforParam);

    /**
     * 批量创建
     *
     * @param aeSysLogininforParamList
     */
    void sysLogininforAddBatch(List<AeSysLogininforParam> aeSysLogininforParamList);

    /**
     * 编辑
     *
     * @param aeSysLogininforParam
     */
    void sysLogininforEdit(AeSysLogininforParam aeSysLogininforParam);

    /**
     * 删除
     *
     * @param infoIds
     */
    void sysLogininforRemove(Long[] infoIds);

    /**
     * 分页查询
     *
     * @param getSysLogininforListParam
     * @return
     */
    TableRecordVo<SysLogininforInfoVo> getSysLogininforList(GetSysLogininforListParam getSysLogininforListParam);

    /**
     * 明细
     *
     * @param infoId
     * @return
     */
    SysLogininforInfoVo getSysLogininforDetail(Long infoId);


    /**
     * 清空数据
     */
    void truncateTable();
}
