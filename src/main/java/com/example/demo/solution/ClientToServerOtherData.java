package com.example.demo.solution;

public class ClientToServerOtherData extends BasicData{
	public String address;
	public String message;
	public int operation;// 0创建，1加入，2退出，3刷新界面
	public int tableId;
	public int playerId;
	//{"dataType":1,"address":"127.0.0.1","operation":0, "tableId":1}
}
