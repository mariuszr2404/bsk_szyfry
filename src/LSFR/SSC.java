package LSFR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class SSC {

	ArrayList<Integer> valuesFromGen = new ArrayList<>();
	static String file_source = "";
	int counter = 0;
	int genVal = 0;
	int y = 0;
	Generator gen = null; 
	public void readFromFile(String file_to_read, String ziarno, String wielomian, String file_to_write)
			throws IOException {
		
		//int x = 0;
		gen = new Generator(ziarno);
		byte[] wynik = null;
		File inputFile = new File(file_to_read);
		byte[] file_tab = new byte[(int) inputFile.length()];
		FileInputStream fileIs = null;
		try {
			fileIs = new FileInputStream(inputFile);
			fileIs.read(file_tab);
			fileIs.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		
		if (file_tab != null) {
			wynik = new byte[file_tab.length];

			for (int i = 0; i < file_tab.length; i++) {
				int byte_wyj = 0;
				byte temp = file_tab[i];

				for(int j = 0; j < 8; j++) {
					int z = gen.generuj(wielomian);
					int x = (((temp >> j) & 0x01) == 0) ? 0 : 1; 
					int y = x ^ z;
					byte_wyj = byte_wyj | (y << j);
				}
				wynik[i] = (byte) byte_wyj;
			}

		}
		saveFile(wynik, file_to_write);
	}
	
	public static void saveFile(byte wynik[], String file_to_write) throws IOException {
		FileOutputStream fos = new FileOutputStream(file_to_write);
		try {
			fos.write(wynik);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fos.close();
	}

	public int lentghtFile(String fileName) {
		File file = new File(fileName);
		return (int) (file.length());
	}

	public static void main(String args[]) throws IOException {
		String ziarno = "";
		Scanner scanner = new Scanner(System.in);
		System.out.println("Podaj ziarno: ");
		ziarno = scanner.next();
		int proba = 0;
		String wielomian = "";
		file_source = "";
		String file_destination = "";
		String file_coded = "";

		while (ziarno.length() != wielomian.length()) {
			if (proba == 0)
				System.out.println("Podaj wielomian: ");
			else
				System.err.println("Podaj wielomian o d³ugoœci ziarna");
			wielomian = scanner.next();
			proba++;
		}

		System.out.println("Podaj nazwe pliku Ÿród³owego: ");
		file_source = scanner.next();
		
		System.out.println("Podaj nazwe pliku do zaszyfrowania: ");
		file_destination = scanner.next();
		
		SSC scc = new SSC();
		scc.readFromFile(file_source, ziarno, wielomian, file_destination);
		
		// Deszyfrowanie
		file_coded = file_destination.substring(0,file_destination.length()-4).concat("_decoded.bin");
		ziarno = scc.gen.getSeed();
		
		System.out.println();
		System.out.println("*** Nacisnij dowolny klawisz jeœli chcesz odszyfrowac ***");
		//	scanner.next();
		System.in.read();
		scc.readFromFile(file_destination, ziarno, wielomian, file_coded);
		
		System.out.println("Odkodowano do pliku: " +  file_coded);
	}
}
