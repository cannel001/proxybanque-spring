package ci.proxybanquespring;

import ci.proxybanquespring.service.IConseillerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProxybanqueSpringApplication implements CommandLineRunner {
    
    //les proprietes
    @Autowired
    private IConseillerService conseillerService;

    public static void main(String[] args) {
        SpringApplication.run(ProxybanqueSpringApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        conseillerService.conseillerParDefaut();

    }
}
