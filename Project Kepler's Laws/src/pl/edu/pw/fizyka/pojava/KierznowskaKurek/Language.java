package pl.edu.pw.fizyka.pojava.KierznowskaKurek;

import java.util.Locale;
import java.util.ResourceBundle;

//Anna Kierznowska
public class Language {
	Locale locale;
	ResourceBundle text;
	
	public Language() {
		text = ResourceBundle.getBundle("Text");
	}	
}
