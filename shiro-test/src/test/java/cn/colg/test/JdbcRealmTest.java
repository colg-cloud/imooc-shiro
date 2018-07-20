package cn.colg.test;

import cn.hutool.core.lang.Console;
import cn.hutool.crypto.SecureUtil;
import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.realm.jdbc.JdbcRealm;
import org.apache.shiro.subject.Subject;
import org.junit.Test;

/**
 * - jdbc realm 测试
 *
 * @author colg
 */
@Slf4j
public class JdbcRealmTest {

    /** 数据源 */
    private static DruidDataSource dataSource = new DruidDataSource();

    static {
        dataSource.setUrl("jdbc:mysql://localhost:3306/shiro?useUnicode=true&characterEncoding=UTF-8&allowMultiQueries=true&useSSL=false");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }

    @Test
    public void testJdbcRealm() {
        // 注入数据源
        JdbcRealm jdbcRealm = new JdbcRealm();
        jdbcRealm.setDataSource(dataSource);
        // 允许在授权期间查找权限，默认为false
        jdbcRealm.setPermissionsLookupEnabled(true);

        // 认证查询
        String sql = "select u.password from users u where u.username = ?";
        jdbcRealm.setAuthenticationQuery(sql);

        // 用户角色查询
        String roleSql = "select ur.role_name from user_roles ur where ur.username = ?";
        jdbcRealm.setUserRolesQuery(roleSql);

        // 角色权限查询
        String permissionsSql = "select rp.permission from roles_permissions rp where rp.role_name = ?";
        jdbcRealm.setPermissionsQuery(permissionsSql);

        // 1. 构建 SecurityManager 环境
        DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager(jdbcRealm);

        // 3. 主体提交认证
        SecurityUtils.setSecurityManager(defaultSecurityManager);
        Subject subject = SecurityUtils.getSubject();

        UsernamePasswordToken token = new UsernamePasswordToken("jack", "123456");
        Console.log(JSON.toJSONString(token));

        // 登录
        subject.login(token);

        // 校验帐号密码
        boolean authenticated = subject.isAuthenticated();
        log.info("是否登录 : {}", authenticated);

        // 校验角色
        subject.checkRole("admin");

        // 校验权限
        boolean[] permitted = subject.isPermitted("user:insert", "user:delete", "user:update", "user:select");
        log.info("权限列表 : {}", permitted);

        subject.logout();
        log.info("是否登录: {}", subject.isAuthenticated());
    }

}
