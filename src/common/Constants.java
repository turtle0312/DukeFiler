package common;
/*
 * This class contains the global constants used in DFS
 */

public class Constants 
{

	public static final int NUM_OF_BLOCKS = 16384*2; // 2^15
	public static final int BLOCK_SIZE = 1024; // 1kB
	public static final int MAX_DFILES = 512;
	public static final int MAX_FILE_SIZE = 50; // blocks
	public static final int CACHE_SIZE = 512; // blocks

	/* DStore Operation types */
	public enum DiskOperationType 
	{	
		READ, WRITE
	};

	/* Virtual disk file/store name */
	public static final String vdiskName = "DSTORE.dat";
}