/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Virement;
import java.util.List;

/**
 *
 * @author willi
 */
public interface IVirementService{
    
    public Boolean retirer(Virement virement);

    public Boolean verifSolde(String cpt, Double montant);

    public Long virementCompteACompte(String cptDebit, String cptCredit, Double montant);

    public Boolean contrainteCompte(String cpt1, String cpt2);

    public List<Virement> readAllVirementParCompte(String compte);

    public List<Virement> readAllVirementRetirerParCompte(String compte);

    public List<Virement> readAllVirementPrClient(Long idClient);

    public List<Virement> readAllVirementVerserParCompte(String compte);

    public List<Virement> readAllVirementVirePrClient(Long idClient);

    public List<Virement> readAllVirementRecuPrClient(Long idClient);
    
    public Virement readOneVirementVireParRef(Long reference);
    
    public Virement readOneVirementRecuParRef(Long reference);
    
}
