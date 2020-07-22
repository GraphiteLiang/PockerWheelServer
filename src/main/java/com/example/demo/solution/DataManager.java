package com.example.demo.solution;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSONObject;
@Component
public class DataManager implements BasicData{
	// 这个类不发送给任何玩家，留存本地进行数据处理
	List<Player> players;
	public Table t;
	public DataManager() {
		t = new Table();
		players = new ArrayList<Player>(4);
	}
	public String toJson() {
		return null;
	}
	public ServerToClientData translate(ClientToSeverData dr) {
		int id = dr.playerId;
		players.get(id).pay(t.cardPrice(dr.cardId));
		players.get(id).receiveVisibleCard(t.tableCards[dr.cardId]);
		t.delCard(dr.cardId);
		ServerToClientData pd = spawnData(dr.playerId);
		return pd;
	}
	public ServerToClientData spawnData(int playerid) {
		ServerToClientData data = new ServerToClientData();
		data.dataType = Type.game;
		Player p = players.get(playerid);
		data.name = p.name;
		data.visibleCard = p.visibleCard;
		data.tableCards = t.tableCards;
		for(int i=0;i<players.size();i++) {
			data.coins[i] = players.get(i).coin;
			data.scores[i] = players.get(i).score;
		}
		data.epoch = t.epoch;
		data.turn = t.turn;
		return data;
	}
	public static ClientToSeverData jsonToData(String json) {
		JSONObject object = JSONObject.parseObject(json);
		return object.toJavaObject(ClientToSeverData.class);
	}
	
}