package com.stuin.tenseconds;

import android.content.Context;
import android.view.View;
import com.stuin.tenseconds.Game.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Stuart on 3/14/2017.
 */
public class Round {
	//Technical variables
    public static boolean moving;
    public static ArrayList<Cell> cells;
	public static int length;
	public static int scale;

	//Difficulty level
    public static byte size;
    public static byte colors;
    public static boolean next;
    public static byte count;

    //Other game notes
    public static int games;
    public static boolean loss = false;
    public static byte tutorial;

    public static void generate() {
		//reset variables
        scale = length / (size + 1);
        cells = new ArrayList<>();
        Random rand = new Random();

		//Create squares
        for(byte y = 0; y < size; y++) for(byte x = 0; x < size; x++) {
            cells.add(new Cell((byte) rand.nextInt(colors), x, y));
        }
		
		//Set changed square
		byte pos = (byte) rand.nextInt(cells.size());
        Cell marked = cells.get(pos);
        marked.mark = (byte) rand.nextInt(colors - 1);
        if(marked.mark == marked.color) 
			marked.mark = (byte) (colors - 1);
    }

    public static void reset() {
		//Set to first level
        count = 0;
        size = 5;
        colors = 3;
        moving = false;
        next = false;

        //Set details
        games++;
        tutorial = 0;
    }
	
	public static void next() {
		//Increment round settings
		if(!Round.next) Round.next = true;
		else {
			if(Round.size != 9) {
				//next size
				Round.size++;
				//Round.next = false;
			} else {
				//next color
				Round.colors++;
				Round.size = 5;
				Round.next = false;
			}
		}
	}

    public static String separate(int score) {
		//add commas to number
        String text = "" + score;
        for(int i = 3; i < text.length(); i += 4)
            text = text.substring(0, text.length() -  i) + ',' + text.substring(text.length() - i);
        return text;
    }

    public static void visible(View view, boolean bool) {
        //Easy boolean visibility
        if(bool) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }
}
