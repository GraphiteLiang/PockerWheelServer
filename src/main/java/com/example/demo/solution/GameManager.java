package com.example.demo.solution;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
@Component
public class GameManager{
	// 这个类不发送给任何玩家，留存本地进行数据处理
	List<Player> players;
	public Table t;
	public String roomname;
	int permitId;
	public boolean gameactive;
	public GameManager() {
		players = new ArrayList<Player>(4);
		permitId = -1;
	}
	// 构建一个新的GameManger
	public GameManager(Type type, String address, String name) {
		// 测试json用代码
		if(type == Type.test) {
			t = new Table();
			players = new ArrayList<Player>(4);
			for(int i=0;i<4;i++) {
				this.addPlayer(address);
			}
			t.gameInit();
			return;
		}
		// 新建桌子
		this.t = new Table();
		// 新建玩家列表
		this.players = new ArrayList<Player>(4);
		// 当前允许-1玩家行动，也就是不允许玩家行动
		this.permitId = -1;
		// 构建新的gm是某一玩家发起的，因此将这个玩家加入到游戏中
		this.addPlayer(address);
		// 构建roomname名
		this.roomname = name.isBlank()?"让我康康！":name;
	}
	public GameManager(Type type) {
		new GameManager(type, "127.0.0.1", "让我康康！");
	}
	public int addPlayer(String address) {
		// 添加一个玩家，并返回该玩家的id
		int x = players.size();
		if(x >= 4) {
			return -1;
		}
		players.add(new Player(x, address));
		return x;
	}
	public int delPlayer(int id) {
		// 删除一个玩家，返回当前玩家数量
		if(id < players.size()) {
			players.remove(id);
		}
		int x = players.size();
		return x;
	}
	public int playerCount() {
		// 获取当前玩家数量
		return players.size();
	}
	public void startGame() {
		t = new Table();
		for(int i=0;i<4;i++) {
			t.addPlayer(i);
		}
		t.gameInit();
		this.gameactive = true;
		this.permitId = 0;
	}
	public boolean isPermited(int toCheckId) {
		return toCheckId == t.players[this.permitId];
	}
	// 返回下一个用户的id
	public int getNextPlayer() {
		int nextid = this.permitId + 1;
		if(nextid >= 4) {
			t.turn += 1;
			nextid = 0;
			t.playerResort(players, t.turn);
		}
    	this.permitId = nextid;
    	if(t.turn >= 4) {
    		this.nextEpoch();
    	}
    	return t.players[nextid];
	}
	// TODO
	// 收到所有准备信息，开启下一轮, 将选择跳过该轮的玩家忽略掉
	// 调用table的添加/删除玩家来完成忽略掉某些玩家的实现
	// 可以直接调用，函数体内已经实现了存在和不存在的两种情况
	public void nextEpoch() {
		for(int i=0;i<4;i++) {
			if(!players.get(i).isPlay)this.t.delPlayer(i);
			else this.t.addPlayer(i);
		}
		this.t.playerResort(players);
	}
	// TODO
	// 轮末，计算分数,并且得到下一轮的首动玩家
	// 不参与这一轮的玩家使其获得0level
	// 暂且是模拟加分，直接每位玩家获得10分
	public int endOfEpoch() {
		for(int i=0;i<4;i++) {
			players.get(i).score += 10;
			players.get(i).level = new Level(0, new int[] {1,2});
			players.get(i).isPlay = false;
			players.get(i).isReady = false;
		}
		return 0;
	}
	public void translate(ClientToServerGameData dr) {
		int id = dr.playerId;
		players.get(id).pay(t.cardPrice(dr.cardId));
		players.get(id).receiveVisibleCard(t.tableCards[dr.cardId]);
		t.delCard(dr.cardId);
	}
	public void letReady(int id, Type type) {
		if(id < players.size()) {
			players.get(id).isPlay = true;
			if(type == Type.skip)players.get(id).isPlay = false;
			players.get(id).isReady = true;
		}
	}
	public boolean isAllReady() {
		boolean res = true;
		for(Player p : players) {
			res = res && p.isReady;
		}
		return res;
	}
	public ServerToClientData spawnData(Type datatype, int playerid) {
		ServerToClientData data = new ServerToClientData();
		data.dataType = datatype;
		data.message = roomname;
		Player p = players.get(playerid);
		data.name = p.name;
		data.address = p.address;
		data.playerId = p.id;
		data.handCard = p.invisibleCard[0];
		data.tableCards = t.tableCards;
		
		for(int i=0;i<4;i++) {
			if(i < players.size()) {
				if(datatype == Type.calculate)
					data.invisibleCards[i] = players.get(i).invisibleCard;
				data.visibleCards[i] = players.get(i).visibleCard;
				data.coins[i] = players.get(i).coin;
				data.scores[i] = players.get(i).score;
			}else {
				data.visibleCards[i] = new int[] {0,0,0,0};
				data.coins[i] = 0;
				data.scores[i] = -1;
			}
		}
		data.epoch = t.epoch;
		data.turn = t.turn;
		return data;
	}
	public static BasicData jsonToData(String json) {
		JSONObject object = JSONObject.parseObject(json);
		switch(object.getInteger("dataType")) {
		case 1:
			return object.toJavaObject(ClientToServerOtherData.class);
		default:
			return object.toJavaObject(ClientToServerGameData.class);
		}
	}
	
}
