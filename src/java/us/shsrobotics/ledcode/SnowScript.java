package us.shsrobotics.ledcode;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class SnowScript extends LedScript
{
    private ILedGroup _Lights;

    @Override
    public void Setup(ILedGroup strip)
    {
        SetDelay(16 * 2);

        _Lights = strip;
    }

    @Override
    public void Update()
    {
    }
}
