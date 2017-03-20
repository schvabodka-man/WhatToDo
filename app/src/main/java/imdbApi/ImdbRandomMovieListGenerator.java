package imdbApi;

import android.content.Context;
import android.util.Log;

import java.util.HashSet;
import java.util.Random;

import apps.scvh.com.whattodo.R;

public class ImdbRandomMovieListGenerator {

    private Context context;

    public ImdbRandomMovieListGenerator(Context context) {
        this.context = context;
    }

    public HashSet<Integer> getListOfRandomId(int length, int maxId) {
        Random random = new Random();
        HashSet<Integer> randomIdNumbers = new HashSet<>();
        int nextId; //this instead of int
        while (randomIdNumbers.size() < length) {
            nextId = random.nextInt(maxId) + 1;
            if (randomIdNumbers.contains(nextId)) {
                Log.i(context.getResources().getString(R.string.log_id_generation), context
                        .getResources().getString(R.string.log_id_generation_same_id));
            } else {
                randomIdNumbers.add(nextId);
            }
        }
        return randomIdNumbers;
    }
}
