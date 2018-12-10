import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Arrays;

/**
 * @author JulianBermudez
 */
public class ProblemaC {

	public static void main(String[] args) throws Exception {
		ProblemaC pC = new ProblemaC();
		try ( 
				InputStreamReader is= new InputStreamReader(System.in);
				BufferedReader br = new BufferedReader(is);
				) { 
			String line = br.readLine();

			while(line != null && line.length() > 0 && !"0".equals(line)) {
				int n = Integer.parseInt( line );
				line = br.readLine();
				
				final String [] intervalos = line.split(" ");
				final int[] numeros = Arrays.stream(intervalos).mapToInt(f -> Integer.parseInt(f)).toArray();

				int[] limitesInf = pC.arregloLimites(0, numeros, n);
				int[] limitesSup = pC.arregloLimites(1, numeros, n);
				
				pC.sort(limitesInf, limitesSup, 0, n - 1);

				//printArray(limitesInf);
				//printArray(limitesSup);	
				
				boolean[][] intersecciones = pC.matrizIntersecciones(limitesInf, limitesSup, n);
				//printMatriz(intersecciones);
				
				int[] parejaSolucion = pC.parejaSolucion(intersecciones);
				int voe = (int) ( limitesInf[ parejaSolucion[0] ] + limitesSup[ parejaSolucion[1] ] ) / 2;
				System.out.println("" + voe);
				
				//printArray(parejaSolucion);
				//System.out.println("(" + limitesInf[ parejaSolucion[1] ] + ", " + limitesSup[ parejaSolucion[0] ] + ")" );

				line = br.readLine();
			}
		}
	}

	private int[] arregloLimites(int indiceInicio, int[] arreglo, int n) {
		int[] arregloLimites = new int[n];
		int restaIndice;

		if (indiceInicio == 0)
			restaIndice = 0;
		else
			restaIndice = 1;

		for (int i = indiceInicio; i < arreglo.length; i = i + 2) {
			arregloLimites[i - restaIndice] = arreglo[i];
			restaIndice++;
		}

		return arregloLimites;
	}

	private void merge(int A1[], int A2[], int l, int m, int r) { 
		// Find sizes of two subarrays to be merged 
		int n1 = m - l + 1; 
		int n2 = r - m; 

		/* Create temp arrays */
		int L1[] = new int [n1];
		int R1[] = new int [n2];
		
		int L2[] = new int [n1];
		int R2[] = new int [n2];

		/*Copy data to temp arrays*/
		for (int i=0; i<n1; ++i) {
			L1[i] = A1[l + i];
			L2[i] = A2[l + i];
		}
			
		for (int j=0; j<n2; ++j) {
			R1[j] = A1[(m + 1) + j];
			R2[j] = A2[(m + 1) + j];	
		}
			


		/* Merge the temp arrays */

		// Initial indexes of first and second subarrays 
		int i = 0, j = 0; 

		// Initial index of merged subarry array 
		int k = l; 
		while (i < n1 && j < n2) { 
			if ( (L1[i] <= R1[j]) && (L2[i] <= R2[j])) { 
				A1[k] = L1[i];
				A2[k] = L2[i];
				i++; 
			} 
			else {
				A1[k] = R1[j];
				A2[k] = R2[j];
				j++; 
			}
			
			k++; 
		} 

		/* Copy remaining elements of L[] if any */
		while (i < n1) { 
			A1[k] = L1[i];
			A2[k] = L2[i];
			i++; 
			k++; 
		} 

		/* Copy remaining elements of R[] if any */
		while (j < n2) { 
			A1[k] = R1[j];
			A2[k] = R2[j];
			j++; 
			k++; 
		} 
	} 

	// Main function that sorts arr[l..r] using 
	// merge() 
	private void sort(int A1[], int A2[], int l, int r) {
		if (l < r) {
			// Find the middle point 
			int m = (l+r)/2; 

			// Sort first and second halves 
			sort(A1, A2, l, m); 
			sort(A1, A2, m + 1, r); 

			// Merge the sorted halves 
			merge(A1, A2, l, m, r); 
		} 
	} 

	private boolean[][] matrizIntersecciones(int[] limitesInf, int[] limitesSup, int n) {
		boolean[][] matrizIntersecciones = new boolean[n][n];
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				if (j == i)
					matrizIntersecciones[i][j] = true;
				else if (i > j)
					matrizIntersecciones[i][j] = false;
				else if (i < j)
					matrizIntersecciones[i][j] = limitesSup[i] >= limitesInf[j];
			}
		}
		
		return matrizIntersecciones;
	}
	
	private int[] parejaSolucion(boolean[][] intersecciones) {
		int[] pareja = new int[2];
		boolean encontro = false;
		
		for (int i = 0; i < intersecciones.length && !encontro; i++) {
			for (int j = (intersecciones[i].length) - 1; j >= 0 && !encontro; j--) {
				if (!intersecciones[i][j])
					continue;
				else {
					pareja[0] = i;
					pareja[1] = j;
					encontro = true;
				}				
			}
		}
		
		return pareja;
	}

	/**
	private static void printArray(int arr[]) { 
		int n = arr.length; 
		for (int i=0; i<n; ++i) 
			System.out.print(arr[i] + " "); 
		System.out.println(); 
	}
	
	private static void printMatriz(boolean m[][]) { 
		int n = m.length;
		
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				System.out.print(m[i][j] + " ");
			}
		}
			 
		System.out.println(""); 
	}
	**/
}
