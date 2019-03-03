package us.shsrobotics.ledcode;

import java.util.Random;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;

class SuperRandomScript extends LedScript
{
    private LedStrip _Leds;
    private Random _Rand;

    @Override
    public void Setup(LedStrip strip)
    {
        SetDelay(32);
        _Rand = new Random();
        _Leds = strip;
    }

    @Override
    public void Update()
    {
        for (int i = 0; i < 150; i++)
        {
            int color = ColorUtils.HSL2RGB(_Rand.nextInt(1000) / 999D, 1, 0.4);//(int)(_Rand.nextInt() & 0xFFFFFF);
            _Leds.setPixel(i, new Color(color));
        }
    }
}