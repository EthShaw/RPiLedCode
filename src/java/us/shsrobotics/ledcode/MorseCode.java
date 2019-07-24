package us.shsrobotics.ledcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.mbelling.ws281x.Color;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class MorseCode extends LedScript
{
    private ILedGroup _LedStrip;
    private Map<Character, String> _CodeMap;
    private String text;// = "WE ARE SPARTANS 5724";
    private ArrayList<Boolean> code = new ArrayList<Boolean>();

    public MorseCode(String msg)
    {
        if (msg == null) {
            msg = "";
        }

        // Pause in between each replay of the message
        text = msg.toUpperCase() + "    ";

        _CodeMap = new HashMap<Character, String>() {{
            put('A', ".-");
            put('B', "-...");
            put('C', "-.-.");
            put('D', "-..");
            put('E', ".");
            put('F', "..-.");
            put('G', "--.");
            put('H', "....");
            put('I', "..");
            put('J', ".---");
            put('K', "-.-");
            put('L', ".-..");
            put('M', "--");
            put('N', "-.");
            put('O', "---");
            put('P', ".--.");
            put('Q', "--.-");
            put('R', ".-.");
            put('S', "...");
            put('T', "-");
            put('U', "..-");
            put('V', "...-");
            put('W', ".--");
            put('X', "-..-");
            put('Y', "-.--");
            put('Z', "--..");
            put('0', "-----");
            put('1', ".----");
            put('2', "..---");
            put('3', "...--");
            put('4', "....-");
            put('5', ".....");
            put('6', "-....");
            put('7', "--...");
            put('8', "---..");
            put('9', "----.");
        }};
    }

    private void GenerateMorseCode()
    {
        char[] textChars = text.toUpperCase().toCharArray();

        for (char c : textChars)
        {
            String binData = _CodeMap.get(c);
            char[] segment = null;

            if (binData != null) {
                segment = binData.toCharArray();
            }
            

            if (segment != null)
            {
                for (char dotdash : segment)
                {
                    if (dotdash == '.')
                    {
                        // Dot (one unit)
                        code.add(true);
                    }
                    else if (dotdash == '-')
                    {
                        // Dash (three units)
                        for (int i = 0; i < 3; i++)
                            code.add(true);
                    }

                    // Space between parts of the same letter is one unit
                    code.add(false);
                }

                // Space between letters is three units, but we
                // already inserted one above
                for (int i = 0; i < 2; i++)
                    code.add(false);
            }
            else
            {
                // Space between words is seven units, but we already
                // inserted three above
                for (int i = 0; i < 4; i++)
                    code.add(false);
            }
        }
    }

    @Override
    public void Setup(ILedGroup strip)
    {
        _LedStrip = strip;
        SetDelay(200);
        GenerateMorseCode();
    }

    private ArrayList<Integer> _Display = new ArrayList<Integer>();
    private boolean _IsSilver = false;
    private int _Pos = 0;

    @Override
    public void Update()
    {
        _LedStrip.setStrip(new Color(0x000000));

        if (code.get(_Pos))
            _Display.add(0, _IsSilver ? 0xC0C0C0 : 0x800000);
        else
            _Display.add(0, 0x000000);


        while (_Display.size() > 150)
            _Display.remove(_Display.size() - 1);


        for (int i = 0; i < _Display.size(); i++)
            if (_Display.get(i) != 0)
                _LedStrip.setPixel(i, new Color(_Display.get(i)));

        _Pos++;

        if (_Pos >= code.size())
        {
            _Pos = 0;
            _IsSilver = !_IsSilver;
        }
    }
}