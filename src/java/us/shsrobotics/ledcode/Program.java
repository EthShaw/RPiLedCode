package us.shsrobotics.ledcode;

import java.lang.management.ManagementFactory;

import com.github.mbelling.ws281x.Color;
import com.github.mbelling.ws281x.LedStrip;
import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;
import java.util.Map;

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
        switch (name) {
            case "ColorWaves":
                return new ColorWaves();
            case "Binary":
                return new BinaryScript(argument);
            case "SuperRandomScript":
                return new SuperRandomScript();
            case "MorseCode":
                return new MorseCode(argument);
            case "MultiColor":
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
        currentScriptEntry = nt.getEntry("CurrentScript");
        scriptArgEntry = nt.getEntry("ScriptArgument");

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