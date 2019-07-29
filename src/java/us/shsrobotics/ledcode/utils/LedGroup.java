package us.shsrobotics.ledcode.utils;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;

// A helper class for grouping parts of LedScripts
public class LedGroup implements ILedGroup
{
    private int[] _Leds;
    private LedStrip _Strip;

    public LedGroup(LedStrip strip, int[] leds) {
        _Leds = new int[leds.length];
        _Strip = strip;

        for (int i = 0; i < leds.length; i++) {
            _Leds[i] = leds[i];
        }
    }

    public LedGroup(LedStrip strip, int start, int end, boolean reversed) {
        _Strip = strip;
        if (start >= end) {
            throw new InstantiationError("end must be greater than start for an LedGroup!");
        } else if (strip == null) {
            throw new IllegalArgumentException("strip cannot be null when initializing an LedGroup!");
        }

        _Leds = new int[end - start];

        if (reversed) {
            for (int i = start; i < end; i++) {
                int idx = i - start;
                _Leds[_Leds.length - idx - 1] = i;
            }
        } else {
            for (int i = start; i < end; i++) {
                int idx = i - start;
                _Leds[idx] = i;
            }
        }
    }

    public LedGroup(LedStrip strip, int start, int end) {
        this(strip, start, end, false);
    }

    @Override
    public void setPixel(int pixel, Color color) {
        if (pixel < 0 || pixel >= _Leds.length) {
            return;
        }

        _Strip.setPixel(_Leds[pixel], color);
    }

    @Override
    public void setStrip(Color color) {
        for (int i = 0; i < _Leds.length; i++) {
            _Strip.setPixel(_Leds[i], color);
        }
    }

    @Override
    public int getLength() {
        return _Leds.length;
    }
}