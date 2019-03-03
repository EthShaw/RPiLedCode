package us.shsrobotics.ledcode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;

public class BinaryScript extends LedScript
{
    private LedStrip _LedStrip;

    private Map<Character, String> _CodeMap;

    public BinaryScript() throws Exception
    {
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

    private String text = "WE ARE SPARTANS 5724";

    private ArrayList<Boolean> code = new ArrayList<Boolean>();

    private void GenerateMorseCode()
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
        SetDelay(200);

        // Pause in between each replay of the message
        text = text + "    ";

        _LedStrip = strip;

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