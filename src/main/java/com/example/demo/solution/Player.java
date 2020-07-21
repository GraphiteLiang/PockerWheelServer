package com.example.demo.solution;

public class Player {
	
	// ������Ϣ
	public int id;
	public String name;
	
	// ��Ϸ���
	public int[] visibleCard;
	public int[] unvisibleCard;
	public int p;// �����˵ڼ�����
	public int coin;
	public int needMinPrice;
	public int score;
	
	public Player(int id) {
		this.id = id;
		this.visibleCard = new int[4];
		this.unvisibleCard = new int[2];
		p = 0;
		score = 0;
		coin = 18;
		this.needMinPrice = 1;
	}
	// ��һ�ð���
	public void receiveUnvisibleCard(int cardId1, int cardId2) {
		unvisibleCard[0] = cardId1;
		unvisibleCard[1] = cardId2;
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
