package tests;

import dfs.DFS;
import dfs.DukeDFS;

public class TestProgram {
	public static void main(String[] args){
		DFS dukeDFS = new DukeDFS();
		System.out.println("Create DFile: " + 	dukeDFS.createDFile().getDFileID());
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("Create DFile: " + 	dukeDFS.createDFile().getDFileID());
		System.out.println("Create DFile: " + 	dukeDFS.createDFile().getDFileID());
	}	
}
