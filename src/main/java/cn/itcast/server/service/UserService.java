package cn.itcast.server.service;

/**
 * @author Paynesun
 * @title: UserService
 * @projectName JAVASenior
 * @description: 用户管理接口
 * @date 2022/6/13 12:31
 */
public interface UserService {
    /**
     * 登陆
     * @param username 用户名
     * @param password 密码
     * @return
     */
    boolean login(String username,String password);
}
