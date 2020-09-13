import java.util.*;
import java.math.*;


public class Main {

	
	
	static int phaseDif;
	
	public static void main(String args[]) {
		Scanner input = new Scanner(System.in);
		double heatEnergy = 0;
		
		
		System.out.println("Enter the mass in grams.");
		double mass = input.nextDouble();
		System.out.println("Enter the staring temperature in C degrees.");
		double iTemp = minTemp(input.nextDouble()); // i for initial
		System.out.println("Enter the ending temperature in C degrees.");
		double fTemp = minTemp(input.nextDouble()); // f for final
		
		System.out.println("Mass: "+ mass + "g"); // prints out mass
		
		
		int iPhase = phaseDetection(iTemp);
		int fPhase = phaseDetection(fTemp);
		phaseDif = fPhase - iPhase;
		
		// Printing out starting temperature and ending temperature along with current phase.
		
		if(iPhase == 1) {
			System.out.println("Starting Temperature: "+ iTemp + "C (water ice)");
		}else if(iPhase == 3) {
			System.out.println("Starting Temperature: "+ iTemp + "C (water vapor)");
		}else {
			System.out.println("Starting Temperature: "+ iTemp + "C (liquid water)");
		}
		
		if(fPhase == 1) {
			System.out.println("Ending Temperature: "+ fTemp + "C (water ice)");
		}else if(fPhase == 3) {
			System.out.println("Ending Temperature: "+ fTemp + "C (water vapor)");
		}else {
			System.out.println("Ending Temperature: "+ fTemp + "C (liquid water)");
		}
		
		
		if (phaseDif == 0) {//No phase changes :D
			if(iPhase == 3) {
				heatEnergy += gasHeat(iTemp,fTemp, mass);
			}else if(iPhase == 2) {
				heatEnergy += liquidHeat(iTemp,fTemp,mass);
			}else {
				heatEnergy += solidHeat(iTemp, fTemp, mass);
			}
		}else if(phaseDif < 0) { //When initial temperature is greater than final temperature, so temperature is going down
			
			if(Math.abs(phaseDif) > 1) {
				//condensing and freezing
				heatEnergy += gasHeat(iTemp,100, mass);
				heatEnergy += condensing(mass);
				heatEnergy += liquidHeat(100,0,mass);
				heatEnergy += freezing(mass);
				heatEnergy += solidHeat(0,fTemp,mass);
			}else if(iPhase > 2) {
				//initial phase is gas, final phase is liquid, condensing
				heatEnergy += gasHeat(iTemp,100, mass);
				heatEnergy += condensing(mass);
				heatEnergy += liquidHeat(100,fTemp,mass);
			}else {
				//initial phase is liquid, final phase is solid, freezing
				heatEnergy += liquidHeat(iTemp, 100,mass);
				heatEnergy += freezing(mass);
				heatEnergy += solidHeat(0, fTemp, mass);
			}
			
		}else {//When initial temperature is less than final temperature, so temperature is going down
			if(Math.abs(phaseDif) > 1) {
				//melting and evaporating
				heatEnergy += solidHeat(iTemp,0,mass);
				heatEnergy += melting(mass);
				heatEnergy += liquidHeat(0,100,mass);
				heatEnergy += boiling(mass);
				heatEnergy += gasHeat(100, fTemp, mass);
			}else if(fPhase > 2) {
				//evaporating
				heatEnergy += liquidHeat(iTemp,100,mass);
				heatEnergy += boiling(mass);
				heatEnergy += gasHeat(100,fTemp, mass);
			}else{
				//melting
				heatEnergy += solidHeat(iTemp, 0, mass);
				heatEnergy += melting(mass);
				heatEnergy += liquidHeat(0, fTemp,mass);	
			}
		}
		
		heatEnergy = roundThreePlaces(heatEnergy);
		
		System.out.println("Total Heat Energy:"+ heatEnergy +" kJ");

	}
	

	
	public static double minTemp(double temp) {
		if(temp < -243.14) {
			return -243.14;
		} else {
			return temp;
		}
	}
	
	public static int phaseDetection(double temp) {
		if(temp >= 100) {
			return 3;
		}else if(temp >= 0) {
			return 2;
		}else{
			return 1;
		}
	}
	public static double melting(double mass)
	{
		double heat = roundThreePlaces(mass*0.33);
		System.out.println("Phase Change (Freezing): "+ heat + " kJ" );
		return heat;
		
	}
	
	public static double freezing(double mass){
		double heat = roundThreePlaces(mass*-0.333);
		System.out.println("Phase Change (Melting): "+ heat + " kJ" );
		return heat;		
	}
	
	public static double condensing(double mass) {
		double heat = roundThreePlaces(mass*-2.257);
		System.out.println("Phase Change (Condensing): "+ heat + " kJ" );
		return heat;
	}
	
	public static double boiling(double mass) {
		double heat = roundThreePlaces(mass*2.257);
		System.out.println("Phase Change (Boiling): "+ heat + " kJ" );
		return heat;
	}
	
	public static double solidHeat(double initTemp, double finTemp, double mass) {
		double heat = roundThreePlaces((0.002108)*mass*(finTemp - initTemp));
		if(heat > 0) {
			System.out.println("Heating(Solid):" + heat+" kJ");
		} else {
			System.out.println("Cooling(Solid):" + heat+" kJ");
		}
		
		return heat;
	}
	
	public static double liquidHeat(double initTemp, double finTemp, double mass) {
		double heat = roundThreePlaces((0.004184)*mass*(finTemp - initTemp));
		if(heat > 0) {
			System.out.println("Heating(Liquid):" + heat+" kJ");
		} else {
			System.out.println("Cooling(Liquid):" + heat+" kJ");
		}
		return heat;
	}

	public static double gasHeat(double initTemp, double finTemp, double mass) {
		double heat = roundThreePlaces((0.001996)*mass*(finTemp - initTemp));
		if(heat > 0) {
			System.out.println("Heating(Gas):" + heat+" kJ");
		} else {
			System.out.println("Cooling(Gas):" + heat+" kJ");
		}
		return heat;
	}
	public static double roundThreePlaces(double x) {
		x *= 1000;
		
		if(x>0) {
			return (int)(x+0.5)/1000.0;
		}else {
			return (int)(x-0.5)/1000.0;
		}
		
	}
}

