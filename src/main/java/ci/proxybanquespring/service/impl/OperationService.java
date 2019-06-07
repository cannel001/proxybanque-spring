/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.repository.OperationRepository;
import ci.proxybanquespring.service.IEpargneService;
import ci.proxybanquespring.service.IOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author willi
 */
@Service
public class OperationService implements IOperationService {

    //les proprietes
    @Autowired
    private NumeroTransactionService numeroTransactionService;

    @Autowired
    private OperationRepository operationRepository;

    @Autowired
    private CourantService courantService;

    @Autowired
    private IEpargneService epargneService;

    @Override
    public Long generateNumTransc() {

        Long newNum = Long.parseLong((String) numeroTransactionService.generate());

        while (operationRepository.findByNumOperationAndEnabledTrue(newNum) != null) {
            newNum = Long.parseLong((String) numeroTransactionService.generate());
        }

        return newNum;

    }

    @Override
    public String typeCompte(String numeroCpt) {

        if (!"".equals(numeroCpt)) {
            if (courantService.readOne(numeroCpt) != null) {
                return "Current";
            } else if (epargneService.readOne(numeroCpt) != null) {
                return "Savings";
            }
        }

        return null;

    }

    @Override
    public Boolean verifSolde(String cpt, Double montant) {

        String typCompte = typeCompte(cpt);

        if (!"".equals(typCompte)) {
            if (typCompte.equals("Current")) {
                if (courantService.readOne(cpt).getSolde() >= montant) {
                    return true;
                }
            } else if (typCompte.equals("Savings")) {
                if (epargneService.readOne(cpt).getSolde() >= montant) {
                    return true;
                }
            }
        }

        return false;
    }

}
