package dblockcache;

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


	public DukeDBuffer(int blockID, int bufferSize) {
		myBlockID = blockID;
		myData = new byte[bufferSize];

	}

	@Override
	public void startFetch() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void startPush() {
		// TODO Auto-generated method stub
		
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
		return false;
	}

	@Override
	public int read(byte[] buffer, int startOffset, int count) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int write(byte[] buffer, int startOffset, int count) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void ioComplete() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getBlockID() {
		// TODO Auto-generated method stub
		return myBlockID;
	}

	@Override
	public byte[] getBuffer() {
		// TODO Auto-generated method stub
		return null;
	}

}
