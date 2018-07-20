# Shiro 安全框架简介

## 1. Shiro 简介

### 1.1 什么是Shiro？
- Apache的强大灵活的开源安全框架
- 认证、授权、企业会话管理、安全加密

### 1.2 Shiro与Spring Security比较
- Apache Shiro
    - 简单、灵活
    - 可脱离Spring
    - 粒度较粗
- Spring Security
    - 复杂、笨重
    - 不可脱离Spring
    - 粒度更细
    
### 2. Shiro 认证
1. 创建SecurityManager
2. 主体提交认证
3. SecurityManager认证
4. Realm验证
5. Authenticator认证

### 3. Shiro 加密