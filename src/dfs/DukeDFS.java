package dfs;

import java.util.ArrayList;
import java.util.List;

import common.Constants;
import common.DFileID;
import dblockcache.DBuffer;
import dblockcache.DBufferCache;
import dblockcache.DukeDBCache;

public class DukeDFS extends DFS
{
	private static DFS dfs; 
	
	public DukeDFS(String volName, boolean format) {
		super(volName, format); 
		
	}
	
	public DukeDFS(boolean format) {
		super(Constants.vdiskName,format);

	}

	public DukeDFS() {
		super(Constants.vdiskName,false);
		
	}
	public static DFS getInstance()
	{
		if(dfs == null)
		{
			dfs = new DukeDFS(Constants.vdiskName, false);
		}
		return dfs; 
	}
	
    @Override
    public boolean format() {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public DFileID createDFile() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void destroyDFile(DFileID dFID) {
        // TODO Auto-generated method stub
        // Scan over iNodes and get the DFileID of each
        // one. When we find a match, kill the associated
        // data blocks and then kill the iNode itself
        
    }

	/*
	 * reads the file dfile named by DFileID into the buffer starting from the
	 * buffer offset startOffset; at most count bytes are transferred
	 */
    @Override
    public int read(DFileID dFID, byte[] buffer, int startOffset, int count) 
    {
    	//Get Inode for the file
    	// Calculate blockID for corresponding inode
    	// nothing in block 0 
    	// 1 = dFileIds
    	// 3 - 6 = blockMap
    	// 7 - 518 = inodes
    	// 519 - end = data blocks 
    	 int iNodeBlockID = 7 + dFID.getDFileID();
    	 DBuffer iNodeDBuffer = DukeDBCache.getInstance().getBlock(iNodeBlockID);
    	 byte[] iNodeBuffer = new byte[Constants.BLOCK_SIZE];
    	 iNodeDBuffer.read(iNodeBuffer, 0, Constants.BLOCK_SIZE);
    	 
    	 int fileSize = 0;
    	 byte[] fileSizeBytes = new byte[4];
    	 for(int i=0;i<4;i++){
    		 fileSizeBytes[i] = iNodeBuffer[i];
    	 }
    	 
    	 fileSize = java.nio.ByteBuffer.wrap(fileSizeBytes).getInt();
    	
    	//Get corresponding blockIDs for the file from the Inode
    	
    	List<Integer> listOfBlockIDs = new ArrayList<Integer>(); 

    	
    	for(int i=0;i<fileSize-4; i+=4)
    	{
    		byte[] byteArray = new byte[4]; 
    		byteArray[0] = iNodeBuffer[i+4]; 
    		byteArray[1] = iNodeBuffer[i+5]; 
    		byteArray[2] = iNodeBuffer[i+6]; 
    		byteArray[3] = iNodeBuffer[i+7];
    		int blockID = java.nio.ByteBuffer.wrap(byteArray).getInt(); 
    		listOfBlockIDs.add(blockID); 
    	}
    	
    
    	int numRead; 

    	for(Integer i : listOfBlockIDs)
    	{
    		if(!(count <= 0) && !(startOffset >= buffer.length))
    		{  	
	    		DBuffer buf = DukeDBCache.getInstance().getBlock(i);     		
	    		numRead = buf.read(buffer, startOffset, count);     		
	    		count = count - numRead; 
	    		startOffset = startOffset + numRead;     		
    		}
    	}
    	
    	
    	//Fetch corresponding DBuffer from DBufferCache using block ID
    	//Read data from those blocks into the byte[] up till count bytes
    	
    	return 0;
    }

    
    /*
     * writes to the file specified by DFileID from the buffer starting from the
     * buffer offset startOffsetl at most count bytes are transferred
     */
    @Override
    public int write(DFileID dFID, byte[] buffer, int startOffset, int count) {
        // TODO Auto-generated method stub
        // Convert the filename/dFID and filesize to byte[]
        // Write that data as the metadata to an iNode in the VDS
        // then store the buffer in blocks and reference
        // those blockIDs, sequentially, in iNode
    	
    	List<Integer> listOfBlockIDs = new ArrayList<Integer>(); 
    	int numWritten; 
    	for(Integer i : listOfBlockIDs)
    	{
    		if(!(count <= 0) && !(startOffset >= buffer.length))
    		{  	
	    		DBuffer buf = DukeDBCache.getInstance().getBlock(i);     		
	    		numWritten = buf.write(buffer, startOffset, count);     		
	    		count = count - numWritten; 
	    		startOffset = startOffset + numWritten;     		
    		}
    	}	
        return 0;
    }

    @Override
    public int sizeDFile(DFileID dFID) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public List<DFileID> listAllDFiles() {
        // TODO Auto-generated method stub
        return dFiles; 
    }

}
