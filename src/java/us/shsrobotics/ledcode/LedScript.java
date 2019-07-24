package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.LedStrip;

import us.shsrobotics.ledcode.utils.ILedGroup;

public abstract class LedScript
{
    protected boolean IsFinished;
    private int _Delay;
    protected String[] Arguments;

    public LedScript()
    {
        Arguments = new String[0];
    }

    public boolean getIsFinished()
    {
        return IsFinished;
    }

    public void SetArguments(String[] args)
    {
        if (args != null)
            Arguments = args;
        else
            Arguments = new String[0];
    }

    public void SetDelay(int delay)
    {
        if (delay < 0)
            throw new IllegalArgumentException("Delay cannot be negative.");
        _Delay = delay;
    }

    public int GetDelay()
    {
        return _Delay;
    }

    public abstract void Setup(ILedGroup parent);
    public abstract void Update();

    public void Cleanup()
    {
    }
}