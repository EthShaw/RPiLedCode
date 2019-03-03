package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.LedStrip;

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

    public abstract void Setup(LedStrip parent);
    public abstract void Update();

    public void Cleanup()
    {
    }
}