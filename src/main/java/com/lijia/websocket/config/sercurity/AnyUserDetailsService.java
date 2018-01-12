package com.lijia.websocket.config.sercurity;

import com.lijia.websocket.mongo.model.User;
import com.lijia.websocket.mongo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnyUserDetailsService implements UserDetailsService {
	private final static String ROLE_TAG = "ROLE_USER";

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userService.getByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("用户不存在");
		}
		List<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new SimpleGrantedAuthority(ROLE_TAG));
		// 用户认证（用户名，密码，权限）
		UserPrincipal userPrincipal = new UserPrincipal(username, user.getPassword(), authorities);
		userPrincipal.setNickname(user.getNickname());
		userPrincipal.setAvatar(user.getAvatar());
		return userPrincipal;
	}

}
