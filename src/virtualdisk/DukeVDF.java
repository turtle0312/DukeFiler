package virtualdisk;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import common.Constants.DiskOperationType;

import dblockcache.DBuffer;

public class DukeVDF extends VirtualDisk {

	public static Queue<Request> vdfRequests = new LinkedList<Request>();
	private static VirtualDisk vdf;
	
	public static VirtualDisk getInstance()
	{
		if(vdf == null){
			try {
				vdf = new DukeVDF();
				//Thread t = new Thread(new VDFWorker());
				//t.start();				
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
				Request currentRequest = new Request(buf, operation); 
				//vdfRequests.add(request); 
				
				switch(currentRequest.getOperation()){
				case READ:
					try {
						vdf.readBlock(currentRequest.getBuffer());
						currentRequest.getBuffer().ioComplete();
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				case WRITE:
					try {
						vdf.writeBlock(currentRequest.getBuffer());
						currentRequest.getBuffer().ioComplete();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					break;
				}
			}
}
