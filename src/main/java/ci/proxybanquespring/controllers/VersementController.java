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
@RequestMapping("/versement")
public class VersementController {
    
    @GetMapping("/form")
    public String versement(){
        return "versement/formversement";
    }
    
    @GetMapping("/confirm")
    public String confirmVersement(){
        return "";
    }
    
    public String resumeeTransaction(){
        return "";
    }
    
}
