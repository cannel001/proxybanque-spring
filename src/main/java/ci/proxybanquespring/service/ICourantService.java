/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Current;

import java.util.List;

/**
 *
 * @author willi
 */
public interface ICourantService {

    public Boolean verifNumeroCompte(String numero);

    public List<Current> readAllParClient(Long idClient);

    public int countByConseiller(String email);

    public Current create(Current t);

    public List<Current> readAll();

    public Current readOne(String pk);

    public Current update(Current t);

    public Boolean delete(Current t);

}
