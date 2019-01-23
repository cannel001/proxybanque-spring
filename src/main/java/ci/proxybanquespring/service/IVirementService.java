/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Virement;
import java.util.Date;
import java.util.List;

/**
 *
 * @author willi
 */
public interface IVirementService {

    public Boolean retirer(Virement virement);
    
    public Boolean verser(Virement virement);

    public Long virementCompteACompte(String cptDebit, String cptCredit, Double montant);

    public Boolean contrainteCompte(String cpt1, String cpt2);

    public Virement create(Virement t);

    public List<Virement> readAll();

    public Virement readOne(Long pk);

    public Virement update(Virement t);

    public Boolean delete(Virement t);
    
    public Virement readOneVirementVireParRef(Long reference);
    
    public Virement readOneVirementRecuParRef(Long reference);
    
    public List<Virement> readAllVirementByClientAndVerser(Long idClient);
    
    public List<Virement> readAllVirementByClientAndRetirer(Long idClient);

}
