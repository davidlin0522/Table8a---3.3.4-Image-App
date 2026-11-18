public class Matrix2by2 {
    private double a1;
    private double b1;
    private double a2;
    private double b2;

    private double[][] field;
    //Set each individual matrix value
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
    //Set with angle rotation
    public Matrix2by2(double Angle) {
        this.setRotationMatrix(Angle);
    }
    //Set using 2D array
    public Matrix2by2(double[][] field) { //Alternative code
        
        this.a1 = field[0][0];
        this.a2 = field[1][0];
        this.b1 = field[0][1];
        this.b2 = field[1][1];
        this.field = field;

    }
    
    public double[][] getField() {
        return this.field;
    }

    public static Vector1by2 multiply(Vector1by2 v, Matrix2by2 m) {
        double newA = v.getA() * m.a1 + v.getB() * m.a2; 
        double newB = v.getA() * m.b1 + v.getB() * m.b2;
        // System.out.println(newA);
        // System.out.println(newB);
        return new Vector1by2(newA, newB);
    }

    public static Matrix2by2 multiply(Matrix2by2 m1, Matrix2by2 m2) {
        double newA1 = m1.a1 * m2.a1 + m1.b1 * m2.a2; // Top-Left
        double newB1 = m1.a1 * m2.b1 + m1.b1 * m2.b2; // Top-Right
        double newA2 = m1.a2 * m2.a1 + m1.b2 * m2.a2; // Bottom-Left
        double newB2 = m1.a2 * m2.b1 + m1.b2 * m2.b2; // Bottom-Right

        return new Matrix2by2(newA1, newB1, newA2, newB2);
    }

    public void setRotationMatrix(double angle) {
    double radians = Math.toRadians(angle);
    double cos = Math.cos(radians);
    double sin = Math.sin(radians);

    this.a1 = cos;
    this.b1 = sin;
    this.a2 = -sin;
    this.b2 = cos;

    this.field = new double[][] {
        {this.a1, this.b1},
        {this.a2, this.b2}
    };
    }



}
