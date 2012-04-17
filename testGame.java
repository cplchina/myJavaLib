package fjo;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TestGame {
	public static void main(String[] args) {
		RollGame game = new RollGame();
		System.out.println("请输入参与赌博的人数,数字必须 >= 2 且 =< 26");
		game.initGame();
		game.startRoll();
		System.out.println();
		System.out.println("Winner is " + game.getWinner());
	}
}

class Player {
	private String name;
	private int rollResult;

	public Player(String name) {
		this.name = name;
		rollResult = 0;
	}

	public int roll() {
		rollResult = (int) ((Math.random() * 6) + 1);
		return getRollResult();
	}
	
	public String getName() {
		return name;
	}

	public int getRollResult() {
		return rollResult;
	}
}


/**
 * <p>Description:You can create a "GameProcessor" class to separate the game's logic and interactive.</p>
 * CreateDate: Apr 12, 2012
 * @author HeatoN
 */
class RollGame {
	
	private int numOfPlayer;
	private List<Player> playerList;
	private String winner;

	private CommandHelper commandHelper;
	private StringHelper stringHelper;
	
	RollGame(){
		commandHelper = new CommandHelper();
		stringHelper = new StringHelper();
	}
	
	void initGame() {
		String userInput = commandHelper.getUserInput();
		while(!checkInput(userInput)){
			System.out.print("输入有误！请输入2到26之间的数字");
			userInput = commandHelper.getUserInput();
		}
		playerList = new ArrayList<Player>(numOfPlayer);
		for (int i = 0; i < numOfPlayer; i++) {
			playerList.add(new Player(StringHelper.UPPERCASE[i]));
		}
	}

	private boolean checkInput(String s){
		if(!stringHelper.isPosInt(s))return false;
		int t = Integer.parseInt(s);
		if(t<2 || t>26){
			return false;
		}
		numOfPlayer = t;
		return true;
	}
	
	void startRoll() {
		List<Player> biggestPlayer = playerList;
		do{
			biggestPlayer = rollOneRound(biggestPlayer);
		}while(biggestPlayer.size()!=1);
		winner = biggestPlayer.get(0).getName();
	}
	
	private List<Player> rollOneRound(List<Player> playerList){
		List<Player> biggestPlayer = null;
		int biggestNum = 0;
		for (Player player: playerList) {
			int re = player.roll();
			commandHelper.printRoll(player);
			if(biggestPlayer==null || re>biggestNum){
				biggestPlayer = new ArrayList<Player>();
				biggestNum = re;
			}
			if(re==biggestNum)
				biggestPlayer.add(player);
		}
		commandHelper.printhl();
		return biggestPlayer;
	}
	
	String getWinner(){
		return winner;
	}
}

/**
 * <p>Description:every command function should in this class.
 * When you need change the interactive (use jframe or print to jsp),you can extract interface
 * and extends the interface without changing a lot of code.</p>
 * CreateDate: Apr 12, 2012
 * @author HeatoN
 */
class CommandHelper{
	public String getUserInput() {
		String inputLine = null;
		try {
			InputStreamReader is_reader = new InputStreamReader(System.in);
			BufferedReader bfReader = new BufferedReader(is_reader);
			inputLine = bfReader.readLine();
			//inputLine = System.console().readLine();
			if (inputLine.length() == 0)
				return null;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return inputLine;
	}
	
	/**
	 * print halving line
	 */
	public void printhl(){
		System.out.println("===========================");
	}
	
	public void printRoll(Player p){
		System.out.println(p.getName() + "掷出了" + p.getRollResult());
	}
}

/**
 * <p>Description:extend function for String</p>
 * CreateDate: Apr 12, 2012
 * @author HeatoN
 */
class StringHelper{
	
	public final static String[] UPPERCASE = { "A", "B", "C", "D", "E", "F", "G", "H",
			"I", "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U",
			"V", "W", "X", "Y", "Z" };
	private final static Pattern pattern = Pattern.compile("\\d{1,2}");
	
	public boolean isPosInt(String s){
		if(s==null)return false;
		Matcher matcher = pattern.matcher(s.trim());
		return matcher.matches();
	}
}