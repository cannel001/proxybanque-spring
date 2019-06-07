/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.ressources;

import ci.proxybanquespring.domaine.Advisor;
import ci.proxybanquespring.service.IConseillerService;
import ci.proxybanquespring.service.ISendSmsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author Seka Cannel Ulrich Evrard Cette classe represente un
 * conseillerRestController
 */
@RestController
@RequestMapping(value = "/api")
@CrossOrigin
public class ConseillerRestController {

    //les proprietes
    @Autowired
    private IConseillerService conseillerService;

    @Autowired
    private ISendSmsService sendSmsService;

    /**
     * Cette methode permet creer ou modifier un advisor. Elle prend en
     * parametre un objet de type advisor
     *
     * @param c
     * @return
     */
    @PostMapping(value = "/advisor")
    public ResponseEntity<?> createOrUpdateConseilller(@RequestBody Advisor c) {
        
        //les proprietes
        String code=null;

        if (c != null && !"".equals(c.getEmail())) {

            //verifier si le advisor existe deja
            Boolean verifExistance = false;
            verifExistance = conseillerService.readOne(c.getEmail()) != null;

            //au cas ou le advisor n'existe pas
            if (!verifExistance) {
                //generer password par defaut
                code = conseillerService.genererPassword();
                c.setPassword(code);
            }

            Advisor advisorRetourner = conseillerService.create(c);
            if (advisorRetourner != null) {
                //au cas ou le advisor n'existe pas
                if (!verifExistance) {
                    //message à envoyer au advisor pour se connecter
                    String message = "Bienvenue sur la plateforme Proxy Banque. Veuillez utiliser ce code pour vous connecter. Code: " + code;
                    sendSmsService.sendMySms(c.getTel(), message);
                }
                return new ResponseEntity<>(advisorRetourner, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Erreur survenue pendant l'enregistrement", HttpStatus.NOT_ACCEPTABLE);

    }

    /**
     * Cette methode permet de lire un advisor. Elle prend en parametre un
     * email
     *
     * @param email
     * @return
     */
    @GetMapping(value = "/advisor/{id}")
    public ResponseEntity<?> readOneConseiller(@PathVariable String email) {
        if (email != null && !"".equals(email)) {
            Advisor advisorRetourner = conseillerService.readOne(email);
            if (advisorRetourner != null) {
                return new ResponseEntity<>(advisorRetourner, HttpStatus.FOUND);
            }
        }
        return new ResponseEntity<>("Erreur survenue pendant la recuperation du advisor", HttpStatus.NOT_FOUND);
    }

    /**
     * cette methode permet de retourner tous les conseillers
     *
     * @return
     */
    @GetMapping(value = "/advisor")
    public List<Advisor> readAllConseiller() {
        return conseillerService.readAll();
    }

    /**
     * Cette methode permet de supprimer un advisor. Elle prend en parametre
     * un email
     *
     * @param email
     * @return
     */
    @DeleteMapping(value = "/advisor/{id}")
    public ResponseEntity<?> deleteConseiller(@PathVariable String email) {
        if (email != null && !"".equals(email)) {
            if (conseillerService.delete(email)) {
                return new ResponseEntity<>("Suppression effectuée avec succès", HttpStatus.OK);
            }
        }
        return new ResponseEntity<>("Erreur survenue pendant la suppression", HttpStatus.NOT_ACCEPTABLE);
    }

}
