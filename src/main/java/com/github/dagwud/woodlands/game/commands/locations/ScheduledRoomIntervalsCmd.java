package com.github.dagwud.woodlands.game.commands.locations;

import com.github.dagwud.woodlands.game.commands.core.AbstractCmd;
import com.github.dagwud.woodlands.game.commands.core.AbstractRoomCmd;
import com.github.dagwud.woodlands.game.commands.core.RunLaterCmd;
import com.github.dagwud.woodlands.game.domain.Tuple;

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
  public void execute() throws Exception
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
          System.out.println(scheduledCommand.getTwo().getClass() + " exceptioned with " + ex.toString());
        }
      }
    }

    for (Tuple<Long, AbstractRoomCmd> firedCommand : reschedule)
    {
      scheduledCommands.remove(firedCommand);

      scheduledCommands.add(new Tuple<>(now + firedCommand.getTwo().getInterval(), firedCommand.getTwo()));
    }

    new RunLaterCmd(1000, this, false).go();
  }
}
