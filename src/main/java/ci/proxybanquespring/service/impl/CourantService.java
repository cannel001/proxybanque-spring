/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.domaine.Current;
import ci.proxybanquespring.domaine.Customer;
import ci.proxybanquespring.repository.CourantRepository;
import ci.proxybanquespring.service.ICourantService;
import ci.proxybanquespring.service.ISendEmailService;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author willi
 */
@Service
public class CourantService implements ICourantService {

    //les proprietes
    @Autowired
    private CourantRepository courantRepository;

    @Autowired
    private ClientService clientService;

    @Autowired
    private ConseillerService conseillerService;

    @Autowired
    private IbanCiService ibanCiService;

    @Autowired
    private ISendEmailService sendEmailService;

    @Override
    public Boolean verifNumeroCompte(String numero) {

        if (!"".equals(numero)) {
            return courantRepository.findByNumCptAndEnabledTrue(numero) != null;
        }
        return false;

    }

    @Override
    public List<Current> readAllParClient(Long idClient) {

        if (idClient > 0) {
            Customer client = clientService.readOne(idClient);
            return courantRepository.findByClientAndEnabledTrue(client);
        }
        return null;

    }

    @Override
    public int countByConseiller(String email) {

        int cpt = 0;

        if (!"".equals(email)) {

            //recuperation de la liste de tous les clients
            List<Customer> listClient = clientService.readAllByConseiller(conseillerService.readOne(email));

            //compter le nombre des accounts pour chaque clients si la liste des clients est different de null
            if (!listClient.isEmpty()) {

                for (Customer client : listClient) {
                    if (!readAllParClient(client.getId()).isEmpty()) {
                        cpt += readAllParClient(client.getId()).size();
                    }
                }
            }

        }
        return cpt;

    }

    @Override
    public Current create(Current t) {

        if (t != null) {
            t.setDateCreation(new Date());
            t.setDateUpdate(new Date());
            t.setEnabled(true);
            t.setNumCpt(ibanCiService.generate().toString());
            t.setSolde(t.getMontantInitial());

            Current currentRetourne = courantRepository.save(t);

            if (currentRetourne != null) {
                //envoi de l'email au client
                String nomDestinataire = "Moi";
                String emailDestinataire = currentRetourne.getClient().getEmail();
                String messageEmail = "La bienvenue sur la plateforme PROXY BANQUE G6\n\nInformations concernant"
                        + " votre account courant"
                        + "\nIBAN : " + currentRetourne.getNumCpt()
                        + "\nDate d'ouverture : " + (new SimpleDateFormat("dd/MM/yyyy")).format(new Date()) 
                        + "\nMontant : "
                        + currentRetourne.getMontantInitial()
                        + "\nSolde : "
                        + currentRetourne.getSolde();
                String sujet = "Ouverture d'un account courant";

                //envoi du mail
                sendEmailService.sendMyEmail(nomDestinataire, emailDestinataire, messageEmail, sujet);
                
                return currentRetourne;
            }
        }
        return null;

    }

    @Override
    public List<Current> readAll() {

        return courantRepository.findByEnabledTrue();

    }

    @Override
    public Current readOne(String pk) {

        if (!"".equals(pk)) {
            return courantRepository.findByNumCptAndEnabledTrue(pk);
        }
        return null;

    }

    @Override
    public Current update(Current t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            return courantRepository.save(t);
        }
        return null;

    }

    @Override
    public Boolean delete(Current t) {

        if (t != null) {
            t.setDateUpdate(new Date());
            t.setEnabled(false);
            return courantRepository.save(t) != null;
        }
        return false;

    }

}
