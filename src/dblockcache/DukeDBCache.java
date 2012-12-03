package dblockcache;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import virtualdisk.DukeVDF;

public class DukeDBCache extends DBufferCache
{
	
	private static DBufferCache dBufCache; 
	//To use LRU we use a LL where the most recently used blocks
	// are at the beginning (index i=0) of the list
	private LinkedList<DBuffer> cacheList; 
	
	private DukeDBCache(int cacheSize) 
	{
		super(cacheSize);
		// HashSet<DBuffer> set = new LinkedHashSet<DBuffer>(); 
		// cacheSet = Collections.synchronizedSet(set);
		// TODO Auto-generated constructor stub
		cacheList = new LinkedList<DBuffer>();

		// Initialize the new LinkedList
		for (int i =0; i<cacheSize; i++)
			cacheList.add(new Buffer(-1, Constants.BLOCK_SIZE));

	}
	
	public DBufferCache getInstance()
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
			if ( ((DBuffer) cacheList.get(i)).getBlockID() == blockID) {
				buffer = myCache.remove(i);
				myCache.addFirst(buffer); //Add back to front because this is now MRU
				return buffer;
			}
		}

		//If we get here, it means the block wasn't found so now we need to clear
		//out a spot for it

		return null;
	}

	@Override
	public synchronized void releaseBlock(DBuffer buf) 
	{
		int index = cacheList.indexOf(buf); // find the index of the buffer
		DBuffer buffer = cacheList.remove(index); // remove the buffer
		cacheList.addFirst(buffer); // put the buffer in the front
		// as it is now the most recently accessed block

		//now notify everyone who is waiting on it
		notifyAll();

		
	}

	@Override
	public void sync() {
		// TODO Auto-generated method stub
		
	}

}
