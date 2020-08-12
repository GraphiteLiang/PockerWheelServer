package com.example.demo.solution;

public class ClientToServerGameData extends BasicData{
	public String address;
	public int tableId;// 多局游戏的同时进行时的tableid
	public int playerId;// 数据来源的玩家id
	public int cardId;// 进行操作的桌上的牌的id
	// {"dataType":1,"address":"127.0.0.1","tableId":0, "playerId":0,"cardId":2}
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
}
