package vn.hus.mathformula;

public class MathFormula {
	public static String proccessMathFormula(String sMath) {
		sMath = sMath.replace("*MATH*", "");
		sMath = sMath.trim();
		String sOutput = sMath + " = ";
		double rs = 0;

		String[] arr = sMath.split(" ");

		char operator = (char) arr[1].charAt(0);

		double x = Double.parseDouble(arr[0]);
		double y = Double.parseDouble(arr[2]);

		switch (operator) {
		case '+':
			rs = x + y;
			break;
		case '-':
			rs = x - y;
			break;

		case '*':
			rs = x * y;
			break;

		case '/':
			rs = x / y;
			break;
		}
		return sOutput + rs;
	}
}