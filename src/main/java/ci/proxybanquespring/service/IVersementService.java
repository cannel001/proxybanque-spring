/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Versement;
import java.util.Date;
import java.util.List;

/**
 *
 * @author willi
 */
public interface IVersementService {

    public List<Versement> readAllVersementByClient(Long idClient);

    public List<Versement> readAllVersementParCompte(String compte);

    public Versement create(Versement t);

    public List<Versement> readAll();

    public Versement readOne(Long pk);

    public Versement update(Versement t);

    public Boolean delete(Versement t);

}
