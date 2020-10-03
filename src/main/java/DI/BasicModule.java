package DI;

import Algorithm.Interface.ITreeWalker;
import Algorithm.TreeWalker;
import Controller.AppController;
import Controller.Interface.IAppController;
import Controller.Interface.IStorageController;
import Controller.Interface.ITextController;
import Controller.StorageController;
import Controller.TextController;
import Parser.Interface.IParagraphTextParser;
import Parser.ParagraphTextParser;
import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IAppController.class).to(AppController.class);
        bind(IStorageController.class).to(StorageController.class);
        bind(ITextController.class).to(TextController.class);
        bind(ITreeWalker.class).to(TreeWalker.class);
        bind(IParagraphTextParser.class).to(ParagraphTextParser.class);
    }
}

