package com.stuin.tenseconds;
import com.stuin.tenseconds.Views.*;
import org.xml.sax.helpers.*;

public class Tutorial
{
	private int part = 0;
	private Timer timer;
	public boolean run = true;
	private String[] text = {
		"Just find the changed square,",
		"Each level is unique,",
		"They grow as you progress,",
		"So go fast for higher score,",
		"And keep an eye on the timer."};
	
	public Tutorial(Timer timer) {
		this.timer = timer;
	}
	
	public void Next() {
		if(part < text.length) {
			timer.Write(text[part]);
			part++;
			run = false;
		} else if(run) Settings.Set("Tutorial", false);
	}
}
