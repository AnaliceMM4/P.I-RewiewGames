package ifsc.slo.tecinfo.pi.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
/**
 *
 * @author analice
 */
@Controller
public class SecurityController {
 
    
    @GetMapping("/index")
    public String index() {
        return "index";
    }
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
 
     @GetMapping("/")
    public String mostrar() {
        return "redirect:/jogos/mostrar";
    }
    
    @GetMapping("/403")
    public String acessoNegado() {
        return "403";
    }
}


