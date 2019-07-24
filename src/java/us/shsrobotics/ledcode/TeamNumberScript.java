package us.shsrobotics.ledcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class TeamNumberScript extends LedScript
{
    private ILedGroup _LedStrip;
    private List<Integer> leds;
    private int[] TEAM_NUMBER = { 5, 7, 2, 4 };

    public TeamNumberScript()
    {
        String numbers = "0,1,2,3,4,6,7,8,9,10,11,12,14,15,17,18,19,20,22,23,24,25,26,28,29,30,31,32,33,34,36,37,39,40,41,42,44,45,46,47,48,50,51,52,53,54,55,56,58,59,61,62,63,64,66,67,68,69,70,72,73,74,75,76,77,78,80,81,83,84,85,86,88,89,90,91,92,94,95,96,97,98,99,100,102,103,105,106,107,108,110,111,112,113,114,116,117,118,119,120,121,122,124,125,127,128,129,130,132,133,134,135,136,138,139,140,141,142,143,144,146,147,149";
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
    public void Setup(ILedGroup strip)
    {
        _LedStrip = strip;
        SetDelay(200);
    }

    private final Color WHITE_COLOR = new Color(ColorUtils.ApplyBrightness(0xFFFFFF, 0.333));

    @Override
    public void Update()
    {
        _LedStrip.setStrip(Color.BLACK);

        for (Integer idx : leds) {
            _LedStrip.setPixel(idx, WHITE_COLOR);
        }
    }
}