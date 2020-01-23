package com.github.dagwud.woodlands.game.commands.character;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.commands.core.ShowMenuCmd;
import com.github.dagwud.woodlands.game.commands.core.SuspendableCmd;
import com.github.dagwud.woodlands.game.domain.Player;
import com.github.dagwud.woodlands.game.domain.menu.InnMenu;

public class ChangeNamePromptCmd extends SuspendableCmd
{
    private final Player player;

    public ChangeNamePromptCmd(Player player)
    {
        super(player.getPlayerState(), 2);
        this.player = player;
    }

    @Override
    protected void executePart(int phaseToExecute, String capturedInput)
    {
        switch (phaseToExecute)
        {
            case 0:
                SendMessageCmd cmd = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "Well, aren't we fickle? What do you identify as?");
                CommandDelegate.execute(cmd);
                break;
            case 1:
                rename(capturedInput);
                break;
        }

    }

    private void rename(String characterName)
    {
        if (!characterName.matches("[a-zA-Z0-9 _()*?!&\\-']+"))
        {
            SendMessageCmd err = new SendMessageCmd(getPlayerState().getPlayer().getChatId(), "That's not an allowed name. Try a more boring name.");
            CommandDelegate.execute(err);
            super.rejectCapturedInput();
            return;
        }

        player.getActiveCharacter().setName(characterName);

        SendMessageCmd msg = new SendMessageCmd(player.getChatId(), "Henceforth, the people of this land shall call you " + characterName);
        CommandDelegate.execute(msg);

        ShowMenuCmd showMenuCmd = new ShowMenuCmd(new InnMenu(), player.getPlayerState());
        CommandDelegate.execute(showMenuCmd);
    }
}
