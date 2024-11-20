package com.scm.scm10.config;

import com.scm.scm10.service.SecurityCustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Autowired
    private SecurityCustomUserDetailsService securityCustomUserDetailsService;

    @Autowired
    private OAuthAuthenticationSuccessHandler oAuthAuthenticationSuccessHandler;
   @Autowired
    private AutheFailureHandler autheFailureHandler;

//    private InMemoryUserDetailsManager inMemoryUserDetailsManager;
//    @Bean
//    public UserDetailsService userDetailsService(){
//        UserDetails user1 = User
//                .withDefaultPasswordEncoder()
//                .username("admin1").password("admin123").
//                roles("USER","ADMIN").
//                build();
//        UserDetails user2 = User
//                .withDefaultPasswordEncoder()
//                .username("admin2").password("admin1234").
//                roles("USER").
//                build();
//        return new InMemoryUserDetailsManager(user1,user2);
//    }
    @Bean
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        //user details service ka object
        daoAuthenticationProvider.setUserDetailsService(securityCustomUserDetailsService);
        //password encoder ka object
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.authorizeHttpRequests(authorize->{
            //authorize.requestMatchers("/home","/register","/services").permitAll();
            authorize.requestMatchers("/user/**").authenticated();
            authorize.anyRequest().permitAll();
        });
        httpSecurity.formLogin(formLogin -> {
            formLogin.loginPage("/login")
                    .loginProcessingUrl("/authenticate")
                    .defaultSuccessUrl("/user/profile", true) // Redirect to dashboard on successful login
                    .failureUrl("/login?error=true")
                    .usernameParameter("email")
                    .passwordParameter("password");
//                    .failureHandler(new AuthenticationFailureHandler() {
//                        @Override
//                        public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
//                            response.sendRedirect("/login?error=true");
//                        }
//                    })
//                    .successHandler(new AuthenticationSuccessHandler() {
//                        @Override
//                        public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
//                            response.sendRedirect("/user/dashboard");
//                        }
//                    });
            formLogin.failureHandler(autheFailureHandler);
        });
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.logout(logoutform->{
           logoutform.logoutUrl("/logout");
           logoutform.logoutSuccessUrl("/login?logout=true");
        });

        //oauth configuration
        httpSecurity.oauth2Login(oauth->{
            oauth.loginPage("/login");
            oauth.successHandler(oAuthAuthenticationSuccessHandler);
        });
        return httpSecurity.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
