package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.Color;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class ChristmasColorWaves extends TimedLedScript
{
    private ILedGroup _Lights;

    @Override
    public void Setup(ILedGroup strip)
    {
        SetDelay(16 * 2);

        _Lights = strip;
    }

    int offsetWave = 0;

    @Override
    public void Update()
    {
        super.Update();

        for (int i = 0; i < _Lights.getLength(); i++)
        {
            float x = i + offsetWave;

            // Wavelength of 25, min of 0.35, max of 1 (created the wave on
            // Desmos to visualize it and then copied the function)
            //double saturation = Math.sin((2 * Math.PI) / 25 * x) * (13D / 40D) + (27D / 40D);
            //double saturation = Math.abs(Math.sin((2 * Math.PI) / 24 * x)) * (13D / 40D) + (27D / 40D);

            // Used Desmos to create this function as well. Alternates between
            // red and green whenever the saturation dips to its lowest
            //double hue = Math.round(Math.sin(Math.PI / 25 * x + 0.25 * Math.PI/* - 0.5 * Math.PI*/) * 0.5 + 0.5) == 0 ? 0 : 1.0 / 3.0;
            double hue = Math.round(Math.sin(Math.PI * 2 / 24 * x/* - 0.5 * Math.PI*/) * 0.5 + 0.5) == 0 ? 0 : 1.0 / 3.0;

            _Lights.setPixel(i, new Color(ColorUtils.ApplyBrightness(
                ColorUtils.HSL2RGB(hue,
                1,//saturation,
                0.5),
                0.35/* * saturation*/)));
        }

        offsetWave++;
    }
}
