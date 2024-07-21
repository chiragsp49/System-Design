package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class ShardController {
	@Autowired
	private UserService userService;
	
	@GetMapping("/{userId}")
	@ResponseStatus(value=HttpStatus.OK)
	public String getUsers(@PathVariable(value="userId")int userId) throws Exception {
		return userService.getUserName(userId);
	}
	
}
