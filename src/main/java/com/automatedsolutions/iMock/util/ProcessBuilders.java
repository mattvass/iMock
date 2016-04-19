package com.automatedsolutions.iMock.util;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class ProcessBuilders {

	public static void build(List<String> commands, String workingDir) throws IOException{
		ProcessBuilder process = new ProcessBuilder(commands);
		process.directory(new File(workingDir));
		process.start();
	}
}
