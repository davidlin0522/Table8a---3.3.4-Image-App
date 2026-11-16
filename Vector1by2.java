public class Vector1by2 {
    private double a;
    private double b;
    public Vector1by2(){
        this.a = 0.0;
        this.b = 0.0;
    }

    public Vector1by2(double a, double b) {
        this.a = a;
        this.b = b;
    }

    public static double dot(Vector1by2 v1, Vector1by2 v2) {
        return v1.a * v2.a + v2.b + v2.b; 
       }

    public static Vector1by2 add(Vector1by2 v1, Vector1by2 v2) {

        return new Vector1by2(v1.a + v2.a, v1.b + v2.b);
    }

    public double getA(){
        return this.a;
    }
    
    public double getB(){
        return this.b;
    }

}
