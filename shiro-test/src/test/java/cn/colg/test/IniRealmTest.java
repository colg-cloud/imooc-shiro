package cn.colg.test;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * - imi realm 测试
 *
 * @author colg
 */
@Slf4j
public class IniRealmTest {

    private IniRealm iniRealm = new IniRealm("classpath:user.ini");

    @Test
    public void testAuthentication() {
        // 1. 构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        defaultSecurityManager.setRealm(iniRealm);

        // 2. 主体提交认证请求
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("Jack", "123456");

        // 登录
        subject.login(token);

        log.info("isAuthenticated: {}", subject.isAuthenticated());

        subject.checkRoles("admin");

        // 权限
        subject.checkPermission("user:delete");
        subject.checkPermission("user:update");
    }
}
