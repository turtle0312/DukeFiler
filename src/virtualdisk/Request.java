package virtualdisk;

import common.Constants.DiskOperationType;

import dblockcache.DBuffer;

public class Request 
{
	private DBuffer myBuf; 
	private DiskOperationType myOperation; 
	
	public Request(DBuffer buf, DiskOperationType operation)
	{
		myBuf = buf; 
		myOperation = operation; 
	}
	
	private DBuffer getBuffer()
	{
		return myBuf; 
	}
	
	private DiskOperationType getOperation()
	{
		return myOperation; 
	}
}
