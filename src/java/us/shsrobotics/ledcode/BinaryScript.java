package us.shsrobotics.ledcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;

import org.json.JSONObject;

public class BinaryScript extends LedScript
{
    private LedStrip _LedStrip;
    private Map<Character, String> _CodeMap;
    private String text;
    private boolean wholeStripMode;
    private ArrayList<Boolean> code = new ArrayList<Boolean>();
    private static final int MAROON_HEX = 0x800000;
    private static final int SILVER_HEX = 0xC0C0C0;
    private static final Color MAROON_COLOR = new Color(0x800000);
    private static final Color SILVER_COLOR = new Color(0xC0C0C0);

    public BinaryScript(String json)
    {
        if (json == null) {
            json = "";
        }

        JSONObject args = ArgumentHelper.parseArguments(json);

        if (args != null && args.has("msg")) {
            // Spaces for a pause in between each replay of the message
            text = args.get("msg").toString() + "    ";
        } else {
            text = "   ";
        }
        
        if (args != null && args.has("mode")) {
            String mode = args.get("mode").toString();

            if (mode.equalsIgnoreCase("WholeStrip")) {
                wholeStripMode = true;
            } else {
                wholeStripMode = false;
            }
        }
        
        _CodeMap = new HashMap<Character, String>() {{
            put('A', "01000001");
            put('B', "01000010");
            put('C', "01000011");
            put('D', "01000100");
            put('E', "01000101");
            put('F', "01000110");
            put('G', "01000111");
            put('H', "01001000");
            put('I', "01001001");
            put('J', "01001010");
            put('K', "01001011");
            put('L', "01001100");
            put('M', "01001101");
            put('N', "01001110");
            put('O', "01001111");
            put('P', "01010000");
            put('Q', "01010001");
            put('R', "01010010");
            put('S', "01010011");
            put('T', "01010100");
            put('U', "01010101");
            put('V', "01010110");
            put('W', "01010111");
            put('X', "01011000");
            put('Y', "01011001");
            put('Z', "01011010");
            put('0', "00110000");
            put('1', "00110001");
            put('2', "00110010");
            put('3', "00110011");
            put('4', "00110100");
            put('5', "00110101");
            put('6', "00110110");
            put('7', "00110111");
            put('8', "00111000");
            put('9', "00111001");
            put(' ', "00100000");
        }};
    }

    private void GenerateBinaryCode()
    {
        char[] textChars = text.toUpperCase().toCharArray();

        for (char c : textChars)
        {
            char[] segment = _CodeMap.get(c).toCharArray();

            if (segment != null)
            {
                for (char bin : segment)
                {
                    if (bin == '1')
                    {
                        code.add(true);
                    }
                    else if (bin == '0')
                    {
                        code.add(false);
                    }
                }
            }
        }
    }

    @Override
    public void Setup(LedStrip strip)
    {
        _LedStrip = strip;
        if (wholeStripMode) {
            SetDelay(20);
        } else {
            SetDelay(200);
        }
        GenerateBinaryCode();
    }

    private ArrayList<Color> _Display = new ArrayList<Color>();
    private boolean _IsSilver = false;
    private int _Pos = 0;
    private int _Time = 0;
    private boolean _Restarted;

    @Override
    public void Update()
    {
        _LedStrip.setStrip(new Color(0x000000));

        if (wholeStripMode) {
            _Time++;

            int mod = _Time % 21;

            if (_Restarted) {
                double brightness = Math.max(0, (1.0D - (Math.abs(mod - 10) / 10.0D)) / 1.5 - 0.333);

                Color maroon = new Color(ColorUtils.ApplyBrightness(MAROON_HEX, brightness));
                Color silver = new Color(ColorUtils.ApplyBrightness(SILVER_HEX, brightness));

                for (int i = 0; i < 150; i++) {
                    _LedStrip.setPixel(i, i % 2 == 0 ? maroon : silver);
                }
            } else {
                double brightness = Math.max(0, (1.0D - (Math.abs(mod - 10) / 10.0D)) / 1.5 - 0.333);

                if (code.get(_Pos)) {
                    // Red is 1
                    _LedStrip.setStrip(new Color(ColorUtils.ApplyBrightness(MAROON_HEX, brightness)));
                } else {
                    // Silver is 0
                    _LedStrip.setStrip(new Color(ColorUtils.ApplyBrightness(SILVER_HEX, brightness)));
                }
            }

            if (mod == 0) {
                _Restarted = false;
                _Pos++;
            }
        } else {
            if (code.get(_Pos))
                _Display.add(0, _IsSilver ? SILVER_COLOR : MAROON_COLOR);
            else
                _Display.add(0, null);


            while (_Display.size() > 150)
                _Display.remove(_Display.size() - 1);


            for (int i = 0; i < _Display.size(); i++)
                if (_Display.get(i) != null)
                    _LedStrip.setPixel(i, _Display.get(i));
            
            _Pos++;
        }

        if (_Pos >= code.size())
        {
            _Pos = 0;
            _Restarted = true;
            _IsSilver = !_IsSilver;
        }
    }
}