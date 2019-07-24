package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.Color;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class ClearScript extends LedScript
{
    private ILedGroup _LedStrip;

    @Override
    public void Setup(ILedGroup strip)
    {
        SetDelay(200);
        _LedStrip = strip;
    }

    @Override
    public void Update()
    {
        _LedStrip.setStrip(Color.BLACK);
    }
}
