import java.io.*;

public class GetUserInput {
	public static void main(String[] args) {
		String inPutStream = getUserInput("请输入");
		System.out.print("Your input is " + inPutStream);
	}
	
	public static String getUserInput(String prompt) {
		String inputLine = null;
		System.out.print(prompt + " ");
		try {
			// 在Java当中, 用户输入要通过 InputStream (输入流)来获取.
			// System.in 就是系统的输入流. 缺省情况下这个输入流连接到控制台(命令行)。
			// InputStreamReader 就是用来读取 InputStream 的类.
			InputStreamReader is_reader = new InputStreamReader(System.in);
			// 通常使用 BufferedReader 来读取 InputStream 中的字符串内容.
			BufferedReader bfReader = new BufferedReader(is_reader);
			inputLine = bfReader.readLine();
			if (inputLine.length() == 0 ) return null;
		} catch (IOException e) {
			System.out.println("IOException: " + e);
		}
		return inputLine;
	}
}
