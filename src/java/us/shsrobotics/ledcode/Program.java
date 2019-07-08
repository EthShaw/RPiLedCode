package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.LedStrip;
import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

class Program {

    static NetworkTable ledTable;
    static NetworkTableEntry currentScriptEntry;
    static NetworkTableEntry scriptArgEntry;
    static String lastScriptName = "";
    static String lastScriptArg = "";
    static LedScript script = null;

    public static LedScript getLedScript(String name, String argument) {
        switch (name.toLowerCase()) {
            // Because to .toLowerCase the name, all of the following
            // MUST be in lowercase for them to ever be picked.
            case "colorwaves":
                return new ColorWaves();
            case "binary":
                return new BinaryScript(argument);
            case "superrandomscript":
                return new SuperRandomScript();
            case "morsecode":
                return new MorseCode(argument);
            case "christmascolorwaves":
                return new ChristmasColorWaves();
            case "fire":
                return new FireScript();
            case "wholemulticolor":
                return new WholeMultiColorScript();
            case "multicolor":
            default:
                // If nothing is specified, just return the MultiColorScript
                return new MultiColorScript();
        }
    }

    public static void main(String[] args) throws Exception {
        System.out.println("Starting...");

        LedStrip strip = new Ws281xLedStrip(150, 18, 800000, 10, 255, 0, false, 
            LedStripType.WS2811_STRIP_GRB, true);

        NetworkTableInstance nt = NetworkTableInstance.getDefault();

        nt.startClientTeam(5724);
        
        ledTable = nt.getTable("LedInfo");//.getEntry("StationNum")
        currentScriptEntry = ledTable.getEntry("CurrentScript");
        scriptArgEntry = ledTable.getEntry("ScriptArgument");

        while (true) {
            String scriptName;
            String scriptArg;

            if (!nt.isConnected()) {
                scriptName = "ColorWaves";
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

                script = getLedScript(lastScriptName, scriptArg);
                script.Setup(strip);
            }


            script.Update();
            strip.render();
            Thread.sleep(script.GetDelay());
        }
    }
}