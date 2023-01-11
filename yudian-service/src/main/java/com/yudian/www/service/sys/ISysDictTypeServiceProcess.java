package com.yudian.www.service.sys;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.param.AeSysDictTypeParam;
import com.yudian.www.service.sys.param.GetSysDictTypeListParam;
import com.yudian.www.service.sys.vo.SysDictTypeInfoVo;

import java.util.List;

public interface ISysDictTypeServiceProcess {

    /**
     * 添加
     *
     * @param aeSysDictTypeParam
     */
    void sysDictTypeAdd(AeSysDictTypeParam aeSysDictTypeParam);

    /**
     * 批量创建
     *
     * @param aeSysDictTypeParamList
     */
    void sysDictTypeAddBatch(List<AeSysDictTypeParam> aeSysDictTypeParamList);

    /**
     * 编辑
     *
     * @param aeSysDictTypeParam
     */
    void sysDictTypeEdit(AeSysDictTypeParam aeSysDictTypeParam);

    /**
     * 删除
     *
     * @param dictIds
     */
    void sysDictTypeRemove(Long[] dictIds);

    /**
     * 分页查询
     *
     * @param getSysDictTypeListParam
     * @return
     */
    TableRecordVo<SysDictTypeInfoVo> getSysDictTypeList(GetSysDictTypeListParam getSysDictTypeListParam);

    /**
     * 明细
     *
     * @param dictId
     * @return
     */
    SysDictTypeInfoVo getSysDictTypeDetail(Long dictId);
}
