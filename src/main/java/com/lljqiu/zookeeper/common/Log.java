/**
 * Project Name zookeeper
 * File Name Log.java
 * Package Name com.lljqiu.zookeeper.common
 * Create Time 2016年7月31日
 * Auther：liujie@lljqiu.com
 * Copyright © 2005, 2016, https://www.lljqiu.com. All rights reserved.
 */
package com.lljqiu.zookeeper.common;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/** 
 * ClassName: Log.java <br>
 * Description: <br>
 * Auther：liujie@lljqiu.com <br>
 * Create Time: 2016年7月31日<br>
 */
public class Log {
    
    public  Logger LOGGER = null;
    
    public Log(Class clas){
        LOGGER = LoggerFactory.getLogger(clas);
    }
    public  void info(String message){
        System.out.println(message);
        LOGGER.info(message);
    }
    public  void debug(String message){
        System.out.println(message);
        LOGGER.debug(message);
    }
    public  void error(String message){
        System.err.println(message);
        LOGGER.error(message);
    }
    public  void warn(String message){
        System.err.println(message);
        LOGGER.warn(message);
    }
    public  void trace(String message){
        System.err.println(message);
        LOGGER.trace(message);
    }

}
