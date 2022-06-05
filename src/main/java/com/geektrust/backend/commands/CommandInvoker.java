package com.geektrust.backend.commands;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.geektrust.backend.entities.AcknowledgementMessages;
import com.geektrust.backend.exceptions.NoSuchCommandException;

public class CommandInvoker {

    private static Map<String, ICommand> commandMap = new HashMap<>();

    public void register(String commandName, ICommand command)
    {
        CommandInvoker.commandMap.put(commandName, command);
    }

    public Optional<ICommand> getCommand(String commandName)
    {
        return Optional.ofNullable(CommandInvoker.commandMap.get(commandName));
    }

    public void executeCommand(String commandName, List<String> values)
    {
        ICommand command = this.getCommand(commandName).orElseThrow(() -> new NoSuchCommandException(AcknowledgementMessages.INPUT_DATA_ERROR.getMessage()));

        command.execute(values);
    }
    
}
