package com.example.demo.solution;

import java.util.Arrays;

import com.alibaba.fastjson.JSONObject;

public class ServerToClientData extends BasicData{
	int playerId;// 玩家id
	String name;// 玩家名字
	String message;
	int handCard;
	int[][] visibleCards;//二维数组，[i][j]表示第i个玩家的场上可见的第j张牌
	int[] tableCards;// 桌上的公共牌
	int[] coins;// 每个人的金币数量
	int[] scores;
	int epoch;
	int turn;
	
	public ServerToClientData() {
		this.visibleCards = new int[4][4];
		for(int i=0;i<4;i++)Arrays.fill(visibleCards[i], -1);
		this.handCard = -1;
		this.tableCards = new int[10];
		Arrays.fill(tableCards, -1);
		this.coins = new int[4];
		this.scores = new int[4];
	}
	public ServerToClientData(String address, Type datatype) {
		this.address = address;
		this.dataType = datatype;
	}
	@Override
	public String toJson() {
		JSONObject object = new JSONObject();
		object.put("address", address);
		object.put("dataType", dataType);
		object.put("playerId", playerId);
		object.put("name", name);
		object.put("message", message);
		object.put("visibleCards", visibleCards);
		object.put("tableCards", tableCards);
		object.put("handCard", handCard);
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
