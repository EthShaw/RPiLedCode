package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.Color;
import java.util.ArrayList;

import com.github.mbelling.ws281x.LedStrip;

public class ColorWaves extends TimedLedScript
{
    private LedStrip _Lights;

    @Override
    public void Setup(LedStrip strip)
    {
        SetDelay(16 * 2);
        
        //if (Arguments.length > 0)
        //    if (!Integer.TryParse(Arguments[0], out _TimeToRun))
        //        _TimeToRun = TIME_INFINITE;
        _TimeToRun = 30000;

        _Lights = strip;// new LedArray(strip, 0, 150);
    }

    int offsetWave = 0;
    double offsetColor = 0;

    @Override
    public void Update()
    {
        super.Update();

        for (int i = 0; i < 150; i++)
        {
            // Wavelength of 25, min of 0.35, max of 1 (created the wave on
            // Desmos to visualize it and then copied the function)
            double adjustment = Math.sin(0.251327412 * (i + offsetWave)) * 0.325 + 0.675;

            _Lights.setPixel(i, new Color(ColorUtils.ApplyBrightness(
                ColorUtils.HSL2RGB(offsetColor / 150D,
                adjustment,
                0.5),
                0.5)));
        }

        offsetWave++;
        offsetWave %= 150;

        offsetColor += 0.2;
        offsetColor %= 150;

        /*_Display.Insert(0, Spectrum(offset));
        offset += 4;

        while (_Display.Count > 150)
            _Display.RemoveAt(_Display.Count - 1);

        offset %= SPECTRUM_LENGTH;

        for (int j = 0; j < _Display.Count; j++)
            _Lights.SetLed(j, _Display[j]);*/
    }
}
