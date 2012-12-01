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
	
	public DBuffer getBuffer()
	{
		return myBuf; 
	}
	
	public DiskOperationType getOperation()
	{
		return myOperation; 
	}
}
