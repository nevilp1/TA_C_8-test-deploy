package apap.tugas.sipayroll.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http
                .authorizeRequests()
                .antMatchers("/css/**").permitAll()
                .antMatchers("/js/**").permitAll()
                .antMatchers("/gaji/tambah").hasAnyAuthority("Kepala Departemen HR","Staff Payroll")
                .antMatchers("/gaji/ubah-status/**").hasAuthority("Kepala Departemen HR")
                .antMatchers("/gaji").hasAnyAuthority("Kepala Departemen HR","Staff Payroll","Karyawan")
                .antMatchers("/lembur/tambah").hasAnyAuthority("Karyawan")
                .antMatchers("/lembur").hasAnyAuthority("Kepala Departemen HR","Staff Payroll","Karyawan")
                .antMatchers("/bonus/add").hasAnyAuthority("Kepala Departemen HR","Kepala Bagian")
                .antMatchers("/api/**").permitAll()
                .antMatchers("/gaji/hapus/**").hasAnyAuthority("Kepala Departemen HR","Staff Payroll")
                .antMatchers("/gaji/ubah/**").hasAnyAuthority("Kepala Departemen HR","Staff Payroll")
                .antMatchers("/lowongan/tambah").hasAnyAuthority("Staff Payroll")
                .antMatchers("/user/tambah").hasAuthority("Kepala Departemen HR")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login").permitAll()
                .and()
                .logout().logoutRequestMatcher(new AntPathRequestMatcher("/logout")).logoutSuccessUrl("/login").permitAll()
                .and()
                .cors()
                .and()
                .csrf()
                .disable();
    }
    @Bean
    public BCryptPasswordEncoder encoder() { return new BCryptPasswordEncoder(); }

    /*
    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception{
        auth.inMemoryAuthentication()
                .passwordEncoder(encoder())
                .withUser("odading").password(encoder().encode("mangoleh"))
                .roles("USER");
    }*/


    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception{
        auth.userDetailsService(userDetailsService).passwordEncoder(encoder());
    }
}