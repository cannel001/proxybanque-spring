/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author willi
 */
@Controller
@RequestMapping(value = "/")
public class ClientController {
    
    @GetMapping("/client")
    public String profilClient(){
        return "client/client";
    }
    
    @GetMapping("/client/formclient")
    public String formNouvClient(){
        return "client/formulaireclient";
    }
    
    @GetMapping("/client/modifclient")
    public String formModifClient(){
        return "client/formulaireclient";
    }
    
}
