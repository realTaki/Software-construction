package P1;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Scanner;

public class MagicSquares {
	public static void main(String[] argv) {
		System.out.println(generateMagicSquare(6));
		System.out.println(generateMagicSquare(-1));
		System.out.println(generateMagicSquare(5));

		System.out.println(isLegalMagicSquare("E:\\WOKESPACE\\eclipse\\Lab1_1163710227\\src\\P1\\txt\\6.txt"));
	}

	public static boolean isLegalMagicSquare(String fileName) {

		int DEFAULT_NUMBER = 0;// 规定默认初始化的数字，以保持良好的代码风格
		int lines = DEFAULT_NUMBER, cols = DEFAULT_NUMBER, theConstant = DEFAULT_NUMBER;// 初始化行数、列数以及方阵常数
		boolean flag = true;// 为了避免函数中出现多个return false（太多出口），改用标志物来跳过后面的判断，最后只设置一个出口

		MyList square = new MyList();// 使用动态数组存储方阵
		Scanner input = null;

		try {
			input = new Scanner(new File(fileName));
		} catch (FileNotFoundException e) {
			System.out.println("文件不存在！");
			return false;
			// 由于java的某些规定，此处不得不写异常处理代码
			// 但是为了能够优雅地输出错误和返回false，不得不用return打断异常处理
			// 此处如果刻板遵守单一出口原则，反而会使代码变得更加复杂、不同寻常和难以理解
			// 为了维持代码的简洁性，此处略做变通
		}

		while (input.hasNextLine() && flag) {// 按行读取
			String aLine = input.nextLine();
			String[] myLine = aLine.split("\t");
			lines++;
			if (cols != myLine.length) {// 每行的列数不一致，即某行有元素缺失，则方阵不合法
				if (cols != DEFAULT_NUMBER) {
					System.out.println("方阵第" + lines + "行有元素缺失或数字间非\\t隔开！");
					flag = false;
					break;
				} else
					cols = myLine.length;
			}

			for (int i = 0; i < cols; i++) {// 将断句好的各个数字的字符串转化为数字，有非正整数则方阵不合法
				String number = myLine[i];
				if (number.matches("[0-9]+"))
					square.add(Integer.valueOf(number));
				else {
					System.out.println("方阵第" + square.size() / cols + "行、第" + square.size() % cols + "列有非正整数！");
					flag = false;
					break;
				}
			}
		}

		if (flag && cols != lines) {// 行列数不一致，则方阵不合法
			System.out.println("方阵行列数不一致！");
			flag = false;
		}

		if (flag) {
			for (int i = 0; i < lines; i++) {// 每行和不一致，则方阵不合法
				int count = 0;
				for (int j = 0; j < cols; j++) {
					count += square.get(i * cols + j);
				}
				if (theConstant != count) {
					if (theConstant != DEFAULT_NUMBER) {
						System.out.println("方阵第" + i + "行和与其他行的和不同！");
						flag = false;
						break;
					} else
						theConstant = count;
				}
			}
		}

		if (flag) {
			for (int i = 0; i < cols; i++) {// 每列和不一致，则方阵不合法
				int count = 0;
				for (int j = 0; j < lines; j++) {
					count += square.get(j * cols + i);
				}
				if (theConstant != count) {
					if (theConstant != DEFAULT_NUMBER) {
						System.out.println("方阵第" + i + "列和与其他列的和不同！");
						flag = false;
						break;
					} else
						theConstant = count;
				}
			}
		}

		if (flag) {
			int count1 = 0, count2 = 0;// 对角线和不一致，则方阵不合法
			for (int i = 0; i < cols; i++) {
				count1 += square.get(i * cols + i);
				count2 += square.get((lines - i - 1) * cols + i);
			}
			if (count1 != count2 || count1 != theConstant) {
				System.out.println("方阵对角线的和与其他行列的和不同！");
				flag = false;
			}
		}

		// 单一出口
		input.close();
		return flag;
	}

	public static boolean generateMagicSquare(int n) {

		int row = 0, col = n / 2, i, j, square = n * n;
		boolean flag = true;// 单一出口原则设置变量

		File f = new File("E:\\WOKESPACE\\eclipse\\Lab1_1163710227\\src\\P1\\txt\\6.txt");
		PrintStream printStream = null;
		try {
			f.createNewFile();
			FileOutputStream fileOutputStream = new FileOutputStream(f);
			printStream = new PrintStream(fileOutputStream);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (n < 0) {// 排除n<0的情况
			System.out.println("n应该大于0！");
			flag = false;
		} else if (n % 2 != 1) {// 排除n为偶数的情况
			System.out.println("n应当为奇数！");
			flag = false;
		} else {// 劳伯法填奇数阶幻方
			int magic[][] = new int[n][n];
			for (i = 1; i <= square; i++) {
				magic[row][col] = i;

				if (i % n == 0)
					row++;
				else {
					if (row == 0)
						row = n - 1;
					else
						row--;
					if (col == (n - 1))
						col = 0;
					else
						col++;
				}
			}

			for (i = 0; i < n; i++) {
				for (j = 0; j < n; j++)
					printStream.print(magic[i][j] + "\t");
				printStream.println();
			}
		}

		printStream.close();
		return flag;
	}

	public static class MyList {

		// 定义一个初始长度为0的数组，用来缓存数据
		private int[] buff = new int[0];

		public MyList() {
		}

		// 增加
		public void add(int number) {
			// 定义新数组，长度是原数组长度+1
			int[] dest = new int[buff.length + 1];
			// 将原数组的数据拷贝到新数组
			System.arraycopy(buff, 0, dest, 0, buff.length);
			// 将新元素放到dest数组的末尾
			dest[buff.length] = number;
			// 将buff指向dest
			buff = dest;
		}

		// 修改指定位置的元素
		public void modify(int index, int number) {
			buff[index] = number;
		}

		// 删除指定位置的元素
		public void delete(int index) {
			int[] dest = new int[buff.length - 1];
			// 将原数组的数据拷贝到新数组
			System.arraycopy(buff, 0, dest, 0, index);
			System.arraycopy(buff, index + 1, dest, index, buff.length - 1 - index);
			buff = dest;
		}

		// 获得指定位置的元素
		public int get(int index) {
			return buff[index];
		}

		// 在指定位置插入指定元素
		public void insert(int index, int number) {
			// 定义新数组，长度是原数组长度+1
			int[] dest = new int[buff.length + 1];
			// 将原数组的数据拷贝到新数组
			System.arraycopy(buff, 0, dest, 0, index);
			dest[index] = number;
			System.arraycopy(buff, index, dest, index + 1, buff.length - index);
			buff = dest;

		}

		// 获得元素个数
		public int size() {
			return buff.length;
		}

		public void print() {
			for (int i = 0; i < size(); i++)
				System.out.println(buff[i]);
		}
	}

}