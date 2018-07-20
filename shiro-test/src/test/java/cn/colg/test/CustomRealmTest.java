package cn.colg.test;

import cn.colg.shiro.realm.CustomRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.subject.Subject;
import org.junit.Test;


/**
 * - 自定义 realm 测试
 *
 * @author colg
 */
@Slf4j
public class CustomRealmTest {

    @Test
    public void testCustomReal() {
        // 1. 构建 SecurityManager 环境
        CustomRealm customRealm = new CustomRealm();
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager(customRealm);

        // 加密
        HashedCredentialsMatcher matcher = new HashedCredentialsMatcher();
        matcher.setHashAlgorithmName("md5");
        matcher.setHashIterations(1);
        customRealm.setCredentialsMatcher(matcher);

        // 2. 主体提交认证
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("jack", "123456");
        // 登录
        subject.login(token);

        // 校验帐号密码
        log.info("是否登录: {}", subject.isAuthenticated());

        // 校验角色
        subject.checkRole("admin");

        boolean[] permitted = subject.isPermitted("user:insert", "user:delete", "user:update", "user:select");
        log.info("权限校验: {}", permitted);

        // 注销
        subject.logout();
        log.info("是否登录: {}", subject.isAuthenticated());
    }
}
