WARNING: Illegal reflective access by com.google.inject.internal.cglib.core.$ReflectUtils$1 (file:/home/eugene/Projects/checklist_bot/core/build/libs/checklist_bot-all-1.0.jar) to method java.lang.ClassLoader.defineClass(java.lang.String,byte[],int,int,java.security.ProtectionDomain)


Exception in thread "main" java.util.ConcurrentModificationException
        at java.base/java.util.HashMap$HashIterator.nextNode(HashMap.java:1493)
        at java.base/java.util.HashMap$KeyIterator.next(HashMap.java:1516)
        at Algorithm.TreeMerger.getDifference(TreeMerger.java:27)
        at Command.Handler.ReadTextFileCommandHandler.handle(ReadTextFileCommandHandler.java:34)
        at Command.Handler.ReadTextFileCommandHandler.handle(ReadTextFileCommandHandler.java:13)
        at Command.StorageCommandBus.Dispatch(StorageCommandBus.java:36)
        at Controller.AppController.start(AppController.java:81)
        at App.main(App.java:9)
