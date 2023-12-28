package com.example.blogsecurity.Config;

import com.example.blogsecurity.Service.MyUserDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration//تعلمينهم انتي وش تكتبين بهالكلاس عشان تسوين السيكيورتي
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final MyUserDetailsService myUserDetailsService;
//بتدور اليوزر باستخدام الداله اللي بداخل ماي يوزر ديتيلزسيرفس
    //هو اللي يدخل يجيب الداتا من الداتا بيس فنعلمه هو بيجيبها من وين
    public DaoAuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider x=new DaoAuthenticationProvider(); //انشئه عشان اخزن فيه الداتا
        x.setUserDetailsService(myUserDetailsService); // اعطيه مكان الميثود اللي بيستخدمها
        x.setPasswordEncoder(new BCryptPasswordEncoder()); // هنا انا اشفره

        return x;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
                .csrf().disable()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests()
                .requestMatchers("/api/v1/auth/register","/api/v1/auth/my-user","/api/v1/title/{title}").permitAll()
                .requestMatchers("/api/v1/auth/getUsers","/api/v1/blog/get","/api/v1/auth/user/{id}","/api/v1/auth/delete/{id}","/api/v1/blog/get").hasAuthority("ADMIN")
                .requestMatchers("/api/v1/blog/get-blogs","/api/v1/blog/add","/api/v1/auth/update","/api/v1/blog/delete","/api/v1/blog/delete").hasAuthority("USER")
//                .requestMatchers("api/v1/auth/get").hasAnyAuthority("ADMIN","COMPANY")
                .anyRequest().authenticated()
                .and()
                .logout().logoutUrl("/api/v1/auth/logout")
                .deleteCookies("JSESSIONID")
                .invalidateHttpSession(true)
                .and()
                .httpBasic();
        return http.build();
    }

}
