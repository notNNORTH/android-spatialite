package org.proj4;

public class GridInfo {
	
	public static enum Format 
	{
    CTABLE,
    CTABLE2,
    NTV1,
    NTV2, 
    GTX
	}
	
	public static class CTable
	{
		public String id;

		public double originLambda;
		public double originPhi;

		public double cellLambda;
		public double cellPhi;

		public int sizeLambda;
		public int sizePhi;
	}

	public String gridName;
	public String fileName;
	public Format format;
	public int gridOffset;
	public CTable ctable;
	
	public GridInfo next;
	public GridInfo child;
}
