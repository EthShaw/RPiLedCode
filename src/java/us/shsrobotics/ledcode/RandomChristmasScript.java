package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.Color;

import com.github.mbelling.ws281x.LedStrip;

public class RandomChristmasScript extends LedScript
{
    private LedStrip _Lights;
    private Color[] _Pixels;
    private static final int LENGTH = 150;
    private static final double BRIGHTNESS = 0.35;
    private static final Color GREEN = new Color(ColorUtils.ApplyBrightness(0x00FF00, BRIGHTNESS));
    private static final Color RED = new Color(ColorUtils.ApplyBrightness(0xFF0000, BRIGHTNESS));

    @Override
    public void Setup(LedStrip strip)
    {
        SetDelay(1000 / 2);

        _Lights = strip;
        _Pixels = new Color[LENGTH];

        for (int i = 0; i < _Pixels.length; i++) {
            _Pixels[i] = Math.random() < 0.5 ? GREEN : RED;
        }
    }

    //int index = -LENGTH + 1;

    @Override
    public void Update()
    {
        for (int index = 0; index < _Pixels.length; index++)
            _Pixels[Math.abs(index)] = Math.random() < 0.5 ? GREEN : RED;
            //_Pixels[Math.abs(index)] = Math.random() < 0.2 ? (Math.random() < 0.5 ? GREEN : RED) : (_Pixels[Math.abs(index)] == GREEN ? RED : GREEN);

        //int idx2 = (Math.abs(index) + (LENGTH / 2)) % LENGTH;

        //_Pixels[idx2] = _Pixels[idx2]  == GREEN ? RED : GREEN;

        //index++;

        //if (index >= LENGTH) {
        //    index = -LENGTH + 1;
        //}

        for (int i = 0; i < _Pixels.length; i++) {
            _Lights.setPixel(i, _Pixels[i]);
        }
    }
}
