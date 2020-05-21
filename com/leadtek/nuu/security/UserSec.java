package com.leadtek.nuu.security;

import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


public class UserSec implements UserDetails {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String username;// 
	private String name;// 
    private String password;// 
    private boolean isEnabled;
    
    private Collection<GrantedAuthority> grantedAuthorities;
    
    public UserSec(String username, String password) {
        this.username = username;
        this.password = password;
    }
    
    public UserSec(String username, String password, boolean isEnabled, List<GrantedAuthority> authSet) {
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.grantedAuthorities = authSet;
    }

    public UserSec(String username, String password, boolean isEnabled, List<GrantedAuthority> authSet, String name) {
        this.username = username;
        this.password = password;
        this.isEnabled = isEnabled;
        this.grantedAuthorities = authSet;
        this.name = name;
    }
    
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return this.grantedAuthorities; 
	}

	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	public String getUsername() {
		// TODO Auto-generated method stub
		return this.username;
	}

	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return this.isEnabled;
	}

	public String getName() {
		return this.name;
	}
}	
