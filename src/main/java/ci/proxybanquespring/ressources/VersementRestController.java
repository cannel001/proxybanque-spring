/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.ressources;

import ci.proxybanquespring.service.IVersementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cannel
 */
@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class VersementRestController {
    
    //les proprietes
    @Autowired
    private IVersementService versementService;
    
    public ResponseEntity<?> createOrUpdateConseilller(){
        
    }
    
    public ResponseEntity<?> readOneConseiller(){
        
    }
    
    public ResponseEntity<?> readAllConseiller(){
        
    } 
    
    public ResponseEntity<?> deleteConseiller(){
        
    }
    
}
