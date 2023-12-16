package pe.isil.Saturno_1431.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import pe.isil.Saturno_1431.security.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                //habilitar el inicio de sesion
                .formLogin()
                .loginPage("/login") //login.html
                .permitAll()

                .and()

                //definir los permisos por ruta especifica x role
                .authorizeRequests()
                .antMatchers("/admin/**") //admin/cursos /admin/usuarios etc
                .hasAnyRole("ADMIN")

                .antMatchers("/cursos/*", "/mis-cursos")
                .authenticated() //los usuarios autenticadaso puede acceder a estas rutas

                .anyRequest() //para todas las rutas restantes
                .permitAll() //permite el acceso a la ruta a cualquier peticion

                .and()

                .exceptionHandling().accessDeniedPage("/403")

                .and()

                .logout(logout -> logout.logoutRequestMatcher(
                        new AntPathRequestMatcher("/logout", "GET")).logoutSuccessUrl("/"))

                .userDetailsService(userDetailsServiceImpl)
                ;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder()
    {
        return new BCryptPasswordEncoder();
    }

}
