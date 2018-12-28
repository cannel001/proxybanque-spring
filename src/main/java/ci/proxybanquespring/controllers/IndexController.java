/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.controllers;

import ci.proxybanquespring.service.impl.ConseillerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author willi
 */
@Controller
public class IndexController {
    
    @Autowired
    private ConseillerService conseillerService;
    
    @RequestMapping("/")
    public String index(){
        conseillerService.conseillerParDefaut();
        return "index/index";
    }
    
}
