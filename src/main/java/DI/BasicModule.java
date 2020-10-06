package DI;

import Algorithm.Interface.ITreeWalker;
import Algorithm.TreeWalker;
import Command.*;
import Command.Handler.*;
import Command.Handler.Interface.IStorageCommandHandler;
import Command.Interface.IStorageCommandBus;
import Controller.AppController;
import Controller.Interface.IAppController;
import Repository.Interface.IStorageRepository;
import Service.Interface.ITextService;
import Repository.StorageRepository;
import Service.TextService;
import Parser.Interface.IParagraphTextParser;
import Parser.ParagraphTextParser;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(IAppController.class).to(AppController.class);
        bind(IStorageRepository.class).to(StorageRepository.class).asEagerSingleton();
        bind(ITextService.class).to(TextService.class);
        bind(ITreeWalker.class).to(TreeWalker.class);
        bind(IParagraphTextParser.class).to(ParagraphTextParser.class);

        bind(new TypeLiteral<IStorageCommandHandler<OpenStorageCommand>>() {}).to(OpenStorageCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<ReadTextFileCommand>>() {}).to(ReadTextFileCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<ResetAttrCommand>>() {}).to(ResetAttrCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<SaveStorageCommand>>() {}).to(SaveStorageCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<SetBAttrCommand>>() {}).to(SetBAttrCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<StatisticsCommand>>() {}).to(StatisticsCommandHandler.class);

        bind(IStorageCommandBus.class).to(StorageCommandBus.class);
    }
}