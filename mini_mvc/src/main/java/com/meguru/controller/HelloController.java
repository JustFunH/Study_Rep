package com.meguru.controller;

import com.meguru.annotation.Component;
import com.meguru.annotation.Controller;
import com.meguru.annotation.Param;
import com.meguru.annotation.RequestMapping;
import com.meguru.annotation.ResponseBody;
import com.meguru.domain.User;
import com.meguru.model.ModelAndView;

@Controller
@RequestMapping("/hello")
@Component
public class HelloController {
    @RequestMapping("/a")
    public String hello(@Param("name") String name, @Param("age") Integer age) {
        return String.format("<h1>hello world</h1> <br> name: %s age: %s", name, age);
    }

    @RequestMapping("/json")
    @ResponseBody
    public User json(@Param("name") String name, @Param("age") Integer age) {
        User user = new User();
        user.setName("Meguru");
        user.setAge(20);
        return user;
    }

    @RequestMapping("/html")
    public ModelAndView html(@Param("name") String name) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setView("index.html");
        modelAndView.getContext().put("name", name);
        return modelAndView;
    }
}
