/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.service.IGenerateurService;
import java.util.Random;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class NumeroTransactionService implements IGenerateurService{

    @Override
    public Object generate() {
        
        Random random=new Random();
        
        Integer index;
        
        String code="0123456789";
        
        String codeGenere="";
        
        for(int i=0;i<14;i++){
            
            index=random.nextInt(code.length());
            
            codeGenere+=code.charAt(index);
            
        }
        
        return codeGenere;
        
    }
    
}
