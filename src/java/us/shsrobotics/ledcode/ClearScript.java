package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;

public class ClearScript extends LedScript
{
    private LedStrip _LedStrip;

    @Override
    public void Setup(LedStrip strip)
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
