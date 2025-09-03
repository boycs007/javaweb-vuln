package org.javaweb.vuln.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/Debug/")
public class DebugController {


    @RequestMapping(value = {"/debug1"})
    @ResponseBody
    public Object debug1(String payload, HttpServletResponse response) throws Exception {
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        return payload + "ok";
    }

}
