package generator2;

import java.io.*;
import java.util.Properties;

import routing.Edge;

import drawables.DrawableObjects;

/**
 * Class for reporting the positions of moving objects into a file.
 *
 * @version 2.10	27.08.2003	additional parameters reported
 * @version 2.01	17.09.2001	repNum added to file
 * @version 2.00	03.09.2001	separated from the class "Reporter"
 * @author FH Oldenburg
 */
public class PositionReporter extends Reporter {

	/**
	 * The data output stream.
	 */
	protected DataOutputStream dOut = null;//二进制输出
	/**
	 * The print writer.
	 */
	protected PrintWriter pOut = null;//文本输出

/**
 * Constructor of the default reporter.
 * @param properties properties of the generator
 * @param objects container of drawable objects
 */
public PositionReporter (Properties properties, DrawableObjects objects) {
	super (properties,objects);
	String name = properties.getProperty("outputFile");
	if (name != null)
		try {
			if (name.endsWith(".mpf"))
				this.dOut = new DataOutputStream(new FileOutputStream(name));
			else
				this.pOut = new PrintWriter(new FileOutputStream(name));
		}
		catch (Exception ioe) {
			System.err.println("Error occured by creating the output file "+name);
		}
}

/**
 * Closes the reporter.
 */
public void close() {
	try {
		if (dOut != null)
			dOut.close();
		if (pOut != null)
			pOut.close();
	} catch (Exception ex) {
	}
}

/**
 * Prints the given point to the print writer.文本
 * @param  out  the print writer
 * @param  action  the action of the object
 * @param  id  object id
 * @param  repNum  report number
 * @param  objClass  object class
 * @param  time  time stamp
 * @param  x  current x-coordinate
 * @param  y  current y-coordinate
 * @param  speed  current speed
 * @param  doneDist  the distance since the last reporting
 * @param  nextNodeX  x-coordinate of the next node
 * @param  nextNodeY  y-coordinate of the next node
 */
protected static void print (PrintWriter out, String action, long id, int repNum, int objClass, int time, double x, double y, double speed, double doneDist, int nextNodeX, int nextNodeY) {
	if (out == null)
		return;
	out.print(action); out.print('\t');
	out.print(id); out.print('\t');
	out.print(repNum); out.print('\t');
	out.print(objClass); out.print('\t');
	out.print(time); out.print('\t');
	out.print(x); out.print('\t');
	out.print(y); out.print('\t');
	out.print(speed); out.print('\t');
	out.print(nextNodeX); out.print('\t');
	out.println(nextNodeY);
	//out.println("");
}
//printPointAndEdge(pOut,"point",id,newRepNum,objClass,actEdge.getID(),actEdge.getLength(),actEdge.getWeight(),relDist,time,x,y,speed,doneDist,nextNodeX,nextNodeY);
protected static void printPointAndEdge (PrintWriter out, String action, long id, int repNum, int objClass,long lastNode, long edgeid,double edgeLength,double edgeWeight,double relDist, int time, double x, double y, double speed, double doneDist, int nextNodeX, int nextNodeY) {
	if (out == null)
		return;
	out.print(action); out.print('\t');
	out.print(id); out.print('\t');
	out.print(repNum); out.print('\t');
	//out.print(objClass); out.print('\t');
	out.print(lastNode); out.print('\t');
	out.print(edgeid); out.print('\t');
	out.print(edgeLength); out.print('\t');
	out.print(edgeWeight); out.print('\t');
	out.print(relDist); out.print('\t');
	
	out.print(time); out.print('\t');
	//out.print(x); out.print('\t');
	//out.print(y); out.print('\t');
	//out.print(doneDist); out.print('\t');
	out.print(speed); out.print('\t');
	//out.print(nextNodeX); out.print('\t');
	//out.println(nextNodeY);
	out.println("");

}

protected static void print_e (PrintWriter out, String action, long id, int repNum, int objClass, int time, double x, double y, double speed, double doneDist, int nextNodeX, int nextNodeY) {
	if (out == null)
		return;
	out.print(action); out.print('\t');
	out.print(id); out.print('\t');
	out.print(repNum); out.print('\t');
	//out.print(objClass); out.print('\t');
	out.print(time); out.print('\t');
	out.print(x); out.print('\t');
	out.print(y); out.print('\t');
	out.print(speed); out.print('\t');
	//out.print(nextNodeX); out.print('\t');
	//out.println(nextNodeY);
	out.println("");
}

/**
 * Prints the given point to the DataOutputStream.二进制
 * @param  out  the DataOutputStream
 * @param  action  the action of the object
 * @param  id  object id
 * @param  repNum  report number
 * @param  objClass  object class
 * @param  time  time stamp
 * @param  x  current x-coordinate
 * @param  y  current y-coordinate
 * @param  speed  current speed
 * @param  doneDist  the distance since the last reporting
 * @param  nextNodeX  x-coordinate of the next node
 * @param  nextNodeY  y-coordinate of the next node
 */
protected static void print (DataOutputStream out, byte action, long id, int repNum, int objClass, int time, double x, double y, double speed, double doneDist, int nextNodeX, int nextNodeY) {
	if (out == null)
		return;
	try {
		out.writeByte(action);
		out.writeLong(id);
		out.writeInt(repNum);
		out.writeInt(objClass);
		out.writeInt(time);
		out.writeDouble(x); 
		out.writeDouble(y);
		out.writeDouble(speed);
		out.writeDouble(doneDist);
		out.writeInt(nextNodeX); 
		out.writeInt(nextNodeY);
	} catch (Exception ex) {
	}
}

/**
 * Reports the characteristic properties of a dispappering object at a time stamp
 * @param  time  the arrival time (with fraction)
 * @param  id  object id
 * @param  repNum  report number
 * @param  objClass  object class
 * @param  x  x-coordinate
 * @param  y  y-coordinate
 * @param  doneDist  the distance since the last reporting
 * @param  reportProbability  value between (0..1000)
 */
public void reportDisappearingObject (double time, long id, int repNum, int objClass, int x, int y, double doneDist, int reportProbability) {
	if (reportProbability > 0) {
		if (pOut != null)
			print(pOut,"disappearpoint",id,repNum,objClass,(int)Math.ceil(time),x,y,0.0,doneDist,x,y);
		if (dOut != null)
			print(dOut,DEL_OBJECT,id,repNum,objClass,(int)Math.ceil(time),x,y,0.0,doneDist,x,y);
	}
}
public void reportDisappearingObjectAndEdge (double time, long id, int repNum, int objClass,long lastNode,double relDist,Edge actEdge, int x, int y, double doneDist, int reportProbability) {
	if (reportProbability > 0) {
		if (pOut != null)
			printPointAndEdge(pOut,"disappearpoint",id,repNum,objClass,lastNode,actEdge.getID(),actEdge.getLength(),actEdge.getWeight(),relDist,(int)Math.ceil(time),x,y,0.0,doneDist,x,y);
		if (dOut != null)
			print(dOut,DEL_OBJECT,id,repNum,objClass,(int)Math.ceil(time),x,y,0.0,doneDist,x,y);
	}
}

/**
 * Reports the characteristic properties of a moving object at a time stamp
 * according to its report probability.
 * @return  new report number
 * @param  time  time stamp
 * @param  id  object id
 * @param  repNum  report number
 * @param  objClass  object class
 * @param  x  x-coordinate
 * @param  y  y-coordinate
 * @param  speed  current speed
 * @param  doneDist  the distance since the last reporting
 * @param  nextNodeX  x-coordinate of the next node
 * @param  nextNodeY  y-coordinate of the next node
 * @param  reportProbability  value between (0..1000)
 */
public int reportMovingObject (int time, long id, int repNum, int objClass, double x, double y, double speed, double doneDist, int nextNodeX, int nextNodeY, int reportProbability) {
	int newRepNum = super.reportMovingObject (time,id,repNum,objClass,x,y,speed,doneDist,nextNodeX,nextNodeY,reportProbability);
	if (repNum != newRepNum) {
		if (pOut != null)//文件输出
			print(pOut,"point",id,newRepNum,objClass,time,x,y,speed,doneDist,nextNodeX,nextNodeY); 
		if (dOut != null)
			print(dOut,MOVE_OBJECT,id,newRepNum,objClass,time,x,y,speed,doneDist,nextNodeX,nextNodeY);
	}
	return newRepNum;
}
 
public int reportMovingObjectAndEdge(int time, long id, int repNum, int objClass,long lastNode,double relDist,Edge actEdge, double x, double y, double speed, double doneDist, int nextNodeX, int nextNodeY, int reportProbability) {
	int newRepNum = super.reportMovingObject (time,id,repNum,objClass,x,y,speed,doneDist,nextNodeX,nextNodeY,reportProbability);
	if (repNum != newRepNum) {
		if (pOut != null)//文件输出
			//print(pOut,"point",id,newRepNum,objClass,time,x,y,speed,doneDist,nextNodeX,nextNodeY);
		printPointAndEdge(pOut,"point",id,newRepNum,objClass, lastNode,actEdge.getID(),actEdge.getLength(),actEdge.getWeight(),relDist,time,x,y,speed,doneDist,nextNodeX,nextNodeY);
		if (dOut != null)
			print(dOut,MOVE_OBJECT,id,newRepNum,objClass,time,x,y,speed,doneDist,nextNodeX,nextNodeY);
	}
	return newRepNum;
}

/**
 * Reports an new moving object if its report probability > 0.
 * @param  time  time stamp
 * @param  id  object id
 * @param  objClass  object class
 * @param  x  x-coordinate of start
 * @param  y  y-coordinate of start
 * @param  speed  current speed
 * @param  nextNodeX  x-coordinate of the next node
 * @param  nextNodeY  y-coordinate of the next node
 * @param  reportProbability  value between (0..1000)
 */
public int reportNewMovingObject (int time, long id, int objClass, int x, int y, double speed, int nextNodeX, int nextNodeY, int reportProbability) {
	int repNum = super.reportNewMovingObject(time,id,objClass,x,y,speed,nextNodeX,nextNodeY,reportProbability);
	if (repNum > 0) {
		if (pOut != null)
			print(pOut,"newpoint",id,repNum,objClass,time,x,y,speed,0.0,nextNodeX,nextNodeY);
		if (dOut != null)
			print(dOut,NEW_OBJECT,id,repNum,objClass,time,x,y,speed,0.0,nextNodeX,nextNodeY);
	}
	return repNum;
}

public int reportNewMovingObjectAndEdge (int time, long id, int objClass, Edge actEdge, int x, int y, double speed, int nextNodeX, int nextNodeY, int reportProbability) {
	int repNum = super.reportNewMovingObject(time,id,objClass,x,y,speed,nextNodeX,nextNodeY,reportProbability);
	if (repNum > 0) {
		if (pOut != null)
			printPointAndEdge(pOut,"newpoint",id,repNum,objClass,0,actEdge.getID(),actEdge.getLength(),actEdge.getWeight(),0.0,time,x,y,speed,0,nextNodeX,nextNodeY);
		
			//print(pOut,"newpoint",id,repNum,objClass,time,x,y,speed,0.0,nextNodeX,nextNodeY);
		if (dOut != null)
			print(dOut,NEW_OBJECT,id,repNum,objClass,time,x,y,speed,0.0,nextNodeX,nextNodeY);
	}
	return repNum;
}

}