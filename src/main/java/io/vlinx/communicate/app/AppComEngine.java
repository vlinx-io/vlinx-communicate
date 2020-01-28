package io.vlinx.communicate.app;

import io.vlinx.logging.Logger;

import java.util.Vector;


public class AppComEngine {
    private static Vector<AppMessage> msgList = new Vector<AppMessage>();
    private static Vector<AppMessage> grpMsgList = new Vector<AppMessage>();
    private static Vector<IAppMsgListener> lsnrList = new Vector<IAppMsgListener>();

    private static Thread grpMsgThread;
    private static Thread msgThread;

    protected static void addMessage(AppMessage msg) {
        msgList.add(msg);
    }

    protected static void addGrpMessage(AppMessage msg) {
        grpMsgList.add(msg);
    }

    public static void register(IAppMsgListener lsnr) {
        if (!lsnrList.contains(lsnr)) {
            lsnrList.add(lsnr);
        }
    }

    public static void remove(IAppMsgListener lsnr) {
        lsnrList.remove(lsnr);
    }

    public static void sendMessage(Object target, AppMessage msg) {
        msg.getTargets().add((IAppMsgListener) target);
        addMessage(msg);
    }

    public static void msgEngineStart() {
        Runnable runnable = new Runnable() {

            @Override
            public void run() {
                while (true) {
                    try {
                        Thread.sleep(10);
                    } catch (InterruptedException e) {
                        Logger.ERROR(e);
                    }

                    for (int i = 0; i < msgList.size(); i++) {
                        AppMessage msg = msgList.get(i);

                        for (int j = 0; j < lsnrList.size(); j++) {
                            IAppMsgListener lsnr = lsnrList.get(j);

                            for (int k = 0; k < msg.getTargets().size(); k++) {
                                IAppMsgListener target = msg.getTargets()
                                        .get(k);

                                if (target.equals(lsnr)) {
                                    lsnr.handleAppMsg(msg);
                                    msg.getTargets().remove(lsnr);
                                }
                            }
                        }

                        if (msg.getTargets().isEmpty()) {
                            msgList.remove(msg);
                        }
                    }

                }
            }
        };

        msgThread = new Thread(runnable);
        msgThread.setDaemon(true);
        msgThread.start();
    }

    public static void groupMsgEngineStart() {

    }

    public static void start() {
        Logger.INFO("Communicate Engine Started");
        AppComEngine.msgEngineStart();
        AppComEngine.groupMsgEngineStart();
    }

}
