package com.yudian.www.service.sys;

import com.yudian.www.base.TableRecordVo;
import com.yudian.www.service.sys.param.AeSysPostParam;
import com.yudian.www.service.sys.param.GetSysPostListParam;
import com.yudian.www.service.sys.vo.SysPostInfoVo;

import java.util.List;


public interface ISysPostServiceProcess {


    void sysPostAdd(AeSysPostParam aeSysPostParam);


    void sysPostAddBatch(List<AeSysPostParam> aeSysPostParamList);


    void sysPostEdit(AeSysPostParam aeSysPostParam);


    void sysPostRemove(Long[] postIds);


    TableRecordVo<SysPostInfoVo> getSysPostList(GetSysPostListParam getSysPostListParam);


    SysPostInfoVo getSysPostDetail(Long postId);
}
