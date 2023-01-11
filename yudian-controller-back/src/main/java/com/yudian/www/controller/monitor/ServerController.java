package com.yudian.www.controller.monitor;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaMode;
import com.yudian.common.entity.R;
import com.yudian.www.controller.monitor.server.MonitorServer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/monitor/server")
public class ServerController {

    @SaCheckPermission(value = {"monitor:server:list"}, orRole = "admin", mode = SaMode.OR)
    @GetMapping()
    public R getInfo() throws Exception {
        MonitorServer server = new MonitorServer();
        server.copyTo();
        return R.ok(server);
    }
}
