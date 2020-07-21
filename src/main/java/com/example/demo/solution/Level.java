package com.example.demo.solution;

public class Level {
	public static final String level2String[] = {"HighCard", "Pair", "TwoPairs", 
	                                         "ThreeofaKind", "Straight",
	                                     	 "Flush", "FullHouse", "FourofaKind",
	                                     	 "StraightFlush"};
	// 0.HighCard 1.Pair 2.TwoPairs 3.ThreeofaKind4.Straight
	// 5.Flush 6.FullHouse 7.FourofaKind 8.StraightFlush
	private int level;
	private int[] flag;
	public Level(int level, int[] flag) {
		this.level = level;
		this.flag = flag;
	}
	public int getLevel() {
		return level;
	}
	public int[] getFlag() {
		return flag;
	}
	public String toString() {
		return level2String[level];
	}
	
}
