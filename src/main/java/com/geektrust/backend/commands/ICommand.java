package com.geektrust.backend.commands;

import java.util.List;

public interface ICommand {
    
    public void execute(List<String> values);
}
