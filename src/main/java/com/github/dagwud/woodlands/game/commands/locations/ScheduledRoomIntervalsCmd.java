package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.CommandDelegate;
import com.github.dagwud.woodlands.game.Settings;
import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractRoomCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.Tuple;
import com.github.dagwud.woodlands.game.log.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * This is for room interval actions that should happen regardless of human input -
 * that is, not suitable for a refactor of the combat zone scheduled tasks, those
 * are too party dependent.
 * <p>
 * Going with a minimum granularity of around 1s for now. I picked that randomly,
 * but at least it will just be one thread.
 */
public class ScheduledRoomIntervalsCmd extends AbstractCmd
{
  private List<Tuple<Long, AbstractRoomCmd>> scheduledCommands;

  public ScheduledRoomIntervalsCmd(List<Tuple<Long, AbstractRoomCmd>> scheduledCommands)
  {
    this.scheduledCommands = scheduledCommands;
  }

  @Override
  public void execute()
  {
    long now = System.currentTimeMillis();

    List<Tuple<Long, AbstractRoomCmd>> reschedule = new ArrayList<>();

    for (Tuple<Long, AbstractRoomCmd> scheduledCommand : scheduledCommands)
    {
      if (scheduledCommand.getOne() < now)
      {
        try
        {
          scheduledCommand.getTwo().execute();
          reschedule.add(scheduledCommand);
        } catch (Exception ex)
        {
          String message = "Hey giraffe - a room command exception'd. It sha'n't get triggered again: " + ex.toString();
          Logger.error(message);
        }
      }
    }

    for (Tuple<Long, AbstractRoomCmd> firedCommand : reschedule)
    {
      scheduledCommands.remove(firedCommand);

      scheduledCommands.add(new Tuple<>(now + firedCommand.getTwo().getInterval(), firedCommand.getTwo()));
    }

    CommandDelegate.execute(new RunLaterCmd(Settings.ROOM_INTERVAL_MS, this, false));
  }
}
