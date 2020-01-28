package io.vlinx.communicate.app;

public class AppMsgUtil {

	public static void sendMessage(AppMessage message, IAppMsgListener listener) {
		if (listener != null) {
			listener.handleAppMsg(message);
		}
	}
	
	public static void sendMessage(String command,String description,IAppMsgListener listener){
		AppMessage message = new AppMessage(command);
		message.setDescription(description);
		sendMessage(message, listener);
	}

}
