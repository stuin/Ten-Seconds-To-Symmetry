package com.stuin.tenseconds;

import android.content.Context;
import com.stuin.tenseconds.Views.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Stuart on 3/14/2017.
 */
public class Round {
    public static boolean moving;
    public static boolean next;
    public static int size;
	public static int colors;
    public static int scale;
	public static int pos;
    public static List<Cell> cells;
    static int length;

    static void generate(Context context) {
        scale = length / (size + 2);

        cells = new ArrayList<>();
        Random rand = new Random();

        for(int y = 0; y < size; y++) for(int x = 0; x < size; x++) {
            cells.add(new Cell(context, rand.nextInt(colors), x, y));
        }
		
		pos = rand.nextInt(cells.size());
        Cell marked = cells.get(pos);
        marked.mark = rand.nextInt(colors - 1);
        //if(marked.mark == marked.color) marked.mark = colors - 1;
    }

    static void reset() {
        Round.size = 5;
        Round.colors = 3;
        Round.moving = false;
        Round.next = false;
    }
}
