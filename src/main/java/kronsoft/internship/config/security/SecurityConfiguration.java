package kronsoft.internship.config.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import kronsoft.internship.service.UserService;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService userService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		
		http.authorizeRequests().antMatchers(
				"/patient/getByPatientId/*",
				"/patient/editByPatient",
				"/patient/create",
				"/appointment/editByPatient",
				"/appointment/getAppointmentsByPatientId/*",
				"/user/getUserById/*",
				"/appointment/createByPatient",
				"/user/edit")
					.hasAuthority("REGULAR");
		
		http.authorizeRequests().antMatchers(
				"/user/delete/*")
					.hasAnyAuthority("ADMIN","REGULAR");
		
		http.authorizeRequests().antMatchers(
				"/user/getUsers",
				"/user/delete/{id}/",
				"/patient/getPatients",
				"/patient//delete/*",
				"/patient/edit",
				"/appointment/getAppointments",
				"/appointment/edit",
				"/appointment/create",
				"/appointment/delete/{id}/")
					.hasAuthority("ADMIN");
		
		http.authorizeRequests().antMatchers(
				"/user/create")
					.permitAll();
		
		
		
		http.httpBasic()
		.and()
			.headers()
			.frameOptions()
			.sameOrigin()
		.and()
			.authorizeRequests()
			.antMatchers("/**/v2/api-docs",  "/**/swagger-resources/**", "/**/swagger-ui.html", "/**/webjars/**").permitAll()
			.antMatchers("/**").authenticated()
		.and()
			.formLogin()
				.disable()
			.csrf()
				.disable()
			.cors();
		;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
