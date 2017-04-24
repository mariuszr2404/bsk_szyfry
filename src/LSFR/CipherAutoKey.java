package LSFR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class CipherAutoKey {

	ArrayList<Integer> valuesFromGen = new ArrayList<>();
	Generator gen = null;
	static String file_source = "";

	public void readFromFile(String file_to_read, String ziarno, String wielomian, String file_to_write)
			throws IOException {
        
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

				for (int j = 0; j < 8; j++) {
					int z = gen.generuj(wielomian, ziarno);
					int x = (((temp >> j) & 0x01) == 0) ? 0 : 1;
					int y = x ^ z;
					byte_wyj = byte_wyj | (y << j);
					ziarno = y + ziarno.substring(0,ziarno.length()-1);
				}
				wynik[i] = (byte) byte_wyj;
			}

		}
		saveFile(wynik, file_to_write);
	}

	public void decodeFromStream(String ziarno, String wielomian, String file_to_read, String file_to_write)
			throws Exception {
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

				for (int j = 0; j < 8; j++) {
					int z = gen.generuj(wielomian, ziarno);
					int y = (((temp >> j) & 0x01) == 0) ? 0 : 1;
					int x = y ^ z;
					byte_wyj = byte_wyj | (x << j);
					ziarno = y + ziarno.substring(0,ziarno.length()-1);
				}
				wynik[i] = (byte) byte_wyj;
			}

		}
		saveFile(wynik, file_to_write);
	}

	public void saveFile(byte wynik[], String file_to_write) throws IOException {
		FileOutputStream fos = new FileOutputStream(file_to_write);
		try {
			fos.write(wynik);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		fos.close();
	}

	public static void main(String[] args) throws Exception {

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

		CipherAutoKey CaK = new CipherAutoKey();
		CaK.readFromFile(file_source, ziarno, wielomian, file_destination);

		// Deszyfrowanie
		file_coded = file_destination.substring(0, file_destination.length() - 4).concat("_decoded.bin");
		ziarno = CaK.gen.getSeed();
		System.out.println("z: " + ziarno);

		System.out.println();
		System.out.println("*** Nacisnij dowolny klawisz jeœli chcesz odszyfrowac ***");
		// scanner.next();
		System.in.read();
		CaK.decodeFromStream(ziarno, wielomian, file_destination , file_coded);

		System.out.println("Odkodowano do pliku: " + file_coded);
	}
}
