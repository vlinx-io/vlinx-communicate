package io.vlinx.communicate.app;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AppMessage {
	private String command = "";
	private String description = "";

	private List<IAppMsgListener> targets = new ArrayList<IAppMsgListener>();
	private Map<String, Object> data = new HashMap<String, Object>();
	private boolean group = false;

	public AppMessage(String command) {
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

	public void setData(String key, Object value) {
		this.data.put(key, value);
	}

	public Object getData(String key) {
		return this.data.get(key);
	}

	public Map<String, Object> getObjects() {
		return data;
	}

	public void setObjects(HashMap<String, Object> objects) {
		this.data = objects;
	}

	public boolean isGroup() {
		return group;
	}

	public void setGroup(boolean group) {
		this.group = group;
	}

	public boolean getGroup() {
		return this.group;
	}

	public List<IAppMsgListener> getTargets() {
		return targets;
	}

}
