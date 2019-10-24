package com.cbs.dto;

import java.util.Collection;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.cbs.model.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CustomUserDetail  implements UserDetails{

    private static final long serialVersionUID = 1L;
    private User user;

    Set<GrantedAuthority> authorities=null;
    


    public String getFname() {
        return user.getFirstName() + " " + user.getLastName();
    }
    
    public Long getUserId() {
        return user.getId();
    }
    
    public String getPassword() {
        return user.getPassword();
    }

    public String getUsername() {
        return user.getEmail();
    }

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return user.isActive();
	}


}
