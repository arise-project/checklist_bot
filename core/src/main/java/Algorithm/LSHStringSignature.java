package Algorithm;

import info.debatty.java.lsh.MinHash;

import java.util.Set;
import java.util.TreeSet;

public class LSHStringSignature {
    public int [] getStringSignature(Set<Integer> vector) {
        MinHash minhash = new MinHash(0.1, vector.size());
        return minhash.signature(vector);
    }

    public Set<Integer> getStringVector(String s) {
        Set<Integer> result = new TreeSet<Integer>();
        for (int i = 0; i < s.length(); i++){
            result.add((int)s.charAt(i));
        }

        return result;
    }
}
