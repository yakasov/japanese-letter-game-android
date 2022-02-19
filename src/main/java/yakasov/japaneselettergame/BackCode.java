package yakasov.japaneselettergame;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.AssetManager;

import org.json.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Random;
import java.util.Set;

public class BackCode {
    private final Context context;

    private static String[] allRows = {"a-row", "ka-row", "sa-row", "ta-row", "na-row", "ma-row",
                                      "ya-row", "ra-row", "wa-row", "ga-row", "za-row", "da-row",
                                      "ba-row", "pa-row"};
    private static ArrayList<String> chosenRows;
    private static String randomRow;

    private static String correctEnglishCharacter = "";
    private static String correctJapaneseCharacter = "";

    public BackCode(Context context) {
        this.context = context;
    }

    public org.json.simple.JSONObject loadJson(String path) {
        AssetManager am = context.getAssets();
        org.json.simple.JSONObject jsonObject = new org.json.simple.JSONObject();

        try {
            InputStream is = am.open(path);

            JSONParser jsonParser = new JSONParser();
            jsonObject = (org.json.simple.JSONObject)jsonParser.parse(
                    new InputStreamReader(is, "UTF-8"));
        } catch (IOException | ParseException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }

    public static String compareRows(SharedPreferences prefs) {
        chosenRows = new ArrayList<>();
        chosenRows.add("a-row");
        Set<String> gojuon = prefs.getStringSet("hiragana_gojuon_list", null);
        Set<String> dakuon = prefs.getStringSet("hiragana_dakuon_list", null);
        for (String row : allRows) {
            if (gojuon.contains(row) || dakuon.contains(row)) {
                chosenRows.add(row);
            }
        }
        return String.valueOf(chosenRows);
    }

    public static String returnCorrectEnglishCharacter() { return correctEnglishCharacter; }

    public static String returnCorrectJapaneseCharacter() { return correctEnglishCharacter; }

    public static void getCorrectCharacterObj(org.json.simple.JSONObject allCharacters) {
        Random rand = new Random();
        randomRow = chosenRows.get(rand.nextInt(chosenRows.size()));  // String type
        org.json.simple.JSONArray correctRowArray = (org.json.simple.JSONArray) allCharacters.get(randomRow);
        org.json.simple.JSONObject correctCharacterObject = (org.json.simple.JSONObject) correctRowArray.get(rand.nextInt(correctRowArray.size()));

        correctEnglishCharacter = (String) correctCharacterObject.keySet().iterator().next();
        correctJapaneseCharacter = (String) correctCharacterObject.get(correctEnglishCharacter);
    }

    public static String getRandomEnglishCharacter(org.json.simple.JSONObject allCharacters) {
        Random rand = new Random();
        String randomEnglishCharacter = correctEnglishCharacter;
        while (randomEnglishCharacter == correctEnglishCharacter) {
            randomRow = chosenRows.get(rand.nextInt(chosenRows.size()));  // String type
            org.json.simple.JSONArray randomRowArray = (org.json.simple.JSONArray) allCharacters.get(randomRow);
            org.json.simple.JSONObject randomCharacterObject = (org.json.simple.JSONObject) randomRowArray.get(rand.nextInt(randomRowArray.size()));
            randomEnglishCharacter = (String) randomCharacterObject.keySet().iterator().next();
        }
        return randomEnglishCharacter;
    }
}
