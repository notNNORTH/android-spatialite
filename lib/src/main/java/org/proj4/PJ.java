package org.proj4;



public class PJ {
    public static final int DIMENSION_MAX = 100;


    private final long ptr;
    

    public static native void setDataPath(final String dataPath);


    public static native void setSearchPaths(final String[] searchPaths);
    

  	public static native GridInfo readGridInfo(String gridName) throws PJException;
  	

  	public static native int getGlobalLastErrorCode();


  	public static native String getGlobalLastErrorString();
  	

  	public static native void clearGlobalLastError();


    public PJ(final String definition) throws IllegalArgumentException {
        ptr = allocatePJ(definition);
        if (ptr == 0) {
            throw new IllegalArgumentException(definition);
        }
    }


    public PJ(final PJ crs, final Type type) throws IllegalArgumentException {
        if (crs == null) {
            // TODO: Use Objects with JDK 7.
            throw new NullPointerException("The CRS must be non-null.");
        }
        if (type != Type.GEOGRAPHIC) {
            throw new IllegalArgumentException("Can not derive the " + type + " type.");
        }
        ptr = allocateGeoPJ(crs);
        if (ptr == 0) {
            throw new IllegalArgumentException(crs.getLastError());
        }
    }
    

    private static native long allocatePJ(String definition);


    private static native long allocateGeoPJ(PJ projected);


    public static native String getVersion();


    public native String getDefinition();


    public native Type getType();


    public static enum Type {

        GEOGRAPHIC,


        GEOCENTRIC,


        PROJECTED
    }


    public native double getSemiMajorAxis();


    public native double getSemiMinorAxis();


    public native double getEccentricitySquared();


    public native char[] getAxisDirections();


    public native double getGreenwichLongitude();


    public native double getLinearUnitToMetre(boolean vertical);


    public native void transform(PJ target, int dimension, double[] coordinates, int offset, int numPts)
            throws PJException;


    public native String getLastError();


    @Override
    public native String toString();


    @Override
    protected final native void finalize();
}
