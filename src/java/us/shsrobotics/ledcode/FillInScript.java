package us.shsrobotics.ledcode;

import java.util.ArrayList;
import java.util.List;

import com.github.mbelling.ws281x.Color;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class FillInScript extends LedScript
{
    private ILedGroup _Lights;
    private Color BG_COLOR = new Color(0x000007);
    private Color FG_COLOR = new Color(ColorUtils.ApplyBrightness(0xFFFFFF, 0.4));
    private boolean[] Pixels;
    private List<Integer> unfilledPixels;

    @Override
    public void Setup(ILedGroup strip)
    {
        SetDelay(250);
        _Lights = strip;
        Pixels = new boolean[_Lights.getLength()];
        
        unfilledPixels = new ArrayList<Integer>(_Lights.getLength());
    }

    @Override
    public void Update()
    {
        if (unfilledPixels.size() == 0) {
            for (int i = 0; i < Pixels.length; i++) {
                unfilledPixels.add(i);
            }

            for (int i = 0; i < Pixels.length; i++) {
                Pixels[i] = false;
            }
        } else {
            int idx = (int)Math.floor(Math.random() * unfilledPixels.size());
            int pixel = unfilledPixels.remove(idx);

            Pixels[pixel] = true;
        }

        for (int i = 0; i < Pixels.length; i++) {
            if (Pixels[i]) {
                _Lights.setPixel(i, FG_COLOR);
            } else {
                _Lights.setPixel(i, BG_COLOR);
            }
        }
    }
}
