/**
 * Project Name zookeeper
 * File Name ZookeeperPoolStore.java
 * Package Name com.lljqiu.zookeeper.common.pool
 * Create Time 2016年7月29日
 * Auther：liujie@lljqiu.com
 * Copyright © 2005, 2016, https://www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.zookeeper.common.pool;

import java.io.IOException;
import java.util.List;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooDefs.Ids;
import org.apache.zookeeper.ZooKeeper;
import org.apache.zookeeper.data.Stat;

/** 
 * ClassName: ZookeeperPoolStore.java <br>
 * Description: <br>
 * Auther：liujie@lljqiu.com <br>
 * Create Time: 2016年7月29日<br>
 */
public class ZookeeperPoolStore {
    private static String ZK_SERVER_HOST;
    private static String ZK_CLIENT_PORT;
    private static int    ZK_CONNECTION_TIMEOUT;
    private ZooKeeper     zk;

    public ZookeeperPoolStore() throws IOException {
        initParams();
        zk = createPool();
    }
    // 用于封装服务
    /*private static  CreatePool instance;
    
    public synchronized  CreatePool getInstance() throws IOException{
        if(instance == null)
        {
            instance = new CreatePool();
        }
        return instance;
    }*/

    /** 
    * Description：初始化参数值
    * @return void
    * @author liujie@lljqiu.com
     **/
    public static void initParams() {
        ZK_SERVER_HOST = "127.0.0.1";
        ZK_CLIENT_PORT = "2181";
        ZK_CONNECTION_TIMEOUT = 2000;
    }

    /** 
    * Description： 创建链接
    * @return void
    * @author liujie@lljqiu.com
     * @throws IOException 
     **/
    public ZooKeeper createPool() throws IOException {
        return new ZooKeeper(ZK_SERVER_HOST + ":" + ZK_CLIENT_PORT, ZK_CONNECTION_TIMEOUT,
                new Watcher() {
                    public void process(WatchedEvent event) {
                        System.out.println("已经触发了" + event.getType() + "事件！");
                    }
                });
    }

    /** 
    * Description： 创建目录节点
    * @param nodePath 节点路径
    * @param nodeName 节点名称
    * @throws KeeperException
    * @throws InterruptedException
    * @return void
    * @author liujie@lljqiu.com
     **/
    public void createNode(String nodePath, String nodeName)
            throws KeeperException, InterruptedException {
        zk.create(nodePath, nodeName.getBytes(), Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
    }

    /** 
    * Description：获取目录节点列表
    * @param nodePath 节点路径
    * @throws KeeperException
    * @throws InterruptedException
    * @return void
    * @author liujie@lljqiu.com
     **/
    public List<String> getNodeList(String nodePath) throws KeeperException, InterruptedException {
        return zk.getChildren(nodePath, true);
    }

    /** 
    * Description： 删除节点并且删除节点所有数据
    * @param nodePath 节点路径
    * @param version 节点版本号，若为空则初始化为-1可删除任何版本
    * @throws InterruptedException
    * @throws KeeperException
    * @return void
    * @author liujie@lljqiu.com
     **/
    public void deleteNode(String nodePath, int version)
            throws InterruptedException, KeeperException {
        if (version == 0) {
            version = -1;
        }
        zk.delete(nodePath, version);
    }

    /** 
    * Description： 获取当前结点状态
    * @param nodePath  节点路径
    * @throws KeeperException
    * @throws InterruptedException
    * @return Stat 节点状态
    * @author liujie@lljqiu.com
     **/
    public Stat getNodeStatus(String nodePath) throws KeeperException, InterruptedException {
        return zk.exists(nodePath, true);
    }

    /** 
    * Description： 向节点添加数据
    * @param nodePath 节点路径
    * @param data 待添加数据
    * @param version 版本号
    * @throws KeeperException
    * @throws InterruptedException
    * @return void
    * @author liujie@lljqiu.com
     **/
    public void setData(String nodePath, byte[] data, int version)
            throws KeeperException, InterruptedException {
        if (version == 0) {
            version = -1;
        }
        zk.setData(nodePath, data, version);
    }

    /** 
    * Description： 获取数据
    * @param nodePath 节点路径
    * @throws KeeperException
    * @throws InterruptedException
    * @return byte[]
    * @author liujie@lljqiu.com
     **/
    public byte[] getData(String nodePath) throws KeeperException, InterruptedException {
        //        zk.setData(nodePath, data, version);
        return zk.getData(nodePath, true, getNodeStatus(nodePath));
    }
}
