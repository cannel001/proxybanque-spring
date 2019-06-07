/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Transfer;

import java.util.List;

/**
 *
 * @author willi
 */
public interface IVirementService {

    public Boolean retirer(Transfer transfer);
    
    public Boolean verser(Transfer transfer);

    public Long virementCompteACompte(String cptDebit, String cptCredit, Double montant);

    public Boolean contrainteCompte(String cpt1, String cpt2);

    public Transfer create(Transfer t);

    public List<Transfer> readAll();

    public Transfer readOne(Long pk);

    public Transfer update(Transfer t);

    public Boolean delete(Transfer t);
    
    public Transfer readOneVirementVireParRef(Long reference);
    
    public Transfer readOneVirementRecuParRef(Long reference);
    
    public List<Transfer> readAllVirementByClientAndVerser(Long idClient);
    
    public List<Transfer> readAllVirementByClientAndRetirer(Long idClient);

}
