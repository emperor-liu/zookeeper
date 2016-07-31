/**
 * Project Name zookeeper
 * File Name WatcherExample.java
 * Package Name com.lljqiu.zookeeper.watcher
 * Create Time 2016年7月31日
 * Auther：liujie@lljqiu.com
 * Copyright © 2005, 2016, https://www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.zookeeper.watcher;

import java.io.IOException;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/** 
 * ClassName: WatcherExample.java <br>
 * Description: <br>
 * Auther：liujie@lljqiu.com <br>
 * Create Time: 2016年7月31日<br>
 */
public class WatcherExample implements Watcher {

    private String zkPath = "localhost:2181";

    public String getZkPath() {
        return zkPath;
    }

    public void setZkPath(String zkPath) {
        this.zkPath = zkPath;
    }

    public void process(WatchedEvent event) {
     // watcher名称
        System.out.println("watcherName=" + this.getClass().getName());
        // 路径
        System.out.println("watcherPath=" + event.getPath());
        // 时间类型
        System.out.println("eventType=" + event.getType().name());
    }
    
    public static void main(String[] args) {
        WatcherExample wx = new WatcherExample();
        System.out.println("Server Config:"+wx.getZkPath());
        try {
                // 创建zk客户端
            ZooKeeper zk = new ZooKeeper(wx.getZkPath(), 10000, wx);
                //不使用默认的watcher
            zk.getChildren("/watcherEcample", true); //当第二个参数为false则不会监听
            Thread.sleep(100000);
        } catch (IOException e) {
            e.printStackTrace();
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


}
