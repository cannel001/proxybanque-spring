/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service;

/**
 *
 * @author willi
 */
public interface ISendSmsService {
    
    public Boolean sendMySms(String numero, String message);
    
}
