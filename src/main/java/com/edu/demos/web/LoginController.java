package com.edu.demos.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

@Controller
public class LoginController {

    // 模拟用户数据（后续可接入数据库）
    private static final String MOCK_USERNAME = "admin";
    private static final String MOCK_PASSWORD = "123456";

    /**
     * 访问登录页面
     * http://127.0.0.1:8080/login.html
     */
    @RequestMapping("/login")
    public String loginPage() {
        return "login.html";
    }

    /**
     * 处理登录请求
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

        if (MOCK_USERNAME.equals(username) && MOCK_PASSWORD.equals(password)) {
            result.put("success", true);
            result.put("message", "登录成功");
            result.put("redirect", "/index.html");
        } else {
            result.put("success", false);
            result.put("message", "用户名或密码错误");
        }

        return result;
    }
}
