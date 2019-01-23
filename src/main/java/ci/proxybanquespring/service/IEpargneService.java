/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Courant;
import ci.proxybanquespring.domaine.Epargne;
import java.util.Date;
import java.util.List;

/**
 *
 * @author willi
 */
public interface IEpargneService {

    public String generateNumeroCompte();

    public Boolean verifNumeroCompte(String numero);

    public List<Epargne> readAllParClient(Long idClient);

    public int countByConseiller(String email);

    public Epargne create(Epargne t);

    public List<Epargne> readAll();

    public Epargne readOne(String pk);

    public Epargne update(Epargne t);

    public Boolean delete(Epargne t);

}
