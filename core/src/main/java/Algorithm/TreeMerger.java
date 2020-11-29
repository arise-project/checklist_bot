package Algorithm;

import Algorithm.Interface.ITreeMerger;
import Algorithm.Interface.ITreeWalker;
import Domain.*;
import com.google.inject.Inject;
import info.debatty.java.stringsimilarity.MetricLCS;

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
        Set<Integer> newSet = new HashSet<>(newNotes.keySet());
        for (var hash: newSet) {
            if(oldNotes.containsKey(hash))
            {
                oldNotes.remove(hash);
                newNotes.remove(hash);
            }
        }

        Map<Integer, MergeType> diff = new HashMap<>();
        Map<Integer, Integer> replace = new HashMap<>();
        for(var sigKey1: oldNotes.keySet()) {
            double minSimilarity = 1;
            int similarNew = -1;
            for(var sigKey2: newNotes.keySet()) {
                MetricLCS lcs = new MetricLCS();
                double current = lcs.distance(
                        ((Note)oldNotes.get(sigKey1)).getText(),
                        ((Note)newNotes.get(sigKey2)).getText());
                if(minSimilarity > current) {
                    minSimilarity = current;
                    similarNew = sigKey2;
                }
            }
            if(minSimilarity < 0.3) {
                diff.put(similarNew, MergeType.Replace);
                replace.put(similarNew, sigKey1);
            }
            else {
                diff.put(sigKey1, MergeType.Delete);
            }
        }

        for(var sigKey2: newNotes.keySet()) {
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
