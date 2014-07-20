package vn.hus.readdata;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;

import vn.hus.mathformula.MathFormula;

public class ReadData {
	public static String getAnswer(String s) {
		String result = "";
		try {
			String s2 = URLEncoder.encode(s, "UTF-8");

			// Create a URL for the desired page
			URL url = new URL(
					"http://tech.fpt.com.vn/AIML/api/bots/53abcf6be4b0f1230cde8c10/chat?request="
							+ s2
							+ "&token=97f76b20-92d4-4360-9968-d5fd7f76294a");

			// Read all the text returned by the server
			BufferedReader in = new BufferedReader(new InputStreamReader(
					url.openStream()));
			String str;

			while ((str = in.readLine()) != null) {

				String[] srr = str.split(":");
				for (int i = 0; i < srr.length; i++) {
					if (srr[i].contains("response") && i < srr.length - 1) {
						result = srr[i + 1].replace("\",\"botname\"", "");
						result = result.replace("\"", "");

						if (result.contains("*MATH*")) {

							return MathFormula.proccessMathFormula(result) + "";
						}

					}
				}

			}
			in.close();

		} catch (Exception e) {
			return e.toString();
		}
		return result;
	}

}
