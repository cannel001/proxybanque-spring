/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Client;
import ci.proxybanquespring.domaine.Conseiller;
import java.util.Date;
import java.util.List;

/**
 *
 * @author willi
 */
public interface IClientService {
    
    public List<Client> readAllByConseiller(Conseiller conseiller);
    
    public Client create(Client t) ;

    public List<Client> readAll();

    public Client readOne(Long pk);

    public Client update(Client t);

    public Boolean delete(Client t);
    
}
