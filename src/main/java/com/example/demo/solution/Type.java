package com.example.demo.solution;

public enum Type {
	game,// 游戏中传递的消息类型
	// client->server
	create,// 创建房间的消息类型
	join,// 加入房间的消息类型
	over,// 回合结束的消息
	quit,// 退出房间的消息类型
	destory,// 取消房间的消息类型
	ready,// 玩家准备完毕的消息
	// server->client
	permit,// 允许某玩家开始行动
	other,
	//and more...
	test
}
