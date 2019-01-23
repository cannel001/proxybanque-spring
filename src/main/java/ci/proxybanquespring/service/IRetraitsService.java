/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Retraits;
import java.util.Date;
import java.util.List;

/**
 *
 * @author willi
 */
public interface IRetraitsService {

    public Retraits create(Retraits t);

    public List<Retraits> readAll();

    public Retraits readOne(Long numOperation);

    public Retraits update(Retraits t);

    public Boolean delete(Retraits t);
    
    public List<Retraits> readAllRetraitByClient(Long idClient);

}
