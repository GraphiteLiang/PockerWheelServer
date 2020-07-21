package com.example.demo.solution;
public class Card {
	private int color;// 1-4 clubs diamonds hearts spades
	private int num;// 1-13
	public Card(int color, int num) {
		this.color = color;
		this.num = num;
	}
	public int getColor() {
		return color;
	}
	public int getNum() {
		return num;
	}
}
