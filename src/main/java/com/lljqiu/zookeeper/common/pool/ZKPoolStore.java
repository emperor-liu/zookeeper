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
public class ZKPoolStore {
    private static String ZK_SERVER_HOST;
    private static String ZK_CLIENT_PORT;
    private static int    ZK_CONNECTION_TIMEOUT;
    private ZooKeeper     zk;

    public ZKPoolStore(Watcher watcher) throws IOException {
        initParams();
        if(watcher != null){
            createPool(watcher);
        }else{
            createPool();
        }
    }
    // 用于封装服务
    /*private static  ZooKeeper instance;
    
    public synchronized  ZooKeeper getInstance() throws IOException{
        if(instance == null)
        {
            instance = new createPool();
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
    public void createPool() throws IOException {
        zk =  new ZooKeeper(ZK_SERVER_HOST + ":" + ZK_CLIENT_PORT, ZK_CONNECTION_TIMEOUT,
                new Watcher() {
                    public void process(WatchedEvent event) {
                        System.out.println("已经触发了" + event.getType() + "事件！");
                    }
                });
    }
    /** 
     * Description： 创建自定义Watcher链接
     * @param watcher
     * @throws IOException
     * @return ZooKeeper
     * @author liujie@lljqiu.com
     **/
    public void createPool(Watcher watcher) throws IOException {
        zk = new ZooKeeper(ZK_SERVER_HOST + ":" + ZK_CLIENT_PORT,ZK_CONNECTION_TIMEOUT,watcher);
    }
    
    /** 
     * Description：关闭当前链接
     * @throws InterruptedException
     * @return void
     * @author liujie@lljqiu.com
     **/
    public void closePool() throws InterruptedException {
        zk.close();
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
    public List<String> getNodeList(String nodePath, boolean watch) throws KeeperException, InterruptedException {
        return zk.getChildren(nodePath, watch);
    }
    /** 
     * Description：
     * <p>Return the list of the children of the node of the given path.</p>
     * <p>If the watch is non-null and the call is successful (no exception is thrown), a watch will be left on the node with the given path.
     * The watch willbe triggered by a successful operation that deletes the node of the given path or creates/delete a child under the node.</p>
     * <p>The list of children returned is not sorted and no guarantee is provided as to its natural or lexical order.</p>
     * <p>A KeeperException with error code KeeperException.NoNode will be thrown if no node with the given path exists.</p>
     * @param nodePath
     * @param watch
     * @throws KeeperException
     * @throws InterruptedException
     * @return List<String>
     * @author liujie@lljqiu.com
     **/
    public List<String> getNodeList(String nodePath, Watcher watch) throws KeeperException, InterruptedException {
        return zk.getChildren(nodePath, watch);
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
     * Description： 获取当前结点状态
     * @param nodePath 节点路径
     * @param watcher 当前节点
     * @throws KeeperException
     * @throws InterruptedException
     * @return Stat
     * @author liujie@lljqiu.com
     **/
    public Stat getNodeStatus(String nodePath, Watcher watcher) throws KeeperException, InterruptedException {
         return zk.exists(nodePath, watcher);
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
    public byte[] getData(String nodePath, boolean watcher) throws KeeperException, InterruptedException {
        //        zk.setData(nodePath, data, version);
        return zk.getData(nodePath, watcher, getNodeStatus(nodePath));
    }
    public byte[] getData(String nodePath, Watcher watcher) throws KeeperException, InterruptedException {
        //        zk.setData(nodePath, data, version);
        return zk.getData(nodePath, watcher, null);
    }
}
