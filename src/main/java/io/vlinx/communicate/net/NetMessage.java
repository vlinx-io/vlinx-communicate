package io.vlinx.communicate.net;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;


public class NetMessage {

	public static final Type type = new TypeToken<NetMessage>() {
	}.getType();

	private String command = "";
	private String description = "";
	private Map<String, Object> data = new HashMap<String, Object>();

	public NetMessage(String command) {
		setCommand(command);
	}

	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Object getData(String key) {
		return data.get(key);
	}
	
	public void setData(String key,Object value){
		data.put(key, value);
	}

	public String getJson() {
		Gson gson = new Gson();
		String json = gson.toJson(this);
		return json;
	}
	
	public String toString(){
		return getJson();
	}

}
