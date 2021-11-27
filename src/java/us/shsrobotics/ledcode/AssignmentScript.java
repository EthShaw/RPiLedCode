package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.Color;

import com.github.mbelling.ws281x.LedStrip;

import org.json.JSONObject;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class AssignmentScript extends LedScript
{
    private ILedGroup _Lights;
    private Color[] _Pixels;
    private static final double BRIGHTNESS = 0.35;
    private String _Args;

    public AssignmentScript(String args)
    {
        if (args == null) {
            _Args = "";
        } else {
            _Args = args;
        }
    }

    @Override
    public void Setup(ILedGroup strip)
    {
        SetDelay(100);
        _Lights = strip;

        JSONObject obj = ArgumentHelper.parseArguments(_Args);
        _Pixels = new Color[strip.getLength()];
        
        if (obj != null) {
            if (obj.has("color")) {
                Color color = new Color(Integer.parseInt(obj.get("color").toString(), 16));
                
                for (int i = 0; i < _Pixels.length; i++) {
                    _Pixels[i] = color;
                }
            }
        } else {
            for (int i = 0; i < _Pixels.length; i++) {
                _Pixels[i] = Color.BLACK;
            }
        }
    }

    @Override
    public void Update()
    {
        for (int i = 0; i < _Pixels.length; i++) {
            _Lights.setPixel(i, _Pixels[i]);
        }
    }
}
