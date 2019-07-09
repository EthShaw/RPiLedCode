package us.shsrobotics.ledcode;

import org.json.JSONException;
import org.json.JSONObject;

public class ArgumentHelper
{
    public static JSONObject parseArguments(String json) {
        try {
            return new JSONObject(json);
        } catch (JSONException e) {
            return null;
        }
    }
}