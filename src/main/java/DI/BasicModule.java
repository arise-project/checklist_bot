package DI;

import Algorithm.Interface.ITreeWalker;
import Algorithm.TreeWalker;
import Controller.AppController;
import Controller.Interface.IAppController;
import Repository.Interface.IStorageRepository;
import Service.Interface.ITextService;
import Repository.StorageRepository;
import Service.TextService;
import Parser.Interface.IParagraphTextParser;
import Parser.ParagraphTextParser;
import com.google.inject.AbstractModule;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IAppController.class).to(AppController.class);
        bind(IStorageRepository.class).to(StorageRepository.class);
        bind(ITextService.class).to(TextService.class);
        bind(ITreeWalker.class).to(TreeWalker.class);
        bind(IParagraphTextParser.class).to(ParagraphTextParser.class);
    }
}

