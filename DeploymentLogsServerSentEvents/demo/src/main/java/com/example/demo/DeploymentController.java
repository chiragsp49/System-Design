package com.example.demo;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/deployment")
public class DeploymentController {
	@Autowired
	private DeploymentService deploymentService;
	
	
	@PostMapping(path="")
	@ResponseStatus(value=HttpStatus.CREATED)
	public void createDeployment() throws Exception {
		try {
			deploymentService.createDeploymentFile();
		} catch (Exception e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	@GetMapping("")
	@ResponseStatus(value=HttpStatus.OK)
	public List<String> getDeployments() throws Exception{
		try {
			return deploymentService.getListOfDeployments();
		} catch (IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
	@GetMapping(path="/logs/{deploymentId}",produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	@ResponseStatus()
	public SseEmitter getDeploymentDetails(@PathVariable("deploymentId") String deploymentId) throws Exception{
		try {
			return deploymentService.getDeploymentDetails(deploymentId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
	}
	
}
