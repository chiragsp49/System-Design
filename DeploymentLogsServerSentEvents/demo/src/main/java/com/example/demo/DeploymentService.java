package com.example.demo;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import com.github.javafaker.Faker;

@Service
public class DeploymentService {
	
	@Autowired
	private Faker faker;
	
	@Value("${file.directory}")
	private String directoryPath;
	
	
	public void createDeploymentFile() throws IOException, Exception {
		String fileName = "\\"+UUID.randomUUID().toString();
		File file = new File(directoryPath+fileName);
		if(file.createNewFile()) {
			FileWriter fr = new FileWriter(file);
			BufferedWriter bw = new BufferedWriter(fr);
			for(int i=0;i<10000;i++) {
				String fakeSentence  = faker.shakespeare().asYouLikeItQuote();
				bw.write(fakeSentence);
				bw.write("\n");
				Thread.sleep(100);
			}
			bw.close();
		
			
		}else {
			throw new Exception("unable to create file");
		}
		
		
		
	}
	
	public List<String> getListOfDeployments() throws IOException{
		return getResourceFiles(directoryPath);
	}
	
	public SseEmitter getDeploymentDetails(String deploymentId) throws Exception {
		SseEmitter  emitter = new SseEmitter();
		String path = directoryPath+"\\"+deploymentId;
		File file = new File(path);
		try(BufferedReader br = new BufferedReader(new FileReader(file))){
			String line = null;
			while((line= br.readLine())!=null) {
				emitter.send(SseEmitter.event().data(line));
			}
			Thread.sleep(100);
			
			emitter.complete();
		}catch(IOException e) {
			e.printStackTrace();
			throw new Exception(e.getMessage());
		}
		return emitter;
	}
	
	private List<String> getResourceFiles(String path) throws IOException {
		List<String> fileNames = new ArrayList<String>();
	    File directory = new File(path);
	    File[] files = directory.listFiles();
	    for(int i=0;i<files.length;i++){
	    	fileNames.add(files[i].getName());
	    }
	    return fileNames;
	}
}
