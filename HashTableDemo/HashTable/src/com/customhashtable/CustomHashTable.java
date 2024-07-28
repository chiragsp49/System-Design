package com.customhashtable;

public class CustomHashTable {
	
	Object[][] arr;
	int size = 10;
	
	public CustomHashTable() {
		this.arr = new Object[size][10];// limiting size of buckets to 10
	}
	
	public CustomHashTable(int size) {
		this.size = size;
		this.arr = new Object[size][10];// limiting size of buckets to 10
	}
	
	
	public void insert(char key, int val) {
		int hash = key+1000;
		int indx = hash%size;
		Object[] buckets = null;
		buckets = this.arr[indx];
		Bucket bucket = new Bucket(key, val);
		if(buckets.length>0) {
			for(int i=0;i<buckets.length;i++) {
				if(buckets[i]!=null && ((Bucket)buckets[i]).key==key) {//checking if there is bucket and is has same values as key then overide it
					buckets[i] = bucket;
					break;
				}
				if(buckets[i]==null) {// inserting into emptyu space in bucket
					buckets[i] = bucket;
					break;
				}
			}
		}else {
			buckets[0]=bucket;
		}
		this.arr[indx]=buckets;
	}
	
	public int get(int key) {
		int hash = key+1000;
		int indx = hash%size;
		if(this.arr[indx].length>0) {
			Object[] buckets = this.arr[indx];
			if(buckets.length>0) {
				for(int i=0;i<buckets.length;i++) {
					if(((Bucket)buckets[i]).key==key) {
						return ((Bucket)buckets[i]).val;
					}
				}
			}
		}
	return 0;
		
	}
	
	
	
	// Decalred a simple Bucket class
	class Bucket{
		char key;
		int val;
		Bucket(char key,int val){
			this.key=key;
			this.val=val;
		}
	}
}
