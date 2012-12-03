package dblockcache;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.*;
import common.Constants;

import virtualdisk.DukeVDF;

public class DukeDBCache extends DBufferCache
{
	
	private static DBufferCache dBufCache; 
	//To use LRU we use a LL where the most recently used blocks
	// are at the beginning (index i=0) of the list
	private LinkedList<DBuffer> cacheList; 
	private LinkedList<Boolean> isHeld;
	
	private DukeDBCache(int cacheSize) 
	{
		super(cacheSize);
		// HashSet<DBuffer> set = new LinkedHashSet<DBuffer>(); 
		// cacheSet = Collections.synchronizedSet(set);
		// TODO Auto-generated constructor stub
		cacheList = new LinkedList<DBuffer>();
		isHeld = new LinkedList<Boolean>();


		// Initialize the new LinkedList
		for (int i =0; i<cacheSize; i++) {
			cacheList.add(new DukeDBuffer(-1, Constants.BLOCK_SIZE));
			isHeld.add(false);
		}

	}
	
	public static DBufferCache getInstance()
	{
		if(dBufCache == null)
		{
			dBufCache = new DukeDBCache(_cacheSize);
		}
		return dBufCache; 
	}
	
	@Override
	public synchronized DBuffer getBlock(int blockID) {
		// TODO Auto-generated method stub
		DBuffer buffer = null;
		
		for (int i=0; i<_cacheSize; i++){
			if ( ((DBuffer) cacheList.get(i)).getBlockID() == blockID && !isHeld.get(i) ) {
				buffer = cacheList.remove(i);
				isHeld.remove(i);
				cacheList.addFirst(buffer); //Add back to front because this is now MRU
				isHeld.addFirst(true); // Mark this buffer as in use, held
				return buffer;
			}
		}

		//If we get here, it means the block wasn't found so now we need to clear
		//out a spot for it
		for (int i=0; i<_cacheSize; i++){
			buffer = cacheList.get(i);
			if ( !buffer.isBusy() && !isHeld.get(i) ) {
				if (!buffer.checkClean() && buffer.checkValid()) //write if the buffer isn't clean
					buffer.startPush();

				cacheList.remove(i); //remove the buffer
				isHeld.remove(i);
				//create a new epty one
				buffer = new DukeDBuffer(blockID, Constants.BLOCK_SIZE);
				cacheList.addFirst(buffer); 
				isHeld.addFirst(true);
				return buffer;
			}

		}

		//If we're here then all of the blocks were busy, unlikely, but possible?


		return null;
	}

	@Override
	public synchronized void releaseBlock(DBuffer buf) 
	{
		int index = cacheList.indexOf(buf); // find the index of the buffer
		DBuffer buffer = cacheList.remove(index); // remove the buffer
		isHeld.remove(index);
		cacheList.addFirst(buffer); // put the buffer in the front
		// as it is now the most recently accessed block
		isHeld.addFirst(false); //we're releasing the block for use now
		//now notify everyone who is waiting on it
		notifyAll();

		
	}

	@Override
	public void sync() {
		DBuffer buffer = null;
		for (int i=0; i<_cacheSize; i++){
			buffer = cacheList.get(i);	
			if (buffer.checkValid() && !buffer.checkClean() )
			{
				buffer.startPush();
				buffer.waitClean();
			}
		}
	}
}
