package us.shsrobotics.ledcode;

import java.util.ArrayList;
import java.util.List;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;

public class FillInScript extends LedScript
{
    private LedStrip _Lights;
    private Color BG_COLOR = new Color(0x00000F);
    private Color FG_COLOR = new Color(ColorUtils.ApplyBrightness(0xFFFFFF, 0.35));
    private boolean[] Pixels;
    private List<Integer> unfilledPixels;

    @Override
    public void Setup(LedStrip strip)
    {
        SetDelay(500);
        _Lights = strip;
        Pixels = new boolean[150];
        
        unfilledPixels = new ArrayList<Integer>(150);
    }

    @Override
    public void Update()
    {

        if (unfilledPixels.size() == 0) {
            for (int i = 0; i < 150; i++) {
                unfilledPixels.add(i);
            }

            for (int i = 0; i < 1000; i++) {
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
