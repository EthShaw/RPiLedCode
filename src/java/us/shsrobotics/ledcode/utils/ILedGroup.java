package us.shsrobotics.ledcode.utils;

import com.github.mbelling.ws281x.Color;

public interface ILedGroup
{
    public int getLength();

    public void setPixel(int pixel, Color color);

    public void setStrip(Color color);
}