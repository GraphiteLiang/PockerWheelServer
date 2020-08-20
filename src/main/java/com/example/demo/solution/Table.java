package com.example.demo.solution;

public class Table {
	public static final int[] player2card = {0, 3, 6, 10, 10};
	public static final int[] need2del = {0, 1, 1, 3, 2};
	public static final int[][] basicprice = {
			{0},
			{0,0,1},
			{0,0,0,1,1,2},
			{0,0,0,0,1,1,1,2,2,3},
			{0,0,0,0,1,1,2,2,2,3}
	};
	public int[] tableCards;
	public int cardCount;
	public int maxCardCount;
	public Deck deck;
	public int maxPlayerCount;
	public int playerCount;
	public int[] players;
	public int epoch;
	public int turn;
	// ��ʼ����һ����Ϸ�ĳ�ʼ��
	public Table() {
		this.cardCount = 0;
		this.playerCount = 0;
		this.maxPlayerCount = 4;
		this.maxCardCount = player2card[maxPlayerCount];
		this.tableCards = new int[maxCardCount];
		this.players = new int[playerCount];
		this.deck = new Deck();
		deck.shuffle(10);
		
	}
	public void gameInit() {
		for(int j=this.cardCount;j<this.maxCardCount;j++) {
			this.addCard(this.deck.nextCard());
		}
	}
	public void addCard(int card) {
		this.tableCards[cardCount] = card;
		cardCount++;
	}
	public void delAllCards() {
		int toDel = need2del[playerCount];
		int[] tmp = new int[maxCardCount];
		int p = 0;
		for(int i=0;i<maxCardCount;i++) {
			if(tableCards[i]!=-1 && toDel<=0) {
				tmp[p] = this.tableCards[i];
				p++;
			}else {
				if(tableCards[i]!=-1)toDel--;
			}
		}
		this.tableCards = tmp;
		this.cardCount = maxCardCount - playerCount - need2del[playerCount];
	}
	public void delCard(int i) {
		this.tableCards[i] = -1;
		this.cardCount -= 1;
	}
	// table的添加删除玩家是减少该回合的玩家数量，区别与gm的删除玩家，仅在游戏内调用
	public void addPlayer(int playerid) {
		if(playerCount == 4) return;
		int[] tmp = new int[maxPlayerCount];
		for(int i=0;i<playerCount;i++) {
			tmp[i] = this.players[i];
		}
		tmp[playerCount] = playerid;
		this.players = tmp;
		this.playerCount++;
		this.maxCardCount = player2card[playerCount];
		this.tableCards = new int[maxCardCount];
	}
	public void delPlayer(int playerid) {
		if(playerCount == 0)return;
		int[] tmp = new int[playerCount - 1];
		int p = 0;
		for(int i=0;i<playerCount;i++) {
			if(players[i] != playerid) {
				tmp[p] = this.players[i];
				p++;
			}
		}
		this.players = tmp;
		this.playerCount --;
		this.maxCardCount = player2card[playerCount];
		this.tableCards = new int[maxCardCount];
	}
	// �������Ϊ�ڼ�����
	public int cardPrice(int p) {
		switch(this.cardCount) {
		case 10:
			if(p >= 0 && p<=3) {
				return 0;
			}else if(p >=4 && p<=6) {
				return 1;
			}else if(p >=7 && p<=8) {
				return 2;
			}else {
				return 3;
			}
		case 6:
			if(p >= 0 && p<=2) {
				return 0;
			}else if(p >=3 && p<=4) {
				return 1;
			}else {
				return 2;
			}
		case 3:
			if(p >= 0 && p<=1) {
				return 0;
			}else{
				return 1;
			}
		}
		return 0;
	}
	public void playerResort(Player[] players, int n) {
		for(int i=0;i<playerCount;i++) {
			for(int j=0;j<playerCount-1;j++) {
				boolean x = Judge.comparePlayerCard(deck.cards, 
						players[this.players[j]], players[this.players[j+1]], n);
				if(x) {
					int tmp = this.players[j];
					this.players[j] = this.players[j+1];
					this.players[j+1] = tmp;
				}
			}
		}
	}
}
