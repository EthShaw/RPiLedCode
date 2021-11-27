package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.Color;

import org.json.JSONObject;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class ColorWaves extends LedScript
{
    private ILedGroup _Lights;
    private boolean _IsMultiColor = true;
    private double _Hue = 0;

    public ColorWaves(String args)
    {
        if (args == null) {
            args = "";
        }

        JSONObject obj = ArgumentHelper.parseArguments(args);

        if (obj != null && obj.has("hue")) {
            String s = obj.get("hue").toString();

            if (s != null) {
                try {
                    double color = Double.parseDouble(s);
                    _IsMultiColor = false;
                    _Hue = color;
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public void Setup(ILedGroup strip)
    {
        SetDelay(16 * 2);

        _Lights = strip;
    }

    int offsetWave = 0;
    double offsetColor = 0;

    @Override
    public void Update()
    {
        for (int i = 0; i < _Lights.getLength(); i++)
        {
            // Wavelength of 25, min of 0.35, max of 1 (created the wave on
            // Desmos to visualize it and then copied the function)
            double adjustment = Math.sin(0.251327412 * (i + offsetWave)) * 0.325 + 0.675;

            double hue;

            if (_IsMultiColor) {
                hue = offsetColor / 150D;
            } else {
                hue = _Hue;
            }
            
            Color color = new Color(ColorUtils.ApplyBrightness(
                        ColorUtils.HSL2RGB(hue,
                            adjustment,
                            0.5),
                        0.5));;

            _Lights.setPixel(i, color);
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
