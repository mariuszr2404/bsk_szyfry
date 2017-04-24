package LSFR;

import java.util.ArrayList;

public class Generator implements Cloneable {

	boolean alreadyExecuted = false;
	public ArrayList<Integer> generatorValue = new ArrayList<Integer>();
	public ArrayList<int[]> seedList = new ArrayList<int[]>();
	int iter = 0;
	String seed;
	public ArrayList<Integer> position = new ArrayList<Integer>();

	public Generator(String seed) {
		this.seed = seed;
	}

	public Generator(int iteration, String seed) {
		this.iter = iteration;
		this.seed = seed;
	}

	protected Object clone() throws CloneNotSupportedException {
		return super.clone();

	}

	public int generuj(String poly) {

		int[] ziarno = new int[seed.length()];
		int[] wielomian = new int[poly.length()];
		int xor_result = 0;

		if (seedList.size() == 0) {
			for (int i = 0; i < seed.length(); i++) {
				if (seed.charAt(i) == '1') {
					ziarno[i] = 1;
				} else {
					ziarno[i] = 0;
				}
			}
		} else {
			ziarno = seedList.get(seedList.size() - 1);
		}

		for (int i = 0; i < poly.length(); i++) {
			if (poly.charAt(i) == '1') {
				wielomian[i] = 1;
			} else {
				wielomian[i] = 0;
			}
		}

		ArrayList<Integer> position = positionToXor(wielomian);

		xor_result = doXor(position, ziarno);

		for (int j = ziarno.length - 1; j > 0; j--) {
			ziarno[j] = ziarno[j - 1]; // przesuwamy bity w prawo
		}

		ziarno[0] = xor_result; // wstawiamy wynik ksorowania na najbardziej
								// znaczacy bit

		int[] copyOfZiarno = new int[ziarno.length];
		for (int w = 0; w < ziarno.length; w++) {
			copyOfZiarno[w] = ziarno[w];
		}
		seedList.add(copyOfZiarno);
		generatorValue.add(xor_result);

		return xor_result;
	}

	public int generuj(String poly, String seed) {

		
		int[] ziarno = new int[seed.length()];
		int[] wielomian = new int[poly.length()];
		int xor_result = 0;

		for (int i = 0; i < seed.length(); i++) {
			if (seed.charAt(i) == '1') {
				ziarno[i] = 1;
			} else {
				ziarno[i] = 0;
			}
		}
		
		for (int i = 0; i < poly.length(); i++) {
			if (poly.charAt(i) == '1') {
				wielomian[i] = 1;
			} else {
				wielomian[i] = 0;
			}
		}
        
		if(!alreadyExecuted){
			position = positionToXor(wielomian);
			alreadyExecuted = true;
		}
		
		xor_result = doXor(position, ziarno);
/*
		for (int j = ziarno.length - 1; j > 0; j--) {
			ziarno[j] = ziarno[j - 1]; // przesuwamy bity w prawo
		}

		ziarno[0] = xor_result; // wstawiamy wynik ksorowania na najbardziej
	*/							// znaczacy bit

		int[] copyOfZiarno = new int[ziarno.length];
		for (int w = 0; w < ziarno.length; w++) {
			copyOfZiarno[w] = ziarno[w];
		}
		seedList.add(copyOfZiarno);
		generatorValue.add(xor_result);

		return xor_result;
	}

	public ArrayList<Integer> positionToXor(int[] wielonan) {
		ArrayList<Integer> positionList = new ArrayList<Integer>();
		for (int i = 0; i < wielonan.length; i++) {
			if (wielonan[i] == 1) {
				positionList.add(i);
			}
		}
		return positionList;
	}

	public int doXor(ArrayList<Integer> lista, int[] seed) {
		int suma = 0;
		for (int i = 0; i < lista.size(); i++) {
			if (seed[lista.get(i)] == 0) {
			}
			if (seed[lista.get(i)] == 1) {
				suma += 1;
			}
		}

		int xor = suma % 2;
		return xor;
	}

	public void displaySeed() {
		int[] tab = seedList.get(seedList.size() - 1);
		for (int j = 0; j < tab.length; j++) {
			System.out.print(tab[j]);
		}
		System.out.println();
	}

	// Display Generator based on itereation
	public void displayGen() {
		System.out.println("Generator dla " + getIter() + " iteracji = ");
		for (int i = 0; i < getIter(); i++) {
			System.out.print(generatorValue.get(i));
		}
	}

	public String getSeed() {
		return seed;
	}

	public void setSeed(String seed) {
		this.seed = seed;
	}

	public int getIter() {
		return iter;
	}
}
