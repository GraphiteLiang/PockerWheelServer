package com.example.demo.solution;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSONObject;

public class RoomInfo extends BasicData{
	public List<Room> info;
	public String address;
	public RoomInfo() {
		info = new ArrayList<Room>();
		
	}
	public void addRoom(int id, String name, int count) {
		info.add(new Room(id, name, count));
	}
	public String toJson() {
		JSONObject object = new JSONObject();
		object.put("address", address);
		object.put("dataType", dataType);
		object.put("info", info);
		return object.toJSONString();
	}
	class Room{
		public int id;
		public String name;
		public int count;
		Room(int id, String name, int count){
			this.id = id;this.name=name;this.count = count;
		}
	}

}
