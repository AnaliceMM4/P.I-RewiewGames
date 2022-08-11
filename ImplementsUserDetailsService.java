/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ifsc.slo.tecinfo.pi.security;

import ifsc.slo.tecinfo.pi.modelo.Role;
import ifsc.slo.tecinfo.pi.modelo.Usuario;
import ifsc.slo.tecinfo.pi.repositorio.UsuarioRepositorio;
import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Repository;

/**
 *
 * @author analice
 */
@Repository
@Transactional
public class ImplementsUserDetailsService implements UserDetailsService {

    @Autowired
    private UsuarioRepositorio ur;
    
    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        Usuario usuario = ur.findByLogin(login);
        System.out.println("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb");
        if(usuario == null){
            throw new UsernameNotFoundException("Usuario nao encontrado!"); 
        }
        return new User(usuario.getLogin(), usuario.getSenha(), true,true,true,true,usuario.getAuthorities());
    } 

    public Usuario save(Usuario user){
        Usuario usuario = new Usuario();
        usuario.setNome(user.getNome());
        usuario.setEmail(user.getEmail()); 
        usuario.setLogin(user.getLogin());
        usuario.setSenha(new BCryptPasswordEncoder(). encode (user.getSenha()));
        List<Role> roles = new ArrayList<>();
        roles.add(new Role("ROLE_USER"));
        usuario.setRoles(roles);

    return ur.save(usuario);
            
   }
}
