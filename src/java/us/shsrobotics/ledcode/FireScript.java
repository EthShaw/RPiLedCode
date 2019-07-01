package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;

public class FireScript extends LedScript {
    private LedStrip _Lights;

    @Override
    public void Setup(LedStrip parent) {
        _Lights = parent;
        SetDelay(17);
    }

    int offset = 0;

    @Override
    public void Update() {
        offset = 0;
        for (int i = 0; i < 150; i++) {
            double x = ((i + offset) / 30D) % 1;
            double bright = 0.5 * Math.sin(Math.sin(Math.sin(5 * x) + x)) + 0.5;
            
            _Lights.setPixel(i + offset, new Color(ColorUtils.ApplyBrightness(ColorUtils.HSL2RGB(0, x, 0.5), bright)));
        }
        
        offset++;
    }

}