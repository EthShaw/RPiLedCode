package us.shsrobotics.ledcode;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class LedGroupScript extends LedScript
{
    private LedScript[] scripts;
    private ScriptTrigger[] scriptTracking;

    public LedGroupScript(LedScript... ledScripts) {
        scripts = new LedScript[ledScripts.length];

        for (int i = 0; i < ledScripts.length; i++) {
            scripts[i] = ledScripts[i];
        }
    }

    @Override
    public void Setup(ILedGroup parent) {
        SetDelay(0);

        scriptTracking = new ScriptTrigger[scripts.length];

        for (int i = 0; i < scripts.length; i++) {
            scriptTracking[i] = new ScriptTrigger(scripts[i]);
        }
    }

    @Override
    public void Update() {
        int max_delay = 200;

        for (ScriptTrigger trigger : scriptTracking) {
            long delay = System.currentTimeMillis() - trigger.lastRun;
            
            if (delay < max_delay) {
                max_delay = (int)delay;
            }

            if (delay >= trigger.script.GetDelay()) {
                trigger.script.Update();
                trigger.lastRun = System.currentTimeMillis();
            }
        }

        if (max_delay < 0) {
            max_delay = 0;
        }
        
        SetDelay(max_delay);
    }

    @Override
    public void Cleanup() {
        for (LedScript script : scripts) {
            script.Cleanup();
        }
    }

    private static class ScriptTrigger {
        public LedScript script;
        public long lastRun;

        public ScriptTrigger(LedScript scriptIn) {
            script = scriptIn;
            lastRun = System.currentTimeMillis();
        }
    }
}