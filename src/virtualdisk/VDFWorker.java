package virtualdisk;

import java.io.IOException;
import java.util.Queue;

import common.Constants.DiskOperationType;

public class VDFWorker implements Runnable {

	public Queue<Request> vdfRequests;
	public VirtualDisk vdf;
	
	public VDFWorker(Queue<Request> vdfRequests){
		this.vdfRequests = vdfRequests;
		this.vdf = DukeVDF.getInstance();
	}
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(vdfRequests != null){	
				if(vdfRequests.size() > 0){
					Request currentRequest = vdfRequests.poll();
					switch(currentRequest.getOperation()){
					case READ:
						try {
							vdf.readBlock(currentRequest.getBuffer());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					case WRITE:
						try {
							vdf.writeBlock(currentRequest.getBuffer());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					currentRequest.getBuffer();
					currentRequest.getOperation();
				}
			}
		}
	}

}
