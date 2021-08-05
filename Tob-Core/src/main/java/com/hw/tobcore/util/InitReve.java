package com.hw.tobcore.util;

//import com.iristar.cn.server.MessageServer;
//import com.iristar.cn.server.MonitorServer;
//import com.iristar.cn.server.RollbackServer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class InitReve {

    private static final Logger log = LoggerFactory.getLogger(InitReve.class);

    /**
     * 初始化
     */
    public void init() {
        try {

//            SocketServer s = null;
//            try {
//                int port = ConfigHelper.getInteger("websocket.port"); // 843 flash policy port
//
//                s = new SocketServer(port);
//                s.start();
//                log.info("WebSocket Server started on port: " + s.getPort());
//
//            }catch( Exception e){
//                log.error(e.getMessage());
//                try{
//                    s.stop();
//                }catch (Exception ex) {
//                    log.error(e.getMessage());
//                }
//            }

//            if(ConfigHelper.getInteger("monitor.enable") == 1) {

//                new Thread(MonitorServer.getInstance()).start();
//                new Thread(RollbackServer.getInstance()).start();
//            }else {
//                new Thread(MessageServer.getInstance()).start();
//            }

            log.info("intial successfully");
        } catch (Exception e) {
            log.error("intial failed！", e);
        }
    }
}
