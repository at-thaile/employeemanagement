package com.example.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.example.demo.filter.UnBlockUserFilter;

@Configuration
@EnableWebSecurity
public class WebSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private UnBlockUserFilter unBlockUserFilter;

	@Autowired
	private SuccessLoginHandler successLoginHandle;;

	@Autowired
	private FailureLoginHandler failLoginHandle;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder encoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// disable csrf
		http.csrf().disable();

		http.headers().frameOptions().sameOrigin();
		// all request to /login, /registerAccount, /activeAccount, /forget-passowrd,
		// /change-password auto permit
		// and request to url other must authen
		http.authorizeRequests()
				.antMatchers("/forget-password**", "/h2-console/**", "/login**", "/registerAccount**",
						"/activeAccount**", "/change-password**")
				.permitAll();
		
		http.authorizeRequests().antMatchers("/home").authenticated().antMatchers("/admin/**").hasAuthority("ADMIN").and().authorizeRequests().anyRequest().authenticated();

		http.authorizeRequests().antMatchers("/user/**").authenticated();

		// add filter for check time to unblock user
		http.authorizeRequests().and()
				.addFilterBefore(unBlockUserFilter, UsernamePasswordAuthenticationFilter.class).formLogin()
				.loginPage("/login").permitAll().usernameParameter("email").passwordParameter("password")
				.loginProcessingUrl("/login").successHandler(successLoginHandle).failureHandler(failLoginHandle)
				// setting remember me
				.and().rememberMe().rememberMeParameter("remember-me")
				// setting logout
				.and().logout().logoutUrl("/logout").permitAll()
				// delete cookies when logout
				.deleteCookies("JSESSIONID", "remember-me").logoutSuccessUrl("/login?logout").permitAll().and()
				.httpBasic();

//		http.csrf().disable().authorizeRequests().anyRequest().permitAll();
	}

	@Override
	public void configure(org.springframework.security.config.annotation.web.builders.WebSecurity web)
			throws Exception {
		web.ignoring().antMatchers("/css/**", "/util/**", "/images/**", "/js/**","/fonts/**","/vendor/**");
	}

}
