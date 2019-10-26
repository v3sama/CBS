
package com.cbs.config;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.cbs.model.Role;
import com.cbs.model.User;
import com.cbs.services.RoleService;
import com.cbs.services.UserDetailsServiceImpl;
import com.cbs.services.UserService;

@Configuration

@EnableWebSecurity

@ComponentScan(basePackages = "com.cbs.controllers")
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;

	@Autowired
	private DataSource dataSource;

	@Bean
	public BCryptPasswordEncoder passwordEncoder() {
		BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
		return bCryptPasswordEncoder;
	}

	@Bean
	public PersistentTokenRepository persistentTokenRepository() {
		JdbcTokenRepositoryImpl db = new JdbcTokenRepositoryImpl();
		db.setDataSource(dataSource);
		return db;
	}

	@Autowired
	public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
		if (roleService.findByName("ADMIN") == null) {
			Role role = new Role();
			role.setName("ADMIN");
			roleService.addRole(role);

		}
		if (roleService.findByName("MEMBER") == null) {
			Role role = new Role();
			role.setName("MEMBER");
			roleService.addRole(role);
		}
		if (userService.findByEmail("admin") == null) {
			User user = new User();
			user.setActive(true);
			user.setEmail("admin");
			user.setPassword("admin");
			user.setFirstName("admin");
			user.setLastName("admin");
			user.setPhone("095789462");
			

			Set<Role> roles = new HashSet<Role>();
			roles.add(roleService.findByName("ADMIN"));
			user.setRoles(roles);
			userService.add(user);
		}

		// Sét đặt dịch vụ để tìm kiếm User trong Database.
		// Và sét đặt PasswordEncoder.
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());

	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable();

		// Các trang không yêu cầu login

		http.authorizeRequests().antMatchers("/admin/**", "/admin").hasAuthority("ADMIN")
				.antMatchers("/profile/**", "/orders/**", "/datve").hasAuthority("MEMBER").antMatchers("/**")
				.permitAll();

		// Khi người dùng đã login, với vai trò XX.
		// Nhưng truy cập vào trang yêu cầu vai trò YY,
		// Ngoại lệ AccessDeniedException sẽ ném ra.
		http.authorizeRequests().and().exceptionHandling().accessDeniedPage("/403");

		// Cấu hình cho Login Form.
		http.authorizeRequests().and().formLogin()//
				// Submit URL của trang login
				.loginProcessingUrl("/j_spring_security_check") // Submit URL
				.loginPage("/login")//
				.defaultSuccessUrl("/")//
				.failureUrl("/login?error=true")//
				.usernameParameter("username")//
				.passwordParameter("password")
				// Cấu hình cho Logout Page.
				.and().logout().logoutUrl("/logout").logoutSuccessUrl("/")
				// Cấu hình Remember Me.
				.and() //
				.rememberMe().tokenRepository(this.persistentTokenRepository()) //
				.tokenValiditySeconds(1 * 24 * 60 * 60); // 24h

	}

}
