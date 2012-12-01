package virtualdisk;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Queue;
import java.util.concurrent.SynchronousQueue;

import common.Constants;
import common.Constants.DiskOperationType;

import dblockcache.DBuffer;

public class DukeVDF extends VirtualDisk {

	public Queue vdfRequests;
	private static VirtualDisk vdf;
	
	public static VirtualDisk getInstance(){
		if(vdf == null){
			try {
				vdf = new DukeVDF();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		return vdf;
	}
	
	private DukeVDF()
			throws FileNotFoundException, IOException {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public synchronized void startRequest(DBuffer buf, DiskOperationType operation) throws IllegalArgumentException, IOException 
			{
		// TODO Auto-generated method stub
				Request request = new Request(buf, operation); 
				vdfRequests.add(request); 
				
		
			}

}
