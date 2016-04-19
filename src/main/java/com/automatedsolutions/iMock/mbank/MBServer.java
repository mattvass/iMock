package com.automatedsolutions.iMock.mbank;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.automatedsolutions.iMock.Mock.Server;
import com.automatedsolutions.iMock.util.ProcessBuilders;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class MBServer implements Server {
	private final String BASE_URL = "http://localhost:2525";
	Logger log = LoggerFactory.getLogger(this.getClass().getName());	
	private String mbLocation;
	private boolean debug;
	
	public MBServer(){
		if (System.getenv("MOUNTEBANK_HOME") != null) {
			this.mbLocation = System.getenv("MOUNTEBANK_HOME");
		} else if (System.getProperty("MOUNTEBANK_HOME") != null){
			this.mbLocation = System.getProperty("MOUNTEBANK_HOME");
		} else {
			log.warn("MOUNTEBANK_HOME not found, add the enviroment variable or add this to your properties file.");
		}
	}
		
	@Override
	public void start() {		
		List<String> commands = new ArrayList<String>();
		String os = System.getProperty("os.name").toLowerCase();
		
		if(os.indexOf("mac") >= 0){
			commands.add("./mb");
			commands.add("start");
		} else if (os.indexOf("windows") >= 0){
			commands.add(this.mbLocation + File.separator + "mb.cmd");
		}
		
		if(debug){
			commands.add("--logfile");
			commands.add(System.getProperty("user.dir") + File.separator + "iMock.log");
			commands.add("--loglevel");
			commands.add("debug");
			commands.add("--allowInjection");
			commands.add("--debug");
		}
		
		try {
			ProcessBuilders.build(commands, this.mbLocation);	
			
			// wait 10 seconds for mounte bank to start.
			for(int i = 0; i < 10; i++){
				if(this.isRunningOnPort()){
					return;
				}
				
				Thread.sleep(500);
			}
			throw new IOException("Mounte Bank may not have started or has taken longer to start then allotted time.");
		} catch (IOException e) {
			log.error("Unable to start mounte bank. " + e.getMessage());
		} catch (InterruptedException e) {
			log.error("There was an issue waiting for mounte bank to start. " + e.getMessage());
		} 
	}
	
	@Override
	public void start(boolean debug) {
		this.debug = debug;
		this.start();			
	}
	
	@Override
	public void stop() {
		List<String> commands = new ArrayList<String>();
		String os = System.getProperty("os.name").toLowerCase();
		
		if(os.indexOf("mac") >= 0){
			commands.add("./mb");
			commands.add("stop");
		} else if (os.indexOf("windows") >= 0){
			commands.add(this.mbLocation + File.separator + "mb.cmd");
			commands.add("stop");
		}
		
		try {
			ProcessBuilders.build(commands, this.mbLocation);
			
			for(int i = 0; i < 10; i++){
				if(!this.isRunningOnPort()){
					return;
				}
				
				Thread.sleep(500);
			}
			
			throw new IOException("Mounte Bank may not have stopped or has taken longer to stopped then allotted time.");
		} catch (IOException e) {
			log.error("Unable to stop mounte bank. " + e.getMessage());
		} catch (InterruptedException e) {
			log.error("There was an issue waiting for mounte bank to stop. " + e.getMessage());
		} 
	}

	@Override
	public boolean isRunning() {
		try {
            HttpResponse<JsonNode> response = Unirest.get(BASE_URL).asJson();
            return response.getStatus() == 200;
        } catch (UnirestException e) {
            return false;
        }
	}
	
	private boolean isRunningOnPort(){
		try {
	        // ServerSocket try to open a LOCAL port
	        new ServerSocket(2525).close();
	        // local port can be opened, it's available
	        return false;
	    } catch(IOException e) {
	        // local port cannot be opened, it's in use
	        return true;
	    }
	}
}
