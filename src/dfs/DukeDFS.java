package dfs;

import java.util.List;

import common.DFileID;

public class DukeDFS extends DFS{

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

    @Override
    public int read(DFileID dFID, byte[] buffer, int startOffset, int count) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int write(DFileID dFID, byte[] buffer, int startOffset, int count) {
        // TODO Auto-generated method stub
        // Convert the filename/dFID and filesize to byte[]
        // Write that data as the metadata to an iNode in the VDS
        // then store the buffer in blocks and reference
        // those blockIDs, sequentially, in iNode
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
        return null;
    }

}
