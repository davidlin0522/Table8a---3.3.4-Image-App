public class Matrix2by2 {
    private double a1;
    private double b1;
    private double a2;
    private double b2;

    private double[][] field;

    public Matrix2by2(double a1, double b1, double a2, double b2) {
        this.a1 = a1;
        this.a2 = a2;
        this.b1 = b1;
        this.b2 = b2;
        this.field = new double[][] {
            {a1, b1},
            {a2, b2}
        };
    }
    
    public Matrix2by2() {
        this.field = new double[2][2]; 
    }

    //  Vector1by2 multiply(Vector1by2, Matrix2by2)
    // Matrix2by2 multiply(Matrix2by2, Matrix2by2)

    public static Vector1by2 multiply(Vector1by2 v, Matrix2by2 m) {
        double newA = v.getA() * m.a1 + v.getB() * m.a2; 
        double newB = v.getA() * m.b1 + v.getB() * m.b2;
       

        
        return new Vector1by2(newA, newB);
    }

}
