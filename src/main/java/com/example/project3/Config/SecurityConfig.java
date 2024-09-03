package com.example.project3.Config;

import com.example.project3.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MyUserDetailsService myUserDetailsService;

    @Bean
    public DaoAuthenticationProvider daoauthenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(myUserDetailsService);
        provider.setPasswordEncoder(new BCryptPasswordEncoder());
        return provider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(daoauthenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("api/v1/employee/register-employee","api/v1/customer/customer-register").permitAll()
                .requestMatchers("api/v1/customer/update-customer","api/v1/customer/creat-account",
                        "api/v1/customer/view-account-details/","api/v1/customer/view-user-accounts",
                        "api/v1/customer/deposit/","api/v1/customer/withdraw/","api/v1/customer/transfer-funds/").hasAuthority("CUSTOMER")
                .requestMatchers("api/v1/employee/update-employee/").hasAuthority("EMPLOYEE")
                .requestMatchers("api/v1/customer/delete-customer/").hasAnyAuthority("CUSTOMER", "ADMIN")
                .requestMatchers("api/v1/employee/delete-employee/","api/v1/employee/block-account/").hasAnyAuthority("EMPLOYEE", "ADMIN")
                .requestMatchers("api/v1/customer/get-all-customers","api/v1/employee/active-account/").hasAuthority("EMPLOYEE")
                .requestMatchers("api/v1/customer/get-all-customers","api/v1/employee/get-all-employees").hasAuthority("ADMIN")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("api/v1/customer/user-logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }
}
