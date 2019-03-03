package us.shsrobotics.ledcode;

public final class ColorUtils
{
    /// <sumary>
    /// Sets the color brightness based on parameter brightness.
    /// brightness is between 0 and 1
    /// color is the color to use.
    /// </sumary>
    public static int ApplyBrightness(int color, double brightness)
    {
        if (brightness < 0 || brightness > 1)
            throw new IllegalArgumentException("brightness cannot be less than 0 or greater than 1!");
        
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;

        r = (int)Math.round(r * brightness);
        g = (int)Math.round(g * brightness);
        b = (int)Math.round(b * brightness);

        return FromRGB(r, g, b);
    }

    public static int FromRGB(int r, int g, int b)
    {
        return ((r & 0xFF) << 16) | ((g & 0xFF) << 8) | (b & 0xFF);
    }

    /// Heavily based on / from https://geekymonkey.com/Programming/CSharp/RGB2HSL_HSL2RGB.htm
    /// Converts HSL to RGB
    public static int HSL2RGB(double h, double s, double l)
    {
        if (h < 0 || h > 1)
            throw new IllegalArgumentException("Saturation, s, cannot be less than zero or greater than 1");
        if (s < 0 || s > 1)
            throw new IllegalArgumentException("Saturation, s, cannot be less than zero or greater than 1");
        if (l < 0 || l > 1)
            throw new IllegalArgumentException("Light, l, cannot be less than zero or greater than 1");

        double r = 1;
        double g = 1;
        double b = 1;
        double v = (l <= 0.5) ? (l * (1.0 + s)) : (l + s - l * s);
            
        if (v > 0)
        {
            double m;
            double sv;
            int sextant;
            double fract, vsf, mid1, mid2;

            m = l + l - v;
            sv = (v - m) / v;
            h *= 6.0;
            sextant = (int)h;
            fract = h - sextant;
            vsf = v * sv * fract;
            mid1 = m + vsf;
            mid2 = v - vsf;

            switch (sextant)
            {
                case 0:
                    r = v;
                    g = mid1;
                    b = m;
                    break;
                case 1:
                    r = mid2;
                    g = v;
                    b = m;
                    break;
                case 2:
                    r = m;
                    g = v;
                    b = mid1;
                    break;
                case 3:
                    r = m;
                    g = mid2;
                    b = v;
                    break;
                case 4:
                    r = mid1;
                    g = m;
                    b = v;
                    break;
                case 5:
                    r = v;
                    g = m;
                    b = mid2;
                    break;
            }
        }

        return ColorUtils.FromRGB((int)Math.round(r * 255.0f),
            (int)Math.round(g * 255.0f),
            (int)Math.round(b * 255.0f));
    }
}