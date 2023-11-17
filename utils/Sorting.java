package utils;

public class Sorting {

	/**
	 * The selectionSort function sorts an array of Comparable objects in ascending
	 * order using the
	 * selection sort algorithm.
	 * 
	 * @param list The parameter "list" is an array of objects that implement the
	 *             Comparable interface.
	 */
	public static void selectionSort(Comparable[] list) {
		int min;
		Comparable temp;
		for (int index = 0; index < list.length - 1; index++) {
			min = index;
			for (int scan = index + 1; scan < list.length; scan++)
				if (list[scan].compareTo(list[min]) < 0)
					min = scan;
			// Swap the values
			temp = list[min];
			list[min] = list[index];
			list[index] = temp;
		}
	}

	/**
	 * The insertionSort function sorts an array of Comparable objects in descending
	 * order using the
	 * insertion sort algorithm.
	 * 
	 * @param list The parameter "list" is an array of objects that implement the
	 *             Comparable interface.
	 */
	public static void insertionSort(Comparable[] list) {
		for (int index = 1; index < list.length; index++) {
			Comparable key = list[index];
			int position = index;
			// Shift larger values to the right
			while (position > 0 && key.compareTo(list[position - 1]) > 0) {
				list[position] = list[position - 1];
				position--;
			}
			list[position] = key;
		}
	}
}