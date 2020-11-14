package Algorithm.Interface;

import Domain.MergeNote;
import Domain.Root;

import java.util.ArrayList;

public interface ITreeMerger {
    ArrayList<MergeNote> getDifference (Root oldTree, Root newTree);
    Root merge(Root oldTree, Root newTree);
}
