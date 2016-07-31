/**
 * Project Name zookeeper
 * File Name ZKPoolRegister.java
 * Package Name com.lljqiu.zookeeper.common.pool
 * Create Time 2016年7月31日
 * Auther：liujie@lljqiu.com
 * Copyright © 2005, 2016, https://www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.zookeeper.common.pool;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import com.lljqiu.zookeeper.watcher.WatcherExample;

/** 
 * ClassName: ZKPoolRegister.java <br>
 * Description: <br>
 * Auther：liujie@lljqiu.com <br>
 * Create Time: 2016年7月31日<br>
 */
public class ZKPoolRegister implements Watcher {

    static ZKPoolStore zkPool;
    
    
   /* public ZKPoolRegister() throws IOException{
        zkPool = new ZKPoolStore(null);
    }*/
   static void initWatcher(ZKPoolRegister register) throws IOException{
        zkPool = new ZKPoolStore(register);
    }
    /* (non-Javadoc)
     * @see org.apache.zookeeper.Watcher#process(org.apache.zookeeper.WatchedEvent)
     */
    public void process(WatchedEvent event) {
        System.out.println("watcherName=" + this.getClass().getName());
        System.out.println("watcherPath=" + event.getPath());
        System.out.println("eventType=" + event.getType().name());
        try{
            WatcherExample we = new WatcherExample();
            zkPool = new ZKPoolStore(we);
            zkPool.getData(event.getPath(), true);
        }catch (Exception e) {
        }
    }
    public static void main(String[] args) throws KeeperException, InterruptedException, IOException {
        ZKPoolRegister register = new ZKPoolRegister();
        initWatcher(register);
        zkPool.getNodeList("/watcherEcample",register);
        Thread.sleep(100000);
    }

}
