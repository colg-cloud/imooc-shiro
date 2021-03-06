package cn.colg.test;

import cn.hutool.core.lang.Console;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * - ini realm 测试
 *
 * @author colg
 */
@Slf4j
public class IniRealmTest {

    private IniRealm iniRealm = new IniRealm("classpath:user.ini");

    @Test
    public void testIniRealm() {
        // 1. 构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager(iniRealm);

        // 2. 主体提交认证
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("jack", "123456");
        Console.log(JSON.toJSONString(token));

        // 登录
        subject.login(token);

        // 校验帐号密码
        boolean authenticated = subject.isAuthenticated();
        log.info("是否登录 : {}", authenticated);

        // 校验权限
        boolean[] permitted = subject.isPermitted("user:insert", "user:delete", "user:update", "user:select");
        log.info("permitted : {}", permitted);

        // 注销
        subject.logout();
        log.info("是否登录: {}", subject.isAuthenticated());
    }
}
