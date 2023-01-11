package com.yudian.www.mapper.account;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yudian.www.entity.account.Account;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * 平台用户 Mapper 接口
 *
 * @author yudian
 * @since 2023-01-05
 */
@Repository
public interface AccountMapper extends BaseMapper<Account> {

    @Update("UPDATE account set score = score - #{score} where account_id = #{accountId} and score >= #{score} ")
    int subScore(@Param("accountId") Long accountId, @Param("score") BigDecimal score);

    @Update("UPDATE account set score = score + #{score} where account_id = #{accountId}")
    int addScore(@Param("accountId") Long accountId, @Param("score") BigDecimal score);
}
