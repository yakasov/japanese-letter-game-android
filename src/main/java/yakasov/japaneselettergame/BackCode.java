package yakasov.japaneselettergame;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BackCode {
    private final Context context;

    public BackCode(Context context) {
        this.context = context;
    }

    public List<String> readLine(String path) {
        List<String> lines = new ArrayList<>();

        AssetManager am = context.getAssets();

        try {
            InputStream is = am.open(path);
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String line;

            while ((line = reader.readLine()) != null)
                lines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    public int getRandomIndex(int max) {
        Random random = new Random();
        return random.nextInt(max);
    }

    public int getRandomIndexExclusionary(int max, ArrayList<Integer> chosenIndexes) {
        int chosenValue;
        boolean pickedAlready;
        while (true) {
            Random random = new Random();
            chosenValue = random.nextInt(max);

            pickedAlready = false;
            for (int i : chosenIndexes) {
                if (chosenValue == i) {
                    pickedAlready = true;
                }
            }
            if (!pickedAlready) {
                return chosenValue;
            }
        }
    }
}
