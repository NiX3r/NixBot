package cz.iliev.threads;

import cz.iliev.utils.CommonUtils;

public class ShutdownThread extends Thread{

    @Override
    public void run(){

        CommonUtils.shutdownBot();

    }

}