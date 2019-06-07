/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Payment;

import java.util.List;

/**
 *
 * @author willi
 */
public interface IVersementService {

    public List<Payment> readAllVersementByClient(Long idClient);

    public List<Payment> readAllVersementParCompte(String compte);

    public Payment create(Payment t);

    public List<Payment> readAll();

    public Payment readOne(Long pk);

    public Payment update(Payment t);

    public Boolean delete(Payment t);

}
