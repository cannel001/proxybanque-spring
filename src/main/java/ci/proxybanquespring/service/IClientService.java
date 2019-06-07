/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Advisor;
import ci.proxybanquespring.domaine.Customer;

import java.util.List;

/**
 *
 * @author willi
 */
public interface IClientService {
    
    public List<Customer> readAllByConseiller(Advisor advisor);
    
    public Customer create(Customer t) ;

    public List<Customer> readAll();

    public Customer readOne(Long pk);

    public Customer update(Customer t);

    public Boolean delete(Customer t);
    
}
