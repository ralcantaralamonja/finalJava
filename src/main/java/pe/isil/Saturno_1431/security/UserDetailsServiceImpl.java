package pe.isil.Saturno_1431.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pe.isil.Saturno_1431.model.Usuario;
import pe.isil.Saturno_1431.repository.UsuarioRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Usuario usuario = usuarioRepository
                .findByEmail(email)
                .orElseThrow(()->new RuntimeException("El usuario no se encontró para el email: " + email));
        return new AppUserDetails(usuario);
    }
}
