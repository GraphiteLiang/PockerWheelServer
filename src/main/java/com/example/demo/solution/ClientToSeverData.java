package com.example.demo.solution;

public class ClientToSeverData implements BasicData{
	public Type dataType;
	public int tableId;// 多局游戏的同时进行时的tableid
	public int playerId;// 数据来源的玩家id
	public int cardId;// 进行操作的桌上的牌的id
	@Override
	public String toJson() {
		// TODO Auto-generated method stub
		return null;
	}
}
