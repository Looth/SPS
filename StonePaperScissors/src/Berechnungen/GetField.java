package Berechnungen;

import java.awt.Dimension;
import java.awt.Point;

public class GetField {

	
	public static int getField(Dimension dim,Point p){
		
		int a =  dim.width/7;


		int c =(int)  (p.y + a) /a;
		int d =(int)  (p.x + a) /a;

		return 7*(c-1) +d;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
