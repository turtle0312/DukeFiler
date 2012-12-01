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
	private Set<DBuffer> cacheSet; 
	
	private DukeDBCache(int cacheSize) 
	{
		super(cacheSize);
		HashSet<DBuffer> set = new LinkedHashSet<DBuffer>(); 
		cacheSet = Collections.synchronizedSet(set);
		// TODO Auto-generated constructor stub
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
	public DBuffer getBlock(int blockID) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void releaseBlock(DBuffer buf) 
	{
	
		
	}

	@Override
	public void sync() {
		// TODO Auto-generated method stub
		
	}

}
