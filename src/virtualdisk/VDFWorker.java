package virtualdisk;

<<<<<<< HEAD
public class VDFWorker implements Runnable 
{
=======
import java.io.IOException;
import java.util.Queue;

import common.Constants.DiskOperationType;

public class VDFWorker implements Runnable {
>>>>>>> 90e2bb8611e5be08a79ef48a738b0efbc5e7312b

	public Queue<Request> vdfRequests;
	public VirtualDisk vdf;
	
	public VDFWorker(Queue<Request> vdfRequests){
		this.vdfRequests = vdfRequests;
		this.vdf = DukeVDF.getInstance();
	}
	
	@Override
<<<<<<< HEAD
	public void run() 
	{
		
=======
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
>>>>>>> 90e2bb8611e5be08a79ef48a738b0efbc5e7312b
	}

}
