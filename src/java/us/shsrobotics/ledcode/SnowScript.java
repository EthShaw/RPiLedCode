package us.shsrobotics.ledcode;

import java.util.ArrayList;
import java.util.List;

import com.github.mbelling.ws281x.Color;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class SnowScript extends LedScript
{
    private static final Color COLOR_LIGHT = new Color(0xADD8E6);
    private static final Color COLOR_DARK = new Color(0x5555FF);
    private static final Color COLOR_BG = Color.BLACK;
    private ILedGroup _Lights;
    private List<Color> _Snowflakes;

    @Override
    public void Setup(ILedGroup strip)
    {
        SetDelay(150);

        _Lights = strip;
        _Snowflakes = new ArrayList<Color>(_Lights.getLength());
    }

    @Override
    public void Update()
    {
        double val = Math.random();

        if (val > 0.65D) {
            if (val > 0.75) {
                _Snowflakes.add(0, COLOR_LIGHT);
            } else {
                _Snowflakes.add(0, COLOR_DARK);
            }
        }
        else {
            _Snowflakes.add(0, COLOR_BG);
        }

        while (_Snowflakes.size() > _Lights.getLength()) {
            _Snowflakes.remove(_Snowflakes.size() - 1);
        }

        for (int i = 0; i < _Snowflakes.size(); i++) {
            _Lights.setPixel(i, _Snowflakes.get(i));
        }
    }
}
