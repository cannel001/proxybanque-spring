/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ci.proxybanquespring.service.impl;

import ci.proxybanquespring.service.ISendSmsService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.stereotype.Service;

/**
 *
 * @author HP
 */
@Service
public class SendSmsService implements ISendSmsService{

    @Override
    public Boolean sendMySms(String numero, String message) {

        try {

            // This URL is used for sending messages
            String myURI = "https://api.bulksms.com/v1/messages";

            // change these values to match your own account
            String myUsername = "username";
            String myPassword = "password";

            // the details of the message we want to send
            String myData = "{to: \"+225"+numero+"\", body: \""+message+"\"}";

            // build the request based on the supplied settings
            URL url = new URL(myURI);
            HttpURLConnection request = (HttpURLConnection) url.openConnection();
            request.setDoOutput(true);

            // supply the credentials
            String authStr = myUsername + ":" + myPassword;
            String authEncoded = Base64.getEncoder().encodeToString(authStr.getBytes());
            request.setRequestProperty("Authorization", "Basic " + authEncoded);

            try {
                // we want to use HTTP POST
                request.setRequestMethod("POST");
            } catch (ProtocolException ex) {
                Logger.getLogger(SendSmsService.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setRequestProperty("Content-Type", "application/json");

            // write the data to the request
            OutputStreamWriter out = new OutputStreamWriter(request.getOutputStream());
            out.write(myData);
            out.close();

            // try ... catch to handle errors nicely
            try {
                // make the call to the API
                InputStream response = request.getInputStream();
                BufferedReader in = new BufferedReader(new InputStreamReader(response));
                String replyText;
                while ((replyText = in.readLine()) != null) {
                    System.out.println(replyText);
                }
                in.close();
            } catch (IOException ex) {
                System.out.println("An error occurred:" + ex.getMessage());
                BufferedReader in = new BufferedReader(new InputStreamReader(request.getErrorStream()));
                // print the detail that comes with the error
                String replyText;
                while ((replyText = in.readLine()) != null) {
                    System.out.println(replyText);
                }
                in.close();
            }
            request.disconnect();

        } catch (MalformedURLException ex) {
            Logger.getLogger(SendSmsService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(SendSmsService.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;

    }

}
