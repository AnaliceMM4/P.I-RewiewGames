package ifsc.slo.tecinfo.pi.controller;

import ifsc.slo.tecinfo.pi.modelo.Avaliacao;
import ifsc.slo.tecinfo.pi.modelo.Jogo;
import ifsc.slo.tecinfo.pi.modelo.Usuario;
import ifsc.slo.tecinfo.pi.repositorio.JogoRepositorio;
import ifsc.slo.tecinfo.pi.repositorio.UsuarioRepositorio;
import ifsc.slo.tecinfo.pi.security.ImplementsUserDetailsService;
import java.security.Principal;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author analice
 */
@Controller
@RequestMapping("usuario")

public class UsuarioController {
    
    
    private ImplementsUserDetailsService usuarioService;
    private JogoRepositorio jogoRepositorio;
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public UsuarioController(ImplementsUserDetailsService usuarioService, JogoRepositorio jogoRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.usuarioService = usuarioService;
        this.jogoRepositorio = jogoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }
    
    
    @GetMapping("/cadastro")
    public String showRegistrationForm(Model model, Usuario usuario) {
        model.addAttribute("usuario", usuario);
        return "cadastro";
    }

    @PostMapping("/cadastro")
    public String registerUserAccount(@Valid Usuario usuario, BindingResult result) {

        usuarioService.save(usuario);
        return "redirect:/index";
    }

   
    @GetMapping("/lista")
    public String showUpdateForm(Model model, Principal principal) {
       Usuario user = usuarioRepositorio.findByLogin(principal.getName());

       model.addAttribute("meusJogos", user.getJogos());
           
       return "lista-pessoal";
    }
    
    
    @GetMapping("/jogos/{codJogo}")
    public String showSignUpForm(@PathVariable("codJogo") int codJogo, Model model, Principal principal) {
        Jogo jogo = jogoRepositorio.findById(codJogo)
                .orElseThrow(() -> new IllegalArgumentException("O código do jogo é inválido:" + codJogo));
       Usuario user = usuarioRepositorio.findByLogin(principal.getName());
       user.getJogos().add(jogo);
       
       usuarioRepositorio.save(user);
       
        return "redirect:/jogos/mostrar";
    }
    @GetMapping("/jogos/remover/{codJogo}")
    public String removeGame(@PathVariable("codJogo") int codJogo, Model model, Principal principal) {
        Jogo jogo = jogoRepositorio.findById(codJogo)
                .orElseThrow(() -> new IllegalArgumentException("O código do jogo é inválido:" + codJogo));
       Usuario user = usuarioRepositorio.findByLogin(principal.getName());
       user.getJogos().remove(jogo);
       usuarioRepositorio.save(user);
        return "redirect:/usuario/lista";
    }
    
    
}
