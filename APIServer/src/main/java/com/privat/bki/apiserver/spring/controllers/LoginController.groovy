package com.privat.bki.apiserver.spring.controllers

import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

import java.security.Principal

/**
 * Created by sting on 3/28/15.
 */
@Controller
//@RequestMapping("/login")
class LoginController {

    /*@RequestMapping(method = RequestMethod.GET)
    String loginPage(Model model){
        return "login"
    }*/

    @RequestMapping(value = "/login")
    public Principal user(Principal user) {
        return user;
    }
}
