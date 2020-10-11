package com.example.demo.solution;

public class Player {
	
	// ������Ϣ
	public int id;
	public String name;
	public boolean isReady;
	public boolean isPlay;//为真则加入游戏，否则不加入下轮游戏
	public String address;
	
	// ��Ϸ���
	public int[] visibleCard;
	public int[] invisibleCard;
	public int p;// �����˵ڼ�����
	public int coin;
	public int needMinPrice;
	public int score;
	public Level level;//轮末时，这一轮获得的牌的等级
	
	public Player(int id, String address) {
		this.id = id;
		this.address = address;
		this.visibleCard = new int[4];
		this.invisibleCard = new int[2];
		this.isReady = false;
		p = 0;
		score = 0;
		coin = 18;
		this.needMinPrice = 1;
	}
	// ��һ�ð���
	public void receiveUnvisibleCard(int cardId1, int cardId2) {
		invisibleCard[0] = cardId1;
		invisibleCard[1] = cardId2;
	}
	public void receiveVisibleCard(int cardId) {
		if(p == 4) return;
		this.visibleCard[p] = cardId;
		p++;
		Judge.sort(this.visibleCard, p);
	}
	public int pay(int x) {
		int res = x + needMinPrice;
		this.coin -= res;
		this.needMinPrice = res;
		return res;
	}

}
