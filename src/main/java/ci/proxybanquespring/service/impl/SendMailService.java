/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;

/**
 *
 * @author HP
 */
public class SendMailService {

    public static Boolean sendMyEmail(String nameDestinataire, String emailDestinataire,String message,String sujet) {

        try {

            Email email = EmailBuilder.startingBlank()
                    .from("noreply-prepaid@proxybanque.ci", "test@mail.com")
                    .to(nameDestinataire,emailDestinataire)
                    .withSubject(sujet)
                    .withPlainText(message)
                    .buildEmail();

            MailerBuilder
                    .withSMTPServer("smtp.gmail.com", 587, "test@mail.com", "motdepasse")
                    .buildMailer()
                    .sendMail(email);
            return true;

        } catch (Exception e) {
            
            System.out.println(e);
        }

        return false;
    }

}
