package dblockcache;

import virtualdisk.DukeVDF;
import common.Constants;

public class DukeDBuffer extends DBuffer
{	
	//ID for block that this buffer is attached to
	private int myBlockID; 
	//data that this block buffer contains 
	private byte[] myData;

	// Is the buffer data valid?
	private boolean isValid = false;
	// Is the clean? / Has it NOT been modified
	private boolean isClean = true;
	private boolean isBusy = false; // are we busy?

	//Parker is so smart and good looking
	public DukeDBuffer(int blockID, int bufferSize) {
		myBlockID = blockID;
		myData = new byte[bufferSize];

	}

	@Override
	public void startFetch() {
		// Start the read on the VDF
		try {
			DukeVDF.getInstance().startRequest(this,
				Constants.DiskOperationType.READ); 
		}
		catch (IllegalArgumentException e){
			System.out.println("IllegalArgumentException, way to go loser.");
		}
		catch (IOException e){
			System.out.println("IOException, way to go loser.");
		}
	}

	@Override
	public void startPush() {
		// Start the write on the VDF
		try {
			DukeVDF.getInstance().startRequest(this,
				Constants.DiskOperationType.WRITE); 
		}
		catch (IllegalArgumentException e){
			System.out.println("IllegalArgumentException, way to go loser.");
		}
		catch (IOException e){
			System.out.println("IOException, way to go loser.");
		}
	}

	@Override
	public boolean checkValid() {
		return isValid;
	}

	@Override
	public boolean waitValid() {
		// Wait until we're valid
		while(!isValid) {
			try { wait (); }
			catch (InterruptedException e) {
				System.out.println("waitValid() failed! DealWithIt.gif");
				return false
			}
		}
		return true;
	}

	@Override
	public boolean checkClean() {
		return isClean;
	}

	@Override
	public boolean waitClean() {
		// Wait until we're clean
		while(!isClean) {
			try { wait (); }
			catch (InterruptedException e) {
				System.out.println("waitClean() failed! DealWithIt.gif");
				return false
			}
		}
		return true;
	}

	@Override
	public boolean isBusy() {
		// TODO Auto-generated method stub
		return isBusy;
	}

	@Override
	public int read(byte[] buffer, int startOffset, int count) {
		// Read in data from block, start write to buffer at location: offset
		holdRead();
		try {
			for (int i=0; i<count; i++) { //iterate over 
				buffer[startOffset + 1] = myData[i];
			}
		catch (Exception e)
			{
			releaseRead(); retun -1;
			}
		releaseRead();
		return 1; //what should we return?
		}
	}

	@Override
	public int write(byte[] buffer, int startOffset, int count) {
		// Write in data to block, start write to block from buffer location: offset
		holdWrite();
		try {
			for (int i=0; i<count; i++) { //iterate over 
				myData[i] = buffer[startOffset + 1]
			}
		catch (Exception e)
			{
			releaseWrite(); retun -1;
			}
		releaseWrite();
		return 1; //what should we return?
		}
	}

	@Override
	public void ioComplete() {
		isClean = true;
		isValid = true;
		// Do we need ot notify???
	}

	@Override
	public int getBlockID() {
		return myBlockID;
	}

	@Override
	public byte[] getBuffer() {
		return myData;
	}

}
