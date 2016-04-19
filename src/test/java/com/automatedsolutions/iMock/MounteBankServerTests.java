package com.automatedsolutions.iMock;

import java.io.IOException;
import org.testng.Assert;
import org.testng.annotations.Test;

public class MounteBankServerTests {

	@Test
	public void startMB() throws IOException{
		System.setProperty("MOUNTEBANK_HOME", "C:\\mountebank-v1.5.1-win-x86");
		Mock mock = new MockFactory().getMockFactory(MockFactory.Backend.MOUNTEBANK);
		mock.server().start();
				
		Assert.assertTrue(mock.server().isRunning());
		
	}
	
	@Test 
	public void stopMB(){
		System.setProperty("MOUNTEBANK_HOME", "C:\\mountebank-v1.5.1-win-x86");
		Mock mock = new MockFactory().getMockFactory(MockFactory.Backend.MOUNTEBANK);
		mock.server().stop();
				
		Assert.assertFalse(mock.server().isRunning());
	}
}

