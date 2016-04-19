package com.automatedsolutions.iMock;

import com.automatedsolutions.iMock.mbank.MBMock;

public class MockFactory {
	/*
	 * List of backends supported by iMock;
	 */
	public enum Backend {
		MOUNTEBANK
	}
	
	/*
	 * Mock factory
	 */
	public Mock getMockFactory(Backend backend){
		switch(backend){
		case MOUNTEBANK:
			return new MBMock();
		}
		
		return null;
	}
}
