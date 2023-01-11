package com.yudian.www.service.sys;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.param.AeSysDictDataParam;
import com.yudian.www.service.sys.param.GetSysDictDataListParam;
import com.yudian.www.service.sys.vo.SysDictDataInfoVo;

import java.util.List;


public interface ISysDictDataServiceProcess {


    void sysDictDataAdd(AeSysDictDataParam aeSysDictDataParam);


    void sysDictDataAddBatch(List<AeSysDictDataParam> aeSysDictDataParamList);


    void sysDictDataEdit(AeSysDictDataParam aeSysDictDataParam);


    void sysDictDataRemove(Long[] dictDataIds);


    TableRecordVo<SysDictDataInfoVo> getSysDictDataList(GetSysDictDataListParam getSysDictDataListParam);


    SysDictDataInfoVo getSysDictDataDetail(Long dictDataId);
}
