package Algorithm;

import info.debatty.java.lsh.MinHash;

public class LSHStringSignature {
    public int [] getStringSignature(boolean [] vector) {
        // Initialize the hash function for an similarity error of 0.1
        // For sets built from a dictionary of 5 items
        MinHash minhash = new MinHash(0.1, vector.length);
        return minhash.signature(vector);
    }

    public boolean [] getStringVector(String s) {
        boolean [] result = new boolean[s.length()*32];
        int index = 0;
        for (int i = 0; i < s.length(); i++){
            int c = s.charAt(i);
            for (int j = 0; j < 32; ++j) {
                result[index] = (c & (1 << j)) != 0;
                index++;
            }
        }

        return result;
    }
}
