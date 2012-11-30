package hu.bme.mit.emf.incquery.visualization.model;

import java.util.Scanner;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.incquery.patternlanguage.patternLanguage.Pattern;

public class CheckElement extends MyNode {
	private String hooverText;

	public CheckElement(String name, EObject o, Pattern p) {
		super(name, o, p);
		String s = name;
		s = s.replaceAll("\t", " ");
		String line = "", tmp = "", end = "";
		int min = Integer.MAX_VALUE;
		int index = 0, i;
		boolean l;
		Scanner scanner = new Scanner(s);
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			l = false;
			i = 0;
			while ((i < line.length()) && (!l)) {
				if (!Character.isWhitespace(line.charAt(i)))
					l = true;
				i++;
			}
			if ((line.length() > 0) && (l))
				tmp += line + "\n";
		}
		tmp = tmp.substring(0, tmp.length() - 1);
		scanner.close();
		scanner = new Scanner(tmp);
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			l = false;
			i = 0;
			index = 0;
			while ((i < line.length()) && (!l)) {
				if (line.charAt(i) == ' ')
					index = i;
				else
					l = true;
				i++;
			}
			if (index < min)
				min = index;
		}
		scanner.close();
		scanner = new Scanner(tmp);
		while (scanner.hasNextLine()) {
			line = scanner.nextLine();
			line = line.substring(min);
			end += line + "\n";
		}
		end = end.substring(0, end.length() - 1);
		scanner.close();

		hooverText = end;

	}

	public String getHooverText() {
		return hooverText;
	}
}
