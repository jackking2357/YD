package com.yudian.www.project.config;

import lombok.Data;

import java.util.List;

@Data
public class WanWeiItemPrice {

    private String showapi_res_error;
    private String showapi_res_id;
    private Integer showapi_res_code;
    private Integer showapi_fee_num;
    private ShowApiResBody showapi_res_body;


    @Data
    public static class ShowApiResBody {
        private String remark;
        private List<ShowApiResBodyList> list;
        private Integer showapi_res_code;
        private String showapi_res_error;
        private String showapi_res_id;
    }
}
