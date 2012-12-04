package virtualdisk;

import java.io.IOException;
import java.util.Queue;

import common.Constants.DiskOperationType;

public class VDFWorker implements Runnable {

	public VirtualDisk vdf;
	
	public VDFWorker(){
		this.vdf = DukeVDF.getInstance();
	}
	
	@Override

	public void run() {
		// TODO Auto-generated method stub
		while(true){
			if(DukeVDF.vdfRequests != null){	
				if(DukeVDF.vdfRequests.size() > 0)
				{
					Request currentRequest = DukeVDF.vdfRequests.poll();
					switch(currentRequest.getOperation()){
					case READ:
						try {
							vdf.readBlock(currentRequest.getBuffer());
						} catch (IOException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						break;
					case WRITE:
						try {
							vdf.writeBlock(currentRequest.getBuffer());
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						break;
					}
				}
			}
		}

	}

}
