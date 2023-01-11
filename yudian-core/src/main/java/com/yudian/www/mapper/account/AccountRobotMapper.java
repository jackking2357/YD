package com.yudian.www.mapper.account;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yudian.www.entity.account.AccountRobot;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

/**
 * 平台用户机器人 Mapper 接口
 *
 * @author yudian
 * @since 2023-01-05
 */
@Repository
public interface AccountRobotMapper extends BaseMapper<AccountRobot> {

    @Update("<script>"
            + "UPDATE account_robot set cumulative_income = cumulative_income + #{score} where account_robot_id in "
            + "<foreach item='item' index='index' collection='accountRobotIds' open='(' separator=',' close=')'>"
            + "#{item}"
            + "</foreach>"
            + "</script>")
    int addScore(@Param("accountRobotIds") List<Long> accountRobotIds, @Param("score") BigDecimal score);
}
