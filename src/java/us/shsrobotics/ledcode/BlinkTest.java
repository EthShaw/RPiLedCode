package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.Color;
import java.util.ArrayList;

import com.github.mbelling.ws281x.LedStrip;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class BlinkTest extends LedScript
{
    private ILedGroup _Lights;

    @Override
    public void Setup(ILedGroup strip)
    {
        SetDelay(16 * 2);

        _Lights = strip;
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

    int offset = 0;

    ArrayList<Integer> _Display = new ArrayList<Integer>();

    private static final boolean ADJUST_BRIGHTNESS = true;

    @Override
    public void Update()
    {
        double adjustment = ADJUST_BRIGHTNESS ? ((25 - (offset % 25)) % 25) / 38.4615385D + 0.35D : 1.0;

        _Lights.setStrip(new Color(
            ColorUtils.ApplyBrightness(ColorUtils.HSL2RGB(((149 - offset) % 150) / 150D,
            adjustment,
            0.5), 0.5)));

        offset++;
        offset %= 150;
    }
}
