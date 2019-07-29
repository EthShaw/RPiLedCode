package us.shsrobotics.ledcode;

import java.util.ArrayList;

import com.github.mbelling.ws281x.Color;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class MultiColorScript extends TimedLedScript
{
    private ILedGroup _Lights;

    @Override
    public void Setup(ILedGroup strip)
    {
        SetDelay(16);
        
        //if (Arguments.length > 0)
        //    if (!Integer.TryParse(Arguments[0], out _TimeToRun))
        //        _TimeToRun = TIME_INFINITE;
        _TimeToRun = 30000;

        _Lights = strip;// new LedArray(strip, 0, 150);
    }

    private static final double tau = Math.PI * 2;

    int mainImage(int idx)
    {
        double percent = idx / 150D;

        int r = (int)Math.round(Math.sqrt(Math.sin((percent + 0D) / 3 * tau) * .5 + .5) * 255);
        int g = (int)Math.round(Math.sqrt(Math.sin((percent + 2D) / 3 * tau) * .5 + .5) * 255);
        int b = (int)Math.round(Math.sqrt(Math.sin((percent + 1D) / 3 * tau) * .5 + .5) * 255);

        return (int)((r << 16) | (g << 8) | b);
    }

    private static final int SPECTRUM_LENGTH = 256 * 3;

    private int Spectrum(int idx)
    {
        int r;
        int g;
        int b;

        if (idx < 256)
        {
            r = 255 - (int)idx;
            g = (int)idx;
            b = 0;
        }
        else if (idx >= 256 && idx < 512)
        {
            r = 0;
            g = 255 - ((int)idx - 256);
            b = ((int)idx - 256);
        }
        else
        {
            r = ((int)idx - 512);
            g = 0;
            b = 255 - ((int)idx - 512);
        }

        return ColorUtils.FromRGB(r, g, b);
    }

    private int Rainbow(int idx)
    {
        return ColorUtils.HSL2RGB((double)idx / SPECTRUM_LENGTH, 1, 0.5);
    }

    int offset = 0;

    ArrayList<Integer> _Display = new ArrayList<Integer>();

    private static final boolean ADJUST_BRIGHTNESS = true;

    @Override
    public void Update()
    {
        super.Update();

        for (int i = 0; i < _Lights.getLength(); i++)
        {
            double adjustment = ADJUST_BRIGHTNESS ? ((i + (25 - (offset % 25))) % 25) / 38.4615385D + 0.35D : 1.0;

            _Lights.setPixel(i, new Color(
                ColorUtils.ApplyBrightness(ColorUtils.HSL2RGB(((i + (149 - offset)) % 150) / 150D,
                1,
                0.5), adjustment)));
        }

        offset++;
        offset %= 150;

        /*_Display.Insert(0, Spectrum(offset));
        offset += 4;

        while (_Display.Count > 150)
            _Display.RemoveAt(_Display.Count - 1);

        offset %= SPECTRUM_LENGTH;

        for (int j = 0; j < _Display.Count; j++)
            _Lights.SetLed(j, _Display[j]);*/
    }
}
