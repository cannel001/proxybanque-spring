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
public class IbanCiService implements IGenerateurService{

    @Override
    public Object generate() {
        
        //le code comporte 12 chiffre
        //la clé comporte 2 
        //le pays CI
        Random aleatoire = new Random();

        String codeGenere = "";
        Long cle=null;
        int index = 0;
        String code = "0123456789";
        String bban1 = null;
        String bban2 = null;

        //recuperation du code ascii de CI
        String ascii = ((int) 'C' - 55)+"" + ((int) 'I' - 55) + "";

        for (int i = 0; i < 12; i++) {

            index = aleatoire.nextInt(code.length());

            codeGenere += code.charAt(index);

        }

        //BBAN personnalisé par defaut
        bban1 = codeGenere + ascii + "00";

        //calcul de la clé
        cle = (Long.parseLong(bban1)) % 97;
        cle = 98 - cle;

        //nouveau numero generé
        bban2 = "CI" + cle + codeGenere;
        
        return bban2;
        
    }
    
}
