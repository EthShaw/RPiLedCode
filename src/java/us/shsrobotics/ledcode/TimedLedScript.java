package us.shsrobotics.ledcode;

public abstract class TimedLedScript extends LedScript
{
    protected static final int TIME_INFINITE = Integer.MIN_VALUE;
    protected int _TimeToRun = TIME_INFINITE;
    private boolean _IsFirstRun = true;
    private long _EndTime;

    @Override
    public void Update()
    {
        if (_TimeToRun != TIME_INFINITE)
        {
            if (_IsFirstRun)
            {
                _IsFirstRun = false;
                _EndTime = System.currentTimeMillis() + (_TimeToRun);
            }
            else if (System.currentTimeMillis() > _EndTime)
            {
                IsFinished = true;
            }
        }
    }
}