import java.io.*;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class testGame {
	public static void main(String[] args) {
		RollGame game = new RollGame();
		System.out.print("请输入参与赌博的人数,数字必须 >= 2 且 =< 26");
		while (!game.initCompleted) {
			game.initGame();
		}
		while (game.findWinner == false) {
			game.startRoll();
			System.out.println("");
		}
		System.out.println("Winner is " + game.winner);
	}
}

class Player {
	String name;
	int rollResult;

	public Player(String name) {
		this.name = name;
	}

	public void roll() {
		rollResult = (int) ((Math.random() * (3 - 1)) + 1);
	}
}

class RollGame {
	final static Pattern pattern = Pattern.compile("\\d{1,2}");
	final static String[] UPPERCASE = { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z" };
	Boolean initCompleted = false;
	Boolean findWinner = false;
	int numOfPlayer;
	ArrayList<Player> playerList;
	String winner;

	void initGame() {
		int i;
		String userInput = GetUserInput.getUserInput("");
		Matcher matcher = RollGame.pattern.matcher(userInput);
		if (matcher.matches()) {
			numOfPlayer = Integer.parseInt(userInput);
			if (numOfPlayer >= 2 & numOfPlayer <= 26) {
				playerList = new ArrayList<Player>();
				for (i = 0; i < numOfPlayer; i++) {
					playerList.add(new Player(RollGame.UPPERCASE[i]));
				}
				initCompleted = true;
			} else {
				System.out.print("请输入2到26之间的一个数");
			}
		} else {
			System.out.print("请输入有效的数字");
		}
	}

	Boolean startRoll() {
		Boolean firstRound = true;
		ArrayList<Player> biggestPlayer = new ArrayList<Player>();
		Player lastPlayerOfbiggestPlayer;
		// 开始游戏---------------------------------------------------
		for (Player eachPlayer : playerList) {
			eachPlayer.roll();
			if (firstRound) {
				biggestPlayer.add(eachPlayer);
				firstRound = false;
			} else {
				// 用当前Player的rollResult和最大列表的最后一位作比较
				lastPlayerOfbiggestPlayer = biggestPlayer.get(biggestPlayer
						.size() - 1);
				if (eachPlayer.rollResult == lastPlayerOfbiggestPlayer.rollResult) {
					biggestPlayer.add(eachPlayer);
				} else if (eachPlayer.rollResult > lastPlayerOfbiggestPlayer.rollResult) {
					biggestPlayer = new ArrayList<Player>();
					biggestPlayer.add(eachPlayer);
				}
			}
			System.out.print(eachPlayer.name + "掷出了" + eachPlayer.rollResult
					+ " ");
		}
		// 一轮赌博结束-------------------------------------------------
		if (biggestPlayer.size() == 1) {
			findWinner = true;
			winner = biggestPlayer.get(0).name;
		} else {
			playerList = biggestPlayer;
		}
		return findWinner;
	}
}

class GetUserInput {
	public static String getUserInput(String prompt) {
		String inputLine = null;
		System.out.println(prompt + " ");
		try {
			InputStreamReader is_reader = new InputStreamReader(System.in);
			BufferedReader bfReader = new BufferedReader(is_reader);
			inputLine = bfReader.readLine();
			if (inputLine.length() == 0)
				return null;
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return inputLine;
	}
}