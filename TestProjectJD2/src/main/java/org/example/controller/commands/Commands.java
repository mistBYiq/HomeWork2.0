package org.example.controller.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

@Getter
@AllArgsConstructor
public enum Commands {
    FIND_BY_ID("findById"),
    FIND_ALL("findAll"),
    CREATE("create"),
    DELETE("delete"),
    UPDATE("update"),
    DEFAULT("findAll");

    private String commandName;

    public static Commands findByCommandName(String commandName) {
         if (StringUtils.isNotBlank(commandName)) {
             for (Commands command : Commands.values()) {
                 if (command.getCommandName().equalsIgnoreCase(commandName)) {
                     return command;
                 }
             }
         }
         return DEFAULT;
     }
}
