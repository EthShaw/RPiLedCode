package us.shsrobotics.ledcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;

public class NumberedScript extends LedScript
{
    private LedStrip _LedStrip;
    private List<Integer> leds;

    public NumberedScript(String numbers)
    {
        if (numbers != null) {
            String[] numStrs = numbers.split(",");

            leds = new ArrayList<Integer>(numStrs.length);

            for (String str : numStrs) {
                try {
                    leds.add(Integer.parseInt(str));
                } catch (NumberFormatException e) {
                }
            }

        }
    }

    @Override
    public void Setup(LedStrip strip)
    {
        _LedStrip = strip;
        SetDelay(200);
    }

    @Override
    public void Update()
    {
        _LedStrip.setStrip(new Color(0x000000));

        for (Integer idx : leds) {
            _LedStrip.setPixel(idx, new Color(0xFFFFFF));
        }
    }
}