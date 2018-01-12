package com.lijia.websocket.api;

import com.lijia.websocket.config.sercurity.UserPrincipal;
import com.lijia.websocket.mongo.model.User;
import com.lijia.websocket.mongo.service.RelationService;
import com.lijia.websocket.mongo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

/**
 * 通用 API Created by Silence on 2017/4/22.
 */
@RestController
@RequestMapping("/api/common")
public class CommonAPI {

	@Autowired
	private UserService userService;

	@Autowired
	private RelationService relationService;

	@PostMapping(value = "/register")
	public boolean register(HttpServletRequest request) {
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		User user = new User(username, password, nickname);
		// TODO 参数校验
		return userService.addUser(user);
	}

	@PostMapping("/add")
	public boolean add(@AuthenticationPrincipal UserPrincipal userPrincipal, @RequestParam String friend) {
		return relationService.addFriend(userPrincipal.getUsername(), friend);
	}

}
