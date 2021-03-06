package dblockcache;

import virtualdisk.DukeVDF;
import common.Constants;
import java.util.concurrent.*;
import java.io.IOException;
import java.util.*;

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


	private final Semaphore readMutex = new Semaphore(1);
	private final Semaphore writeMutex = new Semaphore(1);

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
				return false;
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
				return false;
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
	public synchronized int read(byte[] buffer, int startOffset, int count) {
		// Read in data from block, start write to buffer at location: offset
		try {
			//readMutex.acquire();
			startFetch();
			int numRead = 0; 
			for (int i=0; (i<count) && (startOffset + i != buffer.length); i++) { //iterate over 
				buffer[startOffset + i] = myData[i];
				numRead++; 
				}
			//readMutex.release(); 
			//readMutex.wait();
			return numRead; 
			}
		catch (Exception e)
			{
			e.printStackTrace();
			//readMutex.release();
			return -1;
			}
	}
	

	@Override
	public synchronized int write(byte[] buffer, int startOffset, int count) {
		// Write in data to block, start write to block from buffer location: offset
		try {
			//writeMutex.acquire();
			int numWritten = 0; 
			for (int i=0; (i<count) && (startOffset + i < buffer.length); i++) { //iterate over 
				myData[i] = buffer[startOffset + i];
				numWritten++;
				}
			//writeMutex.release(); 

			startPush();
			//readMutex.wait();
			return numWritten; 
			}
		catch (Exception e)
			{
			//writeMutex.release(); 
			return -1;
			}
	}

	@Override
	public void ioComplete() {
		isClean = true;	
		isValid = true;
		// Do we need ot notify???
		//readMutex.notifyAll();

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
