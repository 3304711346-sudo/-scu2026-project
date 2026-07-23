package com.edu.demos.web;

import com.edu.entity.User;
import com.edu.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    @Autowired
    private UserMapper userMapper;

    /**
     * 访问登录页面
     */
    @RequestMapping("/login")
    public String loginPage() {
        return "login.html";
    }

    /**
     * 处理登录请求（数据库验证）
     */
    @PostMapping("/login")
    @ResponseBody
    public Map<String, Object> doLogin(
            @RequestParam("username") String username,
            @RequestParam("password") String password,
            @RequestParam(value = "remember", defaultValue = "false") boolean remember) {

        Map<String, Object> result = new HashMap<>();

        if (username == null || username.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "用户名不能为空");
            return result;
        }
        if (password == null || password.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "密码不能为空");
            return result;
        }

        // 从数据库查询用户
        User user = userMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username)
        );

        if (user != null && password.equals(user.getPassword())) {
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("redirect", "/index.html");
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
        }

        return result;
    }

    /**
     * 访问注册页面
     */
    @RequestMapping("/register")
    public String registerPage() {
        return "register.html";
    }

    /**
     * 处理注册请求
     */
    @PostMapping("/register")
    @ResponseBody
    public Map<String, Object> doRegister(
            @RequestParam("username") String username,
            @RequestParam("password") String password) {

        Map<String, Object> result = new HashMap<>();

        if (username == null || username.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "用户名不能为空");
            return result;
        }
        if (password == null || password.trim().isEmpty()) {
            result.put("success", false);
            result.put("message", "密码不能为空");
            return result;
        }
        if (password.length() < 6) {
            result.put("success", false);
            result.put("message", "密码长度不能少于6位");
            return result;
        }

        // 检查用户名是否已存在
        User existing = userMapper.selectOne(
                new com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper<User>()
                        .eq(User::getUsername, username.trim())
        );
        if (existing != null) {
            result.put("success", false);
            result.put("message", "用户名已存在");
            return result;
        }

        // 创建新用户
        User newUser = new User();
        newUser.setUsername(username.trim());
        newUser.setPassword(password);
        userMapper.insert(newUser);

        result.put("success", true);
        result.put("message", "注册成功");
        return result;
    }
}
