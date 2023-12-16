package pe.isil.Saturno_1431.security;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pe.isil.Saturno_1431.model.Usuario;

import java.util.Collection;
import java.util.Collections;

public class AppUserDetails implements UserDetails {
    private final Usuario usuario;
    private final String nombre; //nombre que vamos a mostrar en la aplicacion

    public AppUserDetails(Usuario usuario1) {
        this.usuario = usuario1;
        this.nombre = usuario1.getNombres();
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + usuario.getRol().name()));
    }

    @Override
    public String getPassword() {
        return usuario.getPassword();
    }

    @Override
    public String getUsername() {
        return usuario.getEmail();
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

    public String getNombre(){
        return nombre;
    }
}
