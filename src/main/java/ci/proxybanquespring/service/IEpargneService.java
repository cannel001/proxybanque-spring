/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Savings;

import java.util.List;

/**
 *
 * @author willi
 */
public interface IEpargneService {

    public String generateNumeroCompte();

    public Boolean verifNumeroCompte(String numero);

    public List<Savings> readAllParClient(Long idClient);

    public int countByConseiller(String email);

    public Savings create(Savings t);

    public List<Savings> readAll();

    public Savings readOne(String pk);

    public Savings update(Savings t);

    public Boolean delete(Savings t);

}
