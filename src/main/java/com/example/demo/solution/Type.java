package com.example.demo.solution;

public enum Type {
	game,// 游戏中传递的消息类型
	other,
	test,
	// client->server
	over,// 回合结束的消息
	ready,// 玩家准备完毕的消息
	// server->client
	permit,// 允许某玩家开始行动
	// and more...
	success,//在游戏之外，服务器传递给客户端的回应成功信息
	fail,// 在任何时候都有可能返回的失败信息
}
