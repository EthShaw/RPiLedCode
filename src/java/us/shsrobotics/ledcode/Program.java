package us.shsrobotics.ledcode;

import com.github.mbelling.ws281x.LedStrip;
import com.github.mbelling.ws281x.LedStripType;
import com.github.mbelling.ws281x.Ws281xLedStrip;

//import edu.wpi.first.networktables.NetworkTableInstance;

class Program {
    public static void main(String[] args) throws Exception {
        //NetworkTableInstance.getDefault();

        LedStrip strip = new Ws281xLedStrip(150, 21, 800000, 10, 255, 0, false, 
            LedStripType.WS2811_STRIP_GRB, false);
        
        LedScript script = new ColorWaves();
        BinaryScript script2 = new BinaryScript();
        SuperRandomScript script3 = new SuperRandomScript();
        script.Setup(strip);
        script2.Setup(strip);
        script3.Setup(strip);

        while (true) {
            script.Update();
            strip.render();
            Thread.sleep(script.GetDelay());
            
            /*script2.Update();
            strip.render();
            Thread.sleep(script2.GetDelay());
            
            script3.Update();
            strip.render();
            Thread.sleep(script3.GetDelay());*/
        }
    }
}