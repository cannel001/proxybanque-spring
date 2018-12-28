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
@RequestMapping("/virement")
public class VirementController {
    
    @GetMapping("/form")
    public String virement(){
        return "virement/virement";
    }
    
    @GetMapping("/confirm")
    public String confirVirement(){
        return "virement/confirmvirement";
    }
    
    @GetMapping("/resume")
    public String resumeeTransaction(){
        return "virement/resume";
    }
    
}
