package com.example.demo.solution;

public class Judge {
	public static final int[] level2score = {1, 2, 4, 8, 12, 20, 30, 45, 70};
	// 0.HighCard 1.Pair 2.TwoPairs 3.ThreeofaKind4.Straight
	// 5.Flush 6.FullHouse 7.FourofaKind 8.StraightFlush
	
	public static boolean comparePlayerCard(Card[] cards, Player a, Player b, int n) {
		Level alevel = simpleJudgeLevelId(cards, a.visibleCard, n);
		Level blevel = simpleJudgeLevelId(cards, b.visibleCard, n);
		if(alevel.getLevel() > blevel.getLevel()) {
			return true;
		}else if(alevel.getLevel() < blevel.getLevel()) {
			return false;
		}else {
			int[] flaga = alevel.getFlag();
			int[] flagb = blevel.getFlag();
			for(int i=0;i<flaga.length;i++) {
				if(flaga[i] > flagb[i]) {
					return true;
				}else if(flaga[i] < flagb[i]) {
					return false;
				}
			}
			return false;	
		}
	}
	public static Level simpleJudgeLevelId(Card[] cards, int[] hand, int n) {
		if(n==1) {
			int flag[] = {cards[hand[0]].getNum()};
			return new Level(0, flag);
		}
		if(n==2) {
			if(cards[hand[0]].getNum() == cards[hand[1]].getNum()) {
				int flag[] = {cards[hand[0]].getNum()};
				return new Level(1, flag);
			}
			else {
				int flag[] = {cards[hand[1]].getNum(), cards[hand[0]].getNum()};
				return new Level(0, flag);
			}
		}
		if(n==3) {
			int x = cards[hand[0]].getNum();
			int y = cards[hand[1]].getNum();
			int z = cards[hand[2]].getNum();
			if(x == y && x == z) {
				int[] flag = {x};
				return new Level(3, flag);
			}
			if(x == y) {
				int[] flag = {x, z};
				return new Level(1, flag);
			}
			if(x == z) {
				int[] flag = {x, y};
				return new Level(1, flag);
			}
			if(y == z) {
				int[] flag = {y, x};
				return new Level(1, flag);
			}
			int flag[] = {z, y ,x};
			return new Level(0, flag);
			
		}
		if(n == 5) {
			return getJudgeLevel(cards, hand);
		}
		return null;
	}

	public static Level getJudgeLevel(Card[] cards, int[] hand) {
		boolean isFlush = judgeFlush(cards, hand);
		boolean isStraight = judgeStraight(cards, hand);
		int sameCount[] = getSameCount(cards, hand);
		if(isFlush && isStraight) {
			int flag[] = {0};
			flag[0] = cards[hand[0]].getNum();
			return new Level(8, flag);
		}
		if(isFlush) {
			int flag[] = new int[5];
			for(int i=0;i<5;i++) {
				flag[4-i] = hand[i];
			}
			return new Level(5, flag);
		}
		if(isStraight) {
			int flag[] = {0};
			flag[0] = cards[hand[0]].getNum();
			return new Level(4, flag);
		}
		switch(sameCount[1]){
		case 2:
			int flag[] = new int[4];
			flag[0] = sameCount[0];
			int p = 1;
			for(int i=0;i<5;i++) {
				if(cards[hand[i]].getNum() != flag[0]) {
					flag[p++] = cards[hand[i]].getNum();
				}
			}
			return new Level(1, flag);
		case 3:
			int flag1[] = new int[3];
			flag1[0] = sameCount[0];
			int p1 = 1;
			for(int i=0;i<5;i++) {
				if(cards[hand[i]].getNum() != flag1[0]) {
					flag1[p1++] = cards[hand[i]].getNum();
				}
			}
			return new Level(3, flag1);
		case 4:
			int flag2[] = new int[2];
			flag2[0] = sameCount[0];
			int p2 = 1;
			for(int i=0;i<5;i++) {
				if(cards[hand[i]].getNum() != flag2[0]) {
					flag2[p2++] = cards[hand[i]].getNum();
				}
			}
			return new Level(7, flag2);
 		case 5:
 			int flag3[] = {0};
 			flag3[0] = sameCount[0];
			return new Level(6, flag3);
		}
		int flag[] = new int[5];
		for(int i=0;i<5;i++) {
			flag[4-i] = cards[hand[i]].getNum();
		}
		return new Level(0, flag);
	}
	// ����handcard�Ѿ���С���ֵ������ֽ���������
	public static boolean judgeFlush(Card[] cards, int[] hand) {
		int colorCount[] = new int[4];
		for(int i=0;i<5;i++) {
			colorCount[cards[hand[i]].getColor()-1] += 1;
		}
		for(int i=0;i<4;i++) {
			if(colorCount[i] == 5)return true;
		}
		return false;
	}
	public static boolean judgeStraight(Card[] cards, int[] hand) {
		int last = cards[hand[0]].getNum();
		for(int i=1;i<5;i++) {
			int cur = cards[hand[i]].getNum();
			if(cur != last + 1) {
				if(last == 1 && cur == 10) {
					last = cur;
					continue;
				}else {
					return false;
				}
			}
			last = cur;
		}
		return true;
	}
	public static int[] getSameCount(Card[] cards, int[] hand) {
		int numCount[] = new int[13];
		for(int i=0;i<5;i++) {
			numCount[cards[hand[i]].getNum() - 1] += 1;
		}
		int[] res = new int[2];
		for(int i=0;i<13;i++) {
			if(numCount[i] >= 2) {
				res[0] += 1;
				res[1] += numCount[i];
			}
		}
		return res;
	}

	public static boolean simpleJudgeFlush(Card[] cards, int[] hand) {
		int colorCount[] = new int[4];
		for(int i=1;i<4;i++) {
			colorCount[cards[hand[i]].getColor()-1] += 1;
		}
		for(int i=0;i<4;i++) {
			if(colorCount[i] == 3)return true;
		}
		return false;
	}
	public static boolean simpleJudgeStraight(Card[] cards, int[] hand) {
		int first = cards[hand[1]].getNum();
		int mid = cards[hand[2]].getNum();
		int last = cards[hand[3]].getNum();
		if(first == mid-1 && mid == last-1)
			return true;
		else
			return false;
	}


	public static void sort(int[] hand, int n) {
		if(n==1)return;
		for(int i=0;i<n;i++) {
			for(int j=0;j<n-1;j++) {
				if(hand[j] > hand[j+1]) {
					int tmp = hand[j];
					hand[j] = hand[j+1];
					hand[j+1] = tmp;
				}
			}
		}
	}
}
