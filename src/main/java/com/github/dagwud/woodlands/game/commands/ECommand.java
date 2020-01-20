package com.github.dagwud.woodlands.game.commands;

import com.github.dagwud.woodlands.game.PlayerState;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.locations.MoveToLocationCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.BuyDrinksCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.RetrieveItemsCmd;
import com.github.dagwud.woodlands.game.commands.locations.village.ShortRestCmd;
import com.github.dagwud.woodlands.game.commands.start.PlayerSetupCmd;
import com.github.dagwud.woodlands.game.commands.start.StartCmd;
import com.github.dagwud.woodlands.game.domain.ELocation;

import java.util.HashMap;
import java.util.Map;

public enum ECommand
{
    HELP("/help", false, (state, chatId) -> new ShowHelpCmd(chatId)),
    START("/start", false, StartCmd::new),
    NEW("/new", false, (state, chatId) -> new PlayerSetupCmd(state)),
    ME("/me", false, (state, chatId) -> new ShowCharacterInfoCmd(chatId, state.getActiveCharacter())),

    THE_INN("The Inn", true, (state, chatId) -> new MoveToLocationCmd(state, ELocation.INN)),
    THE_TAVERN("The Tavern", true, (state, chatId) -> new MoveToLocationCmd(state, ELocation.TAVERN)),
    THE_VILLAGE("The Village", true, (state, chatId) -> new MoveToLocationCmd(state, ELocation.VILLAGE_SQUARE)),
    VILLAGE_SQUARE("Village Square", true, (state, chatId) -> new MoveToLocationCmd(state, ELocation.VILLAGE_SQUARE)),
    THE_MOUNTAIN("The Mountain", true, (state, chatId) -> new MoveToLocationCmd(state, ELocation.MOUNTAIN)),
    THE_WOODLANDS("The Woodlands", true, (state, chatId) -> new MoveToLocationCmd(state, ELocation.WOODLANDS)),

    BUY_DRINKS("Buy Drinks", true, (state, chatId) -> new BuyDrinksCmd(chatId, state.getActiveCharacter())),
    SHORT_REST("Short Rest", true, (state, chatId) -> new ShortRestCmd(chatId, state.getActiveCharacter())),
    RETRIEVE_ITEMS("Retrieve Items", true, (state, chatId) -> new RetrieveItemsCmd(state.getActiveCharacter())),
    ;

    ECommand(String name, boolean menuCmd, ICommandBuilder commandBuilder)
    {
        this.name = name;
        this.menuCmd = menuCmd;
        this.commandBuilder = commandBuilder;
    }

    private static final Map<String, ECommand> COMMANDS = new HashMap<>();

    static
    {
        for (ECommand value : ECommand.values())
        {
            COMMANDS.put(value.name, value);
        }
    }

    public static ECommand by(String name)
    {
        return COMMANDS.get(name);
    }

    private String name;
    private boolean menuCmd;
    private ICommandBuilder commandBuilder;

    public boolean isMenuCmd()
    {
        return menuCmd;
    }

    public AbstractCmd build(PlayerState playerState, int chatId)
    {
        return commandBuilder.build(playerState, chatId);
    }
}
