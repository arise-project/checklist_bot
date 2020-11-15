package Service.Interface;

import Domain.MergeNote;

import java.util.ArrayList;

public class MergeService implements IMergeService{
    @Override
    public void printDiff(ArrayList<MergeNote> diff) {
        for(MergeNote change: diff){
            switch (change.getMergeType()){
                case Delete:
                    System.out.println("Deleted:"+change.getNote().getName());
                    break;
                case Insert:
                    System.out.println("Inserted:"+change.getNote().getName());
                    break;
                case Replace:
                    System.out.println("Replaced:"+change.getOldNote().getName());
                    System.out.println("From:"+change.getOldNote().getText());
                    System.out.println("To:"+change.getNote().getText());
                    break;
            }

        }
    }
}
