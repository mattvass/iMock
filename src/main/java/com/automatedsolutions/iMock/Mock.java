package com.automatedsolutions.iMock;

import java.util.List;

public interface Mock {

	Server server();
	
	Imposter imposter();
	
	interface Server {
		void start();
		
		void start(boolean debug);
		
		void stop();
		
		boolean isRunning();
	}
	
	interface Imposter {
		Imposter protocol(String protocol);
		
		Imposter port(int port);
		
		Stub stub();		
	}
	
	interface Stub {
		Stub decorate(String value);
		
		Is is();
		
		Proxies proxies();       
		
		Behavior behaviors();
	}
	
	interface Is {
		Predicate predicate();
	}
	
	interface Proxies {
		Proxies to();
		
		Proxies mode();
		
		Proxies predicateGenerators(List<Predicate> predicates);
		
		Proxies cert(String cert);
		
		Proxies key(String key);
	}
	
	interface Predicate {
		Predicate equals();
		
		Predicate deepEquals();
		
		Predicate contains();
		
		Predicate startsWith();
		
		Predicate endsWith();
		
		Predicate matches();
		
		Predicate exists();
		
		Predicate not();
		
		Predicate or();
		
		Predicate and();
		
		Predicate inject();
	}
		
	interface Behavior {
		Behavior wait(int milliseconds);
		
		Behavior decorate(String value);
	}
}
