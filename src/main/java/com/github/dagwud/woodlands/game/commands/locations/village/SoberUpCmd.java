package com.github.dagwud.woodlands.game.commands.locations.village;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.commands.core.SendMessageCmd;
import com.github.dagwud.woodlands.game.domain.GameCharacter;

public class SoberUpCmd extends AbstractCmd
{
    private final GameCharacter activeCharacter;

    public SoberUpCmd(GameCharacter activeCharacter)
    {
        this.activeCharacter = activeCharacter;
    }

    @Override
    public void execute()
    {
        if (activeCharacter.getStats().getDrunkeness() <= 0)
        {
            activeCharacter.getStats().setDrunkeness(0);
            return;
        }

        CommandDelegate.execute(new SendMessageCmd(activeCharacter.getPlayedBy().getChatId(), "You sober up a bit."));
        activeCharacter.getStats().setDrunkeness(activeCharacter.getStats().getDrunkeness() - 1);

        if (activeCharacter.getStats().getDrunkeness() > 0)
        {
            CommandDelegate.execute(new RunLaterCmd(10_000, new SoberUpCmd(activeCharacter)));
        }
    }
}
