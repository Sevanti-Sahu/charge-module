package com.banking;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class ReadFileAsString {

	public String readFileAsString(String path) throws IOException{
		File file = new File(path);
		String string = FileUtils.readFileToString(file);
//		System.out.println("Read in: " + string);
		return string;
	}
}
