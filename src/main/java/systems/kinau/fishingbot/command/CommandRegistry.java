package systems.kinau.fishingbot.command;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class CommandRegistry {

    @Getter private final List<Command> registeredCommands = new ArrayList<>();

    public void registerCommand(Command command) {
        Command existingCommand = getCommand("/" + command.getLabel());
        if (existingCommand != null)
            return;
        registeredCommands.add(command);
    }

    public void unregisterCommand(Command command) {
        registeredCommands.remove(command);
    }

    public boolean dispatchCommand(String cmdStr, CommandExecutor commandExecutor) {
        Command cmd = getCommand(cmdStr);
        if (cmd == null)
            return false;
        cmdStr = cmdStr.trim();
        if (cmdStr.startsWith("/"))
            cmdStr = cmdStr.substring(1);
        String[] args = cmdStr.split(" ");
        String label = args[0];
        args = Arrays.copyOfRange(args, 1, args.length);
        cmd.onCommand(label, args, commandExecutor);
        return true;
    }

    public Command getCommand(String cmd) {
        if (cmd.startsWith("/"))
            cmd = cmd.substring(1);
        String[] args = cmd.split(" ");
        Optional<Command> optCommand = registeredCommands.stream()
                .filter(command -> command.getLabel().equalsIgnoreCase(args[0]))
                .findAny();
        if (!optCommand.isPresent())
            optCommand = registeredCommands.stream()
                    .filter(command -> command.getAliases().contains(args[0].toLowerCase()))
                    .findAny();

        return optCommand.orElse(null);
    }
}
