package Command;

import Command.Interface.IStorageCommand;

public class StatisticsCommand implements IStorageCommand {
    @Override
    public StorageCommandType getType() {
        return StorageCommandType.Statistics;
    }

    @Override
    public void setAttributes(String[] attributes) {

    }
}
