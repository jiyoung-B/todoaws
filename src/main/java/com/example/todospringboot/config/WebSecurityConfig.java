package com.example.todospringboot.config;

import com.example.todospringboot.security.JwtAuthenticationFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.filter.CorsFilter;

@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        // http 시큐리티 빌더
        http.cors() // WebMvcConfig에서 이미 설정했으므로 기본 cors 설정.
            .and()
            .csrf().disable() // csrf는 현재 사용하지 않으므로 disable
            .httpBasic().disable() // token을 사용하므로 basic 인증 disable
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS) // session 기반이 아님을 선언
            .and()
            .authorizeRequests() // /d와 /auth/** 경로는 인증 안 해도 됨.
                .antMatchers("/", "/auth/**").permitAll()
            .anyRequest() // ㅣ와 /auth/**이외의 모든 경로는 인증해야됨.
                .authenticated();


        // filter 등록.
        // 매 요청마다
        // CorsFilter 실행한 후에
        // jwtAuthenticationFilter 실행한다.
        http.addFilterAfter(jwtAuthenticationFilter, CorsFilter.class);
    }


    /*
    WebSecurityConfigurerAdapter를

    @Order(100)
    지원 중단(@Deprecated)
    WebSecurityConfigurerAdapter 대신에 SecurityFilterChain 빈을 사용하여 HttpSecurity를 구성하거나 WebSecurityCustomizer 빈을 사용하여 WebSecurity를 구성
    Use a org.springframework.security.web.SecurityFilterChain Bean to configure HttpSecurity or a WebSecurityCustomizer Bean to configure WebSecurity.

    예시.
      @Bean
      public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
          http
              .authorizeHttpRequests((authz) ->
                  authz.anyRequest().authenticated()
              );
              // ...
          return http.build();
      }

     @Bean
     public WebSecurityCustomizer webSecurityCustomizer() {
         return (web) -> web.ignoring().antMatchers("/resources/**");
     }

    기존: WebSecurityConfigurerAdapter를 상속하고 configure매소드를 오버라이딩하여 설정하는 방법
    => 현재: SecurityFilterChain을 리턴하는 메소드를 빈에 등록하는 방식(컴포넌트 방식으로 컨테이너가 관리)
    //https://spring.io/blog/2022/02/21/spring-security-without-the-websecurityconfigureradapter

    @Override
    protected void configure(HttpSecurity http) throws  Exception{
        http.csrf().disable();
        http.authorizeRequests()
                .antMatchers("/user/**").authenticated()
                .antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
                .antMatchers("/admin").access("\"hasRole('ROLE_ADMIN')")
                .anyRequest().permitAll();
    }

     */


}
