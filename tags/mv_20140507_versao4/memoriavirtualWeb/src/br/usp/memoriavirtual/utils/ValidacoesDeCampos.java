package br.usp.memoriavirtual.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
   


public class ValidacoesDeCampos {

	public static boolean validarFormatoEmail(String email) {

		String regexp = "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+(?:\\."
				+ "[a-z0-9!#$%&’*+/=?^_‘{|}~-]+)*@"
				+ "(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?\\.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(email);

		if (!matcher.matches())
			return false;

		return true;
	}
	
	public static boolean validarFormatoTelefone(String telefone) {

		String regexp8digitos = "\\([0-9]{2}?\\){1}[0-9]{4}?\\-[0-9]{4}?";
		Pattern pattern8digitos = Pattern.compile(regexp8digitos);
		Matcher matcher8digitos = pattern8digitos.matcher(telefone);
		
		String regexp9digitos = "\\([0-9]{2}?\\){1}[0-9]{5}?\\-[0-9]{4}?";
		Pattern pattern9digitos = Pattern.compile(regexp9digitos);
		Matcher matcher9digitos = pattern9digitos.matcher(telefone);
		
		if (!matcher8digitos.matches() && !matcher9digitos.matches())
			return false;
		
		return true;
	}
	
	public static boolean validarFormatoCep(String Cep) {

		String regexp = "[0-9]{5}?\\-[0-9]{3}?";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Cep);

		if (!matcher.matches())
			return false;

		return true;
	}
	
	public static boolean validarFormatoLocalizacao(String Localizacao) {

		String regexp = "[0-9]{3}";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Localizacao);

		if (!matcher.matches())
			return false;

		return true;
	}
	
	public static boolean validaCoordenadaGeografica(String Coordenada) {

		String regexp = "[-]?[0-9]*[.]{0,1}[0-9]{4}";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Coordenada);
		
		
		if (!matcher.matches() || Coordenada.equals(""))
			return false;

		return true;
	}
	
	public static boolean validarAltitude(String Altitude) {

		String regexp = "[-]?[0-9]*[.]?{0,1}[0-9]{2}";
		Pattern pattern = Pattern.compile(regexp);
		Matcher matcher = pattern.matcher(Altitude);

		if (!matcher.matches() || Altitude.equals(""))
			return false;

		return true;
	}
	
	
	
}
