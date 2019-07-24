package us.shsrobotics.ledcode.utils;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;

// A helper class for grouping parts of LedScripts
public class DirectLedGroup implements ILedGroup
{
    private LedStrip _Strip;

    public DirectLedGroup(LedStrip strip) {
        _Strip = strip;
    }

    @Override
    public void setPixel(int pixel, Color color) {
        _Strip.setPixel(pixel, color);
    }

    @Override
    public void setStrip(Color color) {
        _Strip.setStrip(color);
    }

    @Override
    public int getLength() {
        return _Strip.getLedsCount();
    }
}