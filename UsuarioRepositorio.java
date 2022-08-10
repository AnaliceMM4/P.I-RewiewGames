package ifsc.slo.tecinfo.pi.repositorio;

import ifsc.slo.tecinfo.pi.modelo.Usuario;
import org.springframework.data.repository.CrudRepository;
/**
 *
 * @author analice
 */
public interface UsuarioRepositorio extends CrudRepository<Usuario, Integer>{
    
    Usuario findByLogin(String login);
    
    Usuario save(Usuario usuario);
 
}


    