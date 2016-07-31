/**
 * Project Name zookeeper
 * File Name ZKWatcherHelper.java
 * Package Name com.lljqiu.zookeeper.common.pool.wather
 * Create Time 2016年7月31日
 * Auther：liujie@lljqiu.com
 * Copyright © 2005, 2016, https://www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.zookeeper.common.pool.wather;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.Watcher.Event.EventType;
import org.apache.zookeeper.Watcher.Event.KeeperState;

import com.lljqiu.zookeeper.common.Log;
import com.lljqiu.zookeeper.common.pool.ZKPoolStore;

/** 
 * ClassName: ZKWatcherHelper.java <br>
 * Description: <br>
 * Auther：liujie@lljqiu.com <br>
 * Create Time: 2016年7月31日<br>
 */
public class ZKWatcherHelper extends Thread implements Watcher {

    Log                    log   = new Log(ZKWatcherHelper.class);

    private CountDownLatch latch = new CountDownLatch(1);

    ZKWatcherHelper() {
        if (this.getState().compareTo(Thread.State.NEW) == 0) {
            log.info("state: " + this.getState());
        }
    }

    private static final String ROOT_PATH       = "/watcherEcample";
    private static List<String> nodeList;                           // 所要监控的结点的子结点列表
    private ZKPoolStore         zooKeeper;

    public void connect() throws IOException, InterruptedException {
        zooKeeper = new ZKPoolStore(this);
        latch.await();
        this.start();
    }

    public void close() throws InterruptedException {
        if (zooKeeper != null) {
            zooKeeper.closePool();
        }
    }

    public void process(WatchedEvent event) {
        // 连接状态
        KeeperState keeperState = event.getState();
        // 事件类型
        EventType eventType = event.getType();
        if (KeeperState.SyncConnected == keeperState) {
            // 成功连接上ZK服务器
            if (EventType.None == eventType) {
                log.info("zookeeper is ok !");
                latch.countDown();
            }
        }
    }

    public void syncNodes() {
        try {
            connect();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new ZKWatcherHelper().syncNodes();
    }

    @Override
    public void run() {
        synchronized (this) {
            Watcher wc = new Watcher() {
                public void process(WatchedEvent event) {
                    // 结点数据改变之前的结点列表
                    List<String> nodeListBefore = nodeList;
                    // 主结点的数据发生改变时
                    if (event.getType() == EventType.NodeDataChanged) {
                        log.info("Node data changed:" + event.getPath());
                    }
                    if (event.getType() == EventType.NodeDeleted) {
                        log.info("Node deleted:" + event.getPath());
                    }
                    if (event.getType() == EventType.NodeCreated) {
                        log.info("Node created:" + event.getPath());
                    }

                    // 获取更新后的nodelist
                    try {
                        nodeList = zooKeeper.getNodeList(event.getPath(), false);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                        log.error(event.getPath() + " has no child, deleted.");
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    List<String> nodeListNow = nodeList;
                    // 增加结点
                    if (nodeListBefore.size() < nodeListNow.size()) {
                        for (String str : nodeListNow) {
                            if (!nodeListBefore.contains(str)) {
                                log.info("Node created:" + event.getPath() + "/" + str);
                            }
                        }
                    }
                }

            };
            /**
             * 持续监控ROOT_PATH下的结点
             */
            for (;;) {
                try {
                    zooKeeper.getNodeStatus(ROOT_PATH, wc);// 所要监控的主结点
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                try {
                    nodeList = zooKeeper.getNodeList(ROOT_PATH, wc);
                    for (String child : nodeList) {
                        log.info("child: " + child);
                    }
                } catch (KeeperException e) {
                    e.printStackTrace();
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                // 对PATH下的每个结点都设置一个watcher

                for (String nodeName : nodeList) {
                    try {
                        zooKeeper.getNodeStatus(ROOT_PATH + "/" + nodeName, wc);
                    } catch (KeeperException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(5000);// sleep一会，减少CUP占用率
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
