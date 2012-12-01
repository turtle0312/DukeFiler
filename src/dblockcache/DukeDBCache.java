package dblockcache;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedHashSet;

import virtualdisk.DukeVDF;

public class DukeDBCache extends DBufferCache
{
	
	private static DBufferCache dBufCache; 
	private LinkedHashSet<DBuffer> cacheSet; 
	
	private DukeDBCache(int cacheSize) 
	{
		super(cacheSize);
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
	public void releaseBlock(DBuffer buf) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void sync() {
		// TODO Auto-generated method stub
		
	}

}
