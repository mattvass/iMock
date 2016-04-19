package com.automatedsolutions.iMock.mbank;

import com.automatedsolutions.iMock.Mock;

public class MBMock implements Mock {

	@Override
	public Server server() {
		return new MBServer();
	}

	@Override
	public Imposter imposter() {
		// TODO Auto-generated method stub
		return null;
	}
}
