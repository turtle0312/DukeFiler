package virtualdisk;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

import common.Constants.DiskOperationType;

import dblockcache.DBuffer;

public class DukeVDF extends VirtualDisk {

	public Queue vdfRequests;
	
	public DukeVDF(String volName, boolean format)
			throws FileNotFoundException, IOException {
		super(volName, format);
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized void startRequest(DBuffer buf, DiskOperationType operation)
			throws IllegalArgumentException, IOException {
		// TODO Auto-generated method stub
		
	}

}
