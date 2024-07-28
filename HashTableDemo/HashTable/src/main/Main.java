package main;

import com.customhashtable.CustomHashTable;

public class Main {
	public static void main(String[] args) {
		CustomHashTable hashTable = new CustomHashTable();
		hashTable.insert('A', 51);
		hashTable.insert('B', 52);
		hashTable.insert('B', 60);
		hashTable.insert('C', 53);
		hashTable.insert('D', 54);
		hashTable.insert('E', 55);
		
		
		System.out.println(hashTable.get('B'));
		
	}
}
