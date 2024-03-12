package cz.nix3r.threads;

import cz.nix3r.utils.CommonUtils;

public class ShutdownThread extends Thread{

    @Override
    public void run(){

        CommonUtils.shutdownBot();

    }

}