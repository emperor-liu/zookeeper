/**
 * Project Name zookeeper
 * File Name WatcherRegister.java
 * Package Name com.lljqiu.zookeeper.watcher
 * Create Time 2016年7月31日
 * Auther：liujie@lljqiu.com
 * Copyright © 2005, 2016, https://www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.zookeeper.watcher;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/** 
 * ClassName: WatcherRegister.java <br>
 * Description: <br>
 * Auther：liujie@lljqiu.com <br>
 * Create Time: 2016年7月31日<br>
 */
public class WatcherRegister {

    private ZooKeeper zk;
    public WatcherRegister(String connectString,Watcher watcher){
        try {
            zk = new ZooKeeper(connectString, 10000, watcher);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //测试watcher失效
    public void testWatcherdisable(String path) throws KeeperException, InterruptedException{
        WatcherExample1 we1 = new WatcherExample1();
        we1.setZk(zk);
        zk.getData(path, we1, null);
    }

    public static void main(String[] args) {
        WatcherExample we = new WatcherExample();
        WatcherRegister wr = new WatcherRegister("localhost:2181",we);

        try {
            wr.testWatcherdisable("/watherNode");
            Thread.sleep(1000);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
