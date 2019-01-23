/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

import ci.proxybanquespring.domaine.Billetage;
import java.util.Date;
import java.util.List;

/**
 *
 * @author willi
 */
public interface IBilletageService {

    public Billetage create(Billetage t);

    public List<Billetage> readAll();

    public Billetage readOne(Long pk);

    public Billetage update(Billetage t);

    public Boolean delete(Billetage t);

}
