package us.shsrobotics.ledcode;

import java.util.ArrayList;
import java.util.List;

import com.github.mbelling.ws281x.Color;

import us.shsrobotics.ledcode.utils.ILedGroup;

public class RSLScript extends LedScript {
    private ILedGroup _Lights;
    private boolean _State = true;

    @Override
    public void Setup(ILedGroup strip) {
        SetDelay(250);

        _Lights = strip;
    }

    @Override
    public void Update() {
        if (_State) {
            _Lights.setStrip(Color.ORANGE);
        } else {
            _Lights.setStrip(Color.BLACK);
        }

        _State = !_State;
    }
}
