/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.WithDrawal;

import java.util.List;

/**
 *
 * @author willi
 */
public interface IRetraitsService {

    public WithDrawal create(WithDrawal t);

    public List<WithDrawal> readAll();

    public WithDrawal readOne(Long numOperation);

    public WithDrawal update(WithDrawal t);

    public Boolean delete(WithDrawal t);
    
    public List<WithDrawal> readAllRetraitByClient(Long idClient);

}
