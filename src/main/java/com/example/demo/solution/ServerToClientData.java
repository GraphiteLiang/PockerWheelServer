package com.example.demo.solution;

import com.alibaba.fastjson.JSONObject;

public class ServerToClientData implements BasicData{
	Type dataType;
	
	String address;
	int playerId;// 玩家id
	String name;// 玩家名字
	String message;
	int[] visibleCard;// 一张仅自己可见的手牌【0】，其他所有人可见的牌
	int[] tableCards;// 桌上的公共牌
	int[] coins;// 每个人的金币数量
	int[] scores;
	int epoch;
	int turn;
	
	public ServerToClientData() {
		
	}
	@Override
	public String toJson() {
		JSONObject object = new JSONObject();
		object.put("address", address);
		object.put("dataType", dataType);
		object.put("playerId", playerId);
		object.put("name", name);
		object.put("visibleCard", visibleCard);
		object.put("coins", coins);
		object.put("scores", scores);
		object.put("epoch", epoch);
		object.put("turn", turn);
		return object.toJSONString();
		
	}
	public String toString() {
		return toJson();
	}
	public String getAddress() {
		return address;
	}

}
