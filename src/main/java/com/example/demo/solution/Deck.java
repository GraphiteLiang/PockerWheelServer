package com.example.demo.solution;

public class Deck {
	public static final int colors[] = {1,2,3,4};
	public static final int nums[] = {1,2,3,4,5,6,7,8,9,10,11,12,13};
	public Card[] cards;
	private int[] deck;
	private int deckPos;
	public Deck() {
		cards = new Card[52];
		int p = 0;
		for(int i:nums) {
			for(int j:colors) {
				cards[p] = new Card(j, i);
				p++;
			}
		}
		deck = new int[52];
		for(int i=0;i<52;i++) {
			deck[i] = i;
		}
		this.shuffle(1);
		this.deckPos = 0;
	}
	public void shuffle(int n) {
		for(int i=0;i<n*52;i++) {
			int x = (int)(Math.random() * 52);
			int tmp = deck[0];
			deck[0] = deck[x];
			deck[x] = tmp;
		}
	}
	public Card getCard(int id) {
		if(id == -1)return null;
		return cards[id];
	}
	public int nextCard() {
		int res = this.deck[deckPos];
		deckPos++;
		return res;
	}
}
