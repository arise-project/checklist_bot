package DI;

import Algorithm.Interface.ITreeMerger;
import Algorithm.Interface.ITreeWalker;
import Algorithm.TreeMerger;
import Algorithm.TreeWalker;
import Command.*;
import Command.Handler.*;
import Command.Handler.Interface.IStorageCommandHandler;
import Command.Interface.IStorageCommandBus;
import Controller.AppController;
import Controller.Interface.IAppController;
import Repository.Interface.IStorageRepository;
import Service.EverynoteService;
import Service.Interface.*;
import Repository.StorageRepository;
import Service.TextService;
import Parser.Interface.IParagraphTextParser;
import Parser.ParagraphTextParser;
import com.google.inject.AbstractModule;
import com.google.inject.TypeLiteral;

public class BasicModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(new TypeLiteral<IStorageCommandHandler<OpenStorageCommand>>() {}).to(OpenStorageCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<ReadTextFileCommand>>() {}).to(ReadTextFileCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<SaveStorageCommand>>() {}).to(SaveStorageCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<StatisticsCommand>>() {}).to(StatisticsCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<ConnectEverynoteCommand>>() {}).to(ConnectEverynoteCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<ReadENoteCommand>>() {}).to(ReadENoteCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<ListENotebooksCommand>>() {}).to(ListENotebooksCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<ListAllENotesCommand>>() {}).to(ListAllENotesCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<EverynoteProductionCommand>>() {}).to(EverynoteProductionCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<ListENotesForNotebookCommand>>() {}).to(ListENotesCommandHandler.class);
        bind(new TypeLiteral<IStorageCommandHandler<ResetAttrCommand>>() {}).to(ResetAttrCommandHandler.class);

        bind(IStorageCommandBus.class).to(StorageCommandBus.class);

        bind(IAppController.class).to(AppController.class);
        bind(IStorageRepository.class).to(StorageRepository.class).asEagerSingleton();
        bind(ITextService.class).to(TextService.class);
        bind(ITreeWalker.class).to(TreeWalker.class);
        bind(ITreeMerger.class).to(TreeMerger.class);
        bind(IParagraphTextParser.class).to(ParagraphTextParser.class);
        bind(IMergeService.class).to(MergeService.class);
        bind(IEverynoteService.class).to(EverynoteService.class).asEagerSingleton();
    }
}