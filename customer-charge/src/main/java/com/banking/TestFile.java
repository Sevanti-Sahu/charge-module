package com.banking;

import java.io.IOException;

public  class TestFile {
	
	public static void main(String args[]){
		String path = "C:\\Users\\DELL\\git\\paymentCharge\\customer-charge\\src\\main\\resources\\static\\request.json";
		ReadFileAsString read = new ReadFileAsString();
		String str = new String();
		try {
			 str = read.readFileAsString(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("Exception occured");
			e.printStackTrace();
		}
		System.out.println("sucessfull");
		System.out.println(str);
		
	}

}
