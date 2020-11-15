package Algorithm;

import Algorithm.Interface.ITreeMerger;
import Algorithm.Interface.ITreeWalker;
import Domain.*;
import com.google.inject.Inject;
import info.debatty.java.lsh.MinHash;

import java.util.*;

public class TreeMerger implements ITreeMerger {
    private final ITreeWalker walker;

    @Inject
    public TreeMerger(ITreeWalker walker){
        this.walker = walker;
    }

    @Override
    public ArrayList<MergeNote> getDifference(Root oldTree, Root newTree) {
        Map<Integer, Node> oldNotes = walker.getInBreadth(oldTree);
        Map<Integer, Node> newNotes = walker.getInBreadth(newTree);
        Set<Integer> newSet = new HashSet<>();
        newSet.addAll(newNotes.keySet());
        for (var hash: newSet) {
            if(oldNotes.containsKey(hash))
            {
                oldNotes.remove(hash);
                newNotes.remove(hash);
            }
        }

        Map<Integer, int []> oldSignatures = new HashMap<>();
        for (var old: oldNotes.values()) {
            if (old instanceof Note)
            {
                Note oldNote = (Note)old;
                int [] oldSignature = oldNote.getTextSignature();
                oldSignatures.put(oldNote.hashCode(), oldSignature);
            }
        }

        Map<Integer, int []> newSignatures = new HashMap<>();
        for (var newN: newNotes.values()) {
            if (newN instanceof Note)
            {
                Note newNote = (Note)newN;
                int [] newSignature = newNote.getTextSignature();
                newSignatures.put(newNote.hashCode(), newSignature);
            }
        }

        Map<Integer, MergeType> diff = new HashMap<>();
        Map<Integer, Integer> replace = new HashMap<>();
        for(var sigKey1: oldSignatures.keySet()) {
            double maxSimilarity = Double.MIN_VALUE;
            int similarNew = -1;
            for(var sigKey2: newSignatures.keySet()) {
                int dictSise = oldSignatures.get(sigKey1).length > newSignatures.get(sigKey2).length ?oldSignatures.get(sigKey1).length :newSignatures.get(sigKey2).length;
                MinHash minhash = new MinHash(0.001, dictSise);
                double current = minhash.similarity(oldSignatures.get(sigKey1), newSignatures.get(sigKey2));
                if(maxSimilarity < current) {
                    maxSimilarity = current;
                    similarNew = sigKey2;
                }
            }
            if(maxSimilarity > 0.9) {
                diff.put(similarNew, MergeType.Replace);
                replace.put(similarNew, sigKey1);
            }
            else {
                diff.put(sigKey1, MergeType.Delete);
            }
        }

        for(var sigKey2: newSignatures.keySet()) {
            if(!diff.containsKey(sigKey2)){
                diff.put(sigKey2, MergeType.Insert);
            }
        }

        ArrayList<MergeNote> diffNotes = new ArrayList<>();
        for (var old: oldNotes.values()) {
            if(diff.containsKey(old.hashCode())){
                if(diff.get(old.hashCode()) == MergeType.Delete){
                    var deletedNote = new MergeNote();
                    deletedNote.setNote((Note)old);
                    deletedNote.setMergeType(MergeType.Delete);
                    diffNotes.add(deletedNote);
                }
            }
        }

        for (var newN: newNotes.values()) {
            if(diff.containsKey(newN.hashCode())){
                if(diff.get(newN.hashCode()) == MergeType.Insert){
                    var insertedNote = new MergeNote();
                    insertedNote.setNote((Note)newN);
                    insertedNote.setMergeType(MergeType.Insert);
                    diffNotes.add(insertedNote);
                } else if(diff.get(newN.hashCode()) == MergeType.Replace){
                    var replaceNote = new MergeNote();
                    replaceNote.setNote((Note)newN);
                    replaceNote.setMergeType(MergeType.Replace);
                    replaceNote.setOldNote((Note)oldNotes.get(replace.get(newN.hashCode())));
                    diffNotes.add(replaceNote);
                }
            }
        }

        return diffNotes;
    }

    @Override
    public Root merge(Root oldTree, Root newTree) {
        ArrayList<MergeNote> diff = getDifference(oldTree,newTree);
        for (var change: diff) {
            switch (change.getMergeType()) {
                case Delete:
                    change.getNote().setDeleted(true);
                    break;
                case Insert:
                    oldTree.getNodes().add(change.getNote());
                    break;
                case Replace:
                    change.getOldNote().getTextHistory().add(change.getOldNote().getText());
                    change.getOldNote().setText(change.getNote().getText());
                    break;
            }
        }
        return oldTree;
    }
}
