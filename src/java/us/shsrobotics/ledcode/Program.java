package us.shsrobotics.ledcode;

import java.util.Arrays;
import java.util.stream.IntStream;

import com.github.mbelling.ws281x.LedStrip;
import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import us.shsrobotics.ledcode.utils.DirectLedGroup;
import us.shsrobotics.ledcode.utils.ILedGroup;
import us.shsrobotics.ledcode.utils.LedGroup;

class Program {

    static NetworkTable ledTable;
    static NetworkTableEntry currentScriptEntry;
    static NetworkTableEntry scriptArgEntry;
    static String lastScriptName = "";
    static String lastScriptArg = "";
    static LedScript script = null;
    static LedStrip _Lights;

    private static int[] ConcatIntStreams(IntStream... arrays) {
        IntStream stream = arrays[0];

        for (int i = 1; i < arrays.length; i++) {
            stream = IntStream.concat(stream, arrays[i]);
        }

        return stream.toArray();
    }
    
    private static IntStream GetRange(int start, int end) {
        return GetRange(start, end, false);
    }

    private static IntStream GetRange(int start, int end, boolean reversed) {
        IntStream range = IntStream.range(start, end);

        if (reversed) {
            range = range.map(i -> start + (end - 1 - i));
        }

        return range;
    }

    public static LedScript getLedScript(String name, String argument) {
        switch (name.toLowerCase()) {
            // Because to .toLowerCase the name, all of the following
            // MUST be in lowercase for them to ever be picked.
            case "colorwaves":
                return new ColorWaves(argument);
            case "binary":
                return new BinaryScript(argument);
            case "fillin":
                return new FillInScript();
            case "teamnumber":
                return new TeamNumberScript();
            case "rsl":
                return new RSLScript();
            case "assign":
                return new AssignmentScript(argument);
            case "superrandomscript":
                return new SuperRandomScript();
            case "morsecode":
                return new MorseCode(argument);
            case "randomchristmas":
                return new RandomChristmasScript();
            case "snow":
                return new SnowScript();
            case "christmascombo1":
                {
                    LedScript snowScript1 = new SnowScript();
                    snowScript1.Setup(new LedGroup(_Lights,  ConcatIntStreams(
                        GetRange(17, 32, true),
                        GetRange(34, 58))));
                    
                    LedScript snowScript2 = new SnowScript();
                    snowScript2.Setup(new LedGroup(_Lights,  ConcatIntStreams(
                        GetRange(118, 133),
                        GetRange(92, 116, true))));
                    
                    LedScript christmasScript = new ChristmasColorWaves();
                    
                    christmasScript.Setup(new LedGroup(_Lights, ConcatIntStreams(
                        GetRange(0, 17),
                        GetRange(32, 34),
                        GetRange(58, 92),
                        GetRange(116, 118),
                        GetRange(133, 150))));
                    
                    return new LedGroupScript(snowScript1, snowScript2, christmasScript);
                }
            case "christmascolorwaves":
                return new ChristmasColorWaves();
            case "fire":
                return new FireScript();
            case "wholemulticolor":
                return new WholeMultiColorScript();
            case "clear":
                return new ClearScript();
            case "numbered":
                return new NumberedScript(argument);
            case "multicolor":
            default:
                // If nothing is specified, just return the MultiColorScript
                return new MultiColorScript();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");

        _Lights = new Ws281xLedStrip(150, 18, 800000, 10, 255, 0, false, 
            LedStripType.WS2811_STRIP_GRB, true);

        ILedGroup ledGroup = new DirectLedGroup(_Lights);

        NetworkTableInstance nt = NetworkTableInstance.getDefault();

        nt.startClientTeam(5724);
        
        ledTable = nt.getTable("LedInfo");//.getEntry("StationNum")
        currentScriptEntry = ledTable.getEntry("CurrentScript");
        scriptArgEntry = ledTable.getEntry("ScriptArgument");

        while (true) {
            String scriptName;
            String scriptArg;

            if (!nt.isConnected()) {
                scriptName = "FillIn";
                scriptArg = "";
            } else {
                scriptName = currentScriptEntry.getString("");
                scriptArg = scriptArgEntry.getString("");
            }

            if (!scriptName.equalsIgnoreCase(lastScriptName) ||
                    !scriptArg.equalsIgnoreCase(lastScriptArg) || script == null) {
                if (script != null) {
                    script.Cleanup();
                }

                lastScriptName = scriptName;
                lastScriptArg = scriptArg;

                try {
                    script = getLedScript(lastScriptName, scriptArg);
                    script.Setup(ledGroup);
                } catch(Exception e) {
                    e.printStackTrace();
                    Thread.sleep(200);
                    continue;
                }
            }


            script.Update();
            _Lights.render();
            // Minimum Thread.sleep of 1 millisecond because if _Lights.render
            // gets called more often then that, it can create flickering issues.
            Thread.sleep(Math.max(1, script.GetDelay()));
        }
    }
}