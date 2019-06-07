/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.ressources;

import ci.proxybanquespring.domaine.Customer;
import ci.proxybanquespring.domaine.Current;
import ci.proxybanquespring.service.IClientService;
import ci.proxybanquespring.service.ICourantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author cannel
 */
@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class ClientRestController {
    
    //les proprietes
    @Autowired
    private IClientService clientService;
    
    @Autowired
    private ICourantService courantService;
    
    public ResponseEntity<?> createOrUpdateClient(@RequestBody Customer c){
        
        if(c!=null){
            Customer clientRetournee=clientService.create(c);
            if(clientRetournee!=null){
                Current currentRetournee =courantService.
            }
        }
        
    }
    
    public ResponseEntity<?> readOneClient(){
        
    }
    
    public ResponseEntity<?> readAllClient(){
        
    } 
    
    public ResponseEntity<?> deleteClient(){
        
    }
    
}
