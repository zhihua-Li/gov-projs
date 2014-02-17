package com.lims.util.crypt;

import java.util.Random;
import java.util.regex.Pattern;

public class SimpleDateCrypt {

	/**
	 * 将日期字符串加密。
	 *
	 * @param plainDate YYYY-MM-DD格式的日期字符串。
	 * @return NNNNN-NNNNN-NNNNN-NNNNN-NNNNN-NNNNN格式的加密后的字符串。
	 * @throws CryptException 如果参数格式错误，导致不能加密，则抛出此异常。
	 */
	public static String encrypt(String plainDate) throws CryptException {
		// 先判断参数格式
		if (!Pattern.matches("\\d\\d\\d\\d-\\d\\d-\\d\\d", plainDate))
			throw new CryptException();

		// 生成随机的加密后字符串数组
		char[] encrypted = SimpleDateCrypt.generateEncrypted();

		// 将日期的各个数字变换后放入加密结果中
		String s = plainDate.replaceAll("-", "");
		for (int i = 0; i < s.length(); i ++) {
			int v = s.charAt(i) - '0';
			v = (v + i + 7) % 10;
			char c = String.valueOf(v).charAt(0);

			encrypted[posMap[i]] = c;
		}

		return new String(encrypted);
	}

	/**
	 * 将加密后的日期字符串解密。
	 * @param encryptedDate NNNNN-NNNNN-NNNNN-NNNNN-NNNNN-NNNNN格式的加密字符串。
	 * @return 解密后的日期字符串，格式为YYYY-MM-DD。
	 * @throws CryptException 如果参数格式错误，导致不能解密，则抛出此异常。
	 */
	public static String decrypt(String encryptedDate) throws CryptException {
		// 判断参数长度
		if (encryptedDate.length() != 35)
			throw new CryptException();

		// 加密开始
		char[] d = new char[8];
		for (int i = 0; i < d.length; i ++) {
			char c = encryptedDate.charAt(posMap[i]);
			if ((c < '0') || (c > '9'))
				throw new CryptException();

			int v = ((c - '0') - i + 13) % 10;
			d[i] = String.valueOf(v).charAt(0);
		}

		return "" + d[0] + d[1] + d[2] + d[3] + "-" +
			d[4] + d[5] + "-" + d[6] + d[7];
	}

	private static final char[] charTable = new char[] {
		'A', 'B', 'C', 'D', 'E', 'F', 'G',
		'H', 'J', 'K', 'L', 'M', 'N', // 没有I，防止I和1混淆。
		'P', 'Q', 'R', 'S', 'T', // 没有O，防止O和0混淆。
		'U', 'V', 'W', 'X', 'Y', 'Z',
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9'
	};

	private static final int[] posMap = new int[] {
		22, 16, 2, 6, 30, 33, 8, 13
	};

	private static char[] generateEncrypted() {
		char[] result = new char[35];

		Random r = new Random();

		for (int i = 0; i < result.length; i ++) {
			if (((i + 1) % 6) == 0) {
				result[i] = '-';
			} else {
				result[i] = charTable[Math.abs(r.nextInt()) % charTable.length];
			}
		}
		return result;
	}

	public static void main(String[] argv) throws CryptException {
		System.out.println(SimpleDateCrypt.encrypt("2014-04-15"));
		System.out.println(SimpleDateCrypt.decrypt("A30DT-4A4WV-49V38-84PY9-R2K43-1706C"));
	}
}
