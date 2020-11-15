package Service.Interface;

import Domain.MergeNote;

import java.util.ArrayList;

public interface IMergeService {
    void printDiff(ArrayList<MergeNote> diff);
}
