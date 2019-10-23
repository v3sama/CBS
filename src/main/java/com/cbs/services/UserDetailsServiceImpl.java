
package com.cbs.services;

import com.cbs.model.Role;
import com.cbs.model.User;
import com.cbs.repository.RoleRepository;
import com.cbs.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

	private final UserRepository userRepository;
	private final RoleRepository roleRepository;

	@Autowired
	public UserDetailsServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
		this.userRepository = userRepository;
		this.roleRepository = roleRepository;
	}

	@Override
	@Transactional
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepository.findByEmail(username);
		List<GrantedAuthority> grantList = new ArrayList<GrantedAuthority>();

		List<String> roleNames = new ArrayList<>();
		for (Role role : user.getRoles()) {
			roleNames.add(role.getName());
		}

		if (roleNames != null) {
			for (String role : roleNames) {
				GrantedAuthority authority = new SimpleGrantedAuthority(role);
				grantList.add(authority);
			}
		}

		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), grantList);
	}
}
