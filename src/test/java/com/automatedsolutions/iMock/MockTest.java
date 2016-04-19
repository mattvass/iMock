package com.automatedsolutions.iMock;

import org.testng.annotations.Test;

import com.automatedsolutions.iMock.mbank.MBMock;

public class MockTest {

	@Test
	public void iMockIterfaceTest() {
		Mock imock = new MBMock();

		/*imock.imposter().protocol("")
			.port(1234).stub().response().is()
			.predicate().deepEquals().startsWith();
		*/
	}
}
