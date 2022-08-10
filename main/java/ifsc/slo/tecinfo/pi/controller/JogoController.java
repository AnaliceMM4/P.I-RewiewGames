package ifsc.slo.tecinfo.pi.controller;

import ifsc.slo.tecinfo.pi.modelo.Avaliacao;
import ifsc.slo.tecinfo.pi.modelo.Jogo;
import ifsc.slo.tecinfo.pi.modelo.Usuario;
import ifsc.slo.tecinfo.pi.repositorio.AvaliacaoRepositorio;
import ifsc.slo.tecinfo.pi.repositorio.JogoRepositorio;
import ifsc.slo.tecinfo.pi.repositorio.UsuarioRepositorio;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
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
@RequestMapping("/jogos/")
public class JogoController {

    private JogoRepositorio JogoRepositorio;
    private AvaliacaoRepositorio avaliacaoRepositorio;
    private UsuarioRepositorio usuarioRepositorio;

    @Autowired
    public JogoController(JogoRepositorio JogoRepositorio, AvaliacaoRepositorio avaliacaoRepositorio, UsuarioRepositorio usuarioRepositorio) {
        this.JogoRepositorio = JogoRepositorio;
        this.avaliacaoRepositorio = avaliacaoRepositorio;
        this.usuarioRepositorio = usuarioRepositorio;
    }

    @GetMapping("cadastrar")
    public String showSignUpForm(Jogo jogo, Model model) {

        model.addAttribute("jogos", JogoRepositorio.findAll());
        return "add-game";
    }

    @GetMapping("mostrar")
    public String showUpdateForm(Model model) {

        model.addAttribute("jogos", JogoRepositorio.findAll());
        return "index";
    }

    @PostMapping("add")
    public String addGame(@Valid Jogo jogo, BindingResult result, Model model, Principal principal) {
        Usuario user = usuarioRepositorio.findByLogin(principal.getName());
        jogo.setUsuario(user);

        JogoRepositorio.save(jogo);
        return "redirect:mostrar";
    }

    @GetMapping("editar/{codJogo}")
    public String showUpdateForm(@PathVariable("codJogo") int codJogo, Model model, Principal principal) {
        Jogo jogo = JogoRepositorio.findById(codJogo)
                .orElseThrow(() -> new IllegalArgumentException("O código do jogo é inválido:" + codJogo));
        if (jogo.getUsuario().getLogin().equals(principal.getName())) {
            model.addAttribute("jogo", jogo);
            return "update-game";
        } else {
            return "redirect:/403";
        }

    }

    @PostMapping("update/{codJogo}")
    public String updateGame(@PathVariable("codJogo") int codJogo, @Valid Jogo jogo, Model model, Principal principal) {
        Usuario user = usuarioRepositorio.findByLogin(principal.getName());
        jogo.setUsuario(user);

        JogoRepositorio.save(jogo);
        return "redirect:/jogos/mostrar";
    }

    @GetMapping("remover/{codJogo}")
    public String deleteGame(@PathVariable("codJogo") int codJogo, Model model, Principal principal) {
        Jogo jogo = JogoRepositorio.findById(codJogo)
                .orElseThrow(() -> new IllegalArgumentException("O código do jogo é inválido:" + codJogo));

        if (jogo.getUsuario().getLogin().equals(principal.getName())) {
            List<Usuario> usuarios = (List) usuarioRepositorio.findAll();
            for (Usuario user : usuarios) {
                user.getJogos().remove(jogo);
            }
            JogoRepositorio.delete(jogo);
            return "redirect:/jogos/mostrar";
        } else {
            return "redirect:/403";
        }
    }

    //@GetMapping("/cadastrar/avaliacao")
    //public String showSignUpForm(Avaliacao avaliacao, Model model) {
    //model.addAttribute("avaliacao", avaliacaoRepositorio.findAll());
    //  return "add-avaliacao";
    //}
    @GetMapping("cadastrar/avaliacao/{codJogo}")
    public String showSignUpForm(@PathVariable("codJogo") int codJogo, Model model) {
        Avaliacao avaliacao = new Avaliacao();
        model.addAttribute("codigoJogo", codJogo);

        model.addAttribute("avaliacao", avaliacao);
        return "add-avaliacao";
    }

    @PostMapping("/add/avaliacao/{codJogo}")
    public String addAvaliacao(@PathVariable("codJogo") int codJogo, @Valid Avaliacao avaliacao, BindingResult result, Model model) {
        Jogo jogo = JogoRepositorio.findById(codJogo)
                .orElseThrow(() -> new IllegalArgumentException("O código do jogo é inválido:" + codJogo));
        jogo.getAvaliacoes().add(avaliacao);
        jogo.calcular();

        avaliacao.setJogo(jogo);

        JogoRepositorio.save(jogo);

        return "redirect:/jogos/mostrar";
    }

    @GetMapping("/atualizar/gostei/{codAvaliacao}")
    public String showUpForm(@PathVariable("codAvaliacao") int codAvaliacao, Model model) {
        Avaliacao avaliacao = avaliacaoRepositorio.findById(codAvaliacao)
                .orElseThrow(() -> new IllegalArgumentException("O código da avaliação é inválido:" + codAvaliacao));
        //model.addAttribute("avaliacao", avaliacao);

        avaliacao.setGostei(avaliacao.getGostei() + 1);

        avaliacaoRepositorio.save(avaliacao);
        return "redirect:/jogos/mostrar";
    }

    @GetMapping("/atualizar/naogostei/{codAvaliacao}")
    public String showDownForm(@PathVariable("codAvaliacao") int codAvaliacao, Model model) {
        Avaliacao avaliacao = avaliacaoRepositorio.findById(codAvaliacao)
                .orElseThrow(() -> new IllegalArgumentException("O código da avaliação é inválido:" + codAvaliacao));
        //model.addAttribute("avaliacao", avaliacao);

        avaliacao.setNaoGostei(avaliacao.getNaoGostei() + 1);

        avaliacaoRepositorio.save(avaliacao);
        return "redirect:/jogos/mostrar";
    }

}
