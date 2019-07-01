package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;

public class WholeMultiColorScript extends LedScript {
    private LedStrip _Lights;

    @Override
    public void Setup(LedStrip parent) {
        _Lights = parent;
        SetDelay(67);
    }

    private float offset = 0F;

    @Override
    public void Update() {
        offset += 0.002F;
        
        if (offset > 1F)
            offset -= 1F;

        _Lights.setStrip(new Color(ColorUtils.HSL2RGB(offset, 1, 0.5)));        
    }

}