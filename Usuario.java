package ifsc.slo.tecinfo.pi.modelo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 *
 * @author analice
 */
@Entity
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int codUsuario;
    private String nome;
    private String email;
    private String login;
    private String senha; 
    @ManyToMany(cascade = CascadeType.ALL) 
    @JoinTable(
            name = "usuarios_jogos",
            joinColumns = @JoinColumn(
                    name = "usuario_id", referencedColumnName = "codUsuario"),
            inverseJoinColumns = @JoinColumn(
                    name = "jogo_id", referencedColumnName = "codJogo"))
    private List<Jogo> jogos;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Role> roles;
    
    public Usuario(int codUsuario, String nome, String email, String login, String senha, List<Role> roles) {
        this.codUsuario = codUsuario;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.roles = roles;
    }

    public Usuario() {
    }

    public Usuario(int codUsuario, String nome, String email, String login, String senha) {
        this.codUsuario = codUsuario;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
    }

    public Usuario(int codUsuario, String nome, String email, String login, String senha, List<Jogo> jogos, List<Role> roles) {
        this.codUsuario = codUsuario;
        this.nome = nome;
        this.email = email;
        this.login = login;
        this.senha = senha;
        this.jogos = jogos;
        this.roles = roles;
    }
    
    

    public int getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(int codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public List<Jogo> getJogos() {
        return jogos;
    }

    public void setJogos(List<Jogo> jogos) {
        this.jogos = jogos;
   }
   
    public List<Role> getRoles() {
        return roles;
    }

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }
    

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
       roles.add(new Role("ROLE_USER"));
       return (Collection<? extends GrantedAuthority>) this.roles;
    }

    @Override
    public String getPassword() {
        return this.senha;
    }

    @Override
    public String getUsername() {
        return this.login;
    }

    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
