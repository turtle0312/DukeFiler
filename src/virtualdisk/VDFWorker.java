package virtualdisk;

import java.util.Queue;

public class VDFWorker implements Runnable {

	public Queue<Request> vdfRequests;
	public VirtualDisk vdf;
	
	public VDFWorker(Queue vdfRequests){
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
					if(currentRequess)
				}
			}
		}
	}

}
