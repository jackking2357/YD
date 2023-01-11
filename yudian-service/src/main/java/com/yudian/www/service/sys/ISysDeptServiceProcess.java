package com.yudian.www.service.sys;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.base.TreeSelect;
import com.yudian.www.entity.sys.SysDept;
import com.yudian.www.service.sys.param.AeSysDeptParam;
import com.yudian.www.service.sys.param.GetSysDeptListParam;
import com.yudian.www.service.sys.vo.SysDeptInfoVo;

import java.util.List;

public interface ISysDeptServiceProcess {

    /**
     * 添加
     *
     * @param aeSysDeptParam
     */
    void sysDeptAdd(AeSysDeptParam aeSysDeptParam);

    /**
     * 批量创建
     *
     * @param aeSysDeptParamList
     */
    void sysDeptAddBatch(List<AeSysDeptParam> aeSysDeptParamList);

    /**
     * 编辑
     *
     * @param aeSysDeptParam
     */
    void sysDeptEdit(AeSysDeptParam aeSysDeptParam);

    /**
     * 删除
     *
     * @param deptIds
     */
    void sysDeptRemove(Long[] deptIds);

    /**
     * 分页查询
     *
     * @param getSysDeptListParam
     * @return
     */
    TableRecordVo<SysDeptInfoVo> getSysDeptList(GetSysDeptListParam getSysDeptListParam);

    /**
     * 明细
     *
     * @param deptId
     * @return
     */
    SysDeptInfoVo getSysDeptDetail(Long deptId);

    List<SysDept> getDeptList();

    List<TreeSelect> buildDeptTreeSelect(List<SysDept> sysDeptList);
}
