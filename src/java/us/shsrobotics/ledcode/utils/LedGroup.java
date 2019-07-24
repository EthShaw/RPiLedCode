package us.shsrobotics.ledcode.utils;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;

// A helper class for grouping parts of LedScripts
public class LedGroup implements ILedGroup
{
    private int[] _Leds;
    private LedStrip _Strip;
    private final int _Length;

    public LedGroup(LedStrip strip, int[] leds) {
        _Length = leds.length;
        _Leds = new int[_Length];
        _Strip = strip;

        for (int i = 0; i < _Length; i++) {
            _Leds[i] = leds[i];
        }
    }

    public LedGroup(LedStrip strip, int start, int end, boolean reversed) {
        _Strip = strip;
        if (start <= end) {
            throw new InstantiationError("end must be greater than start for an LedGroup!");
        } else if (strip == null) {
            throw new IllegalArgumentException("strip cannot be null when initializing an LedGroup!");
        }

        _Length = end - start;
        _Leds = new int[_Length];

        if (reversed) {
            for (int i = start; i < end; i++) {
                _Leds[_Length - i - 1] = i;
            }
        } else {
            for (int i = start; i < end; i++) {
                _Leds[i - start] = i;
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

        _Strip.setPixel(pixel, color);
    }

    @Override
    public void setStrip(Color color) {
        for (int i = 0; i < _Length; i++) {
            _Strip.setPixel(i, color);
        }
    }

    @Override
    public int getLength() {
        return _Length;
    }
}