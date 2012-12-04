package tests;

import common.DFileID;

import dfs.DFS;
import dfs.DukeDFS;

public class TestProgram {
	public static void main(String[] args){
		DFS dukeDFS = new DukeDFS();
		int fileID = dukeDFS.createDFile().getDFileID();
		System.out.println("Create DFile: " + 	fileID);
		byte[] testMessage = new String("Test!").getBytes();
		int bytesWritten = dukeDFS.write(new DFileID(fileID), testMessage, 0, testMessage.length);
		System.out.println("Bytes written: " + bytesWritten);
		byte[] readBuffer = new byte[testMessage.length];
		int bytesRead = dukeDFS.read(new DFileID(fileID), readBuffer, 0, testMessage.length);
		String test = new String(readBuffer);
		System.out.println("Test read: " + test);
		System.out.println("Bytes read: " + bytesRead);
	}		
}
