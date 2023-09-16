package com.easy.generator.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * HomeController
 *
 * @author: YZ.YANG
 * @date: 2023-09-16 21:14
 */
@Controller
public class HomeController {

    @GetMapping("/")
    public String getIndexPage() {
        return "index";
    }
}
