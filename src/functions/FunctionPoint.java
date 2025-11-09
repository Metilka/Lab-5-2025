package functions;

import java.io.Serializable;

public class FunctionPoint implements Serializable, Cloneable { // делаем точку клонируемой
    private static final long serialVersionUID = 1L;

    private double x;
    private double y;

    public FunctionPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public FunctionPoint(FunctionPoint other) {
        this(other.x, other.y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() { // человекочитаемый вывод
        // Требуемый формат: (x; y)
        return "(" + x + "; " + y + ")";
    }

    @Override
    public boolean equals(Object o) { // сравниваем точки по координатно
        if (this == o) return true;
        if (o == null || o.getClass() != FunctionPoint.class) return false;
        FunctionPoint that = (FunctionPoint) o;
        // сравнение чисел типа double
        return Double.compare(this.x, that.x) == 0 &&
                Double.compare(this.y, that.y) == 0;
    }

    @Override
    public int hashCode() { // хэш из битов double через XOR
        // XOR из битов double -> long -> два int → XOR
        long xb = Double.doubleToLongBits(x);
        long yb = Double.doubleToLongBits(y);
        int xLow = (int) (xb);
        int xHigh = (int) (xb >>> 32);
        int yLow = (int) (yb);
        int yHigh = (int) (yb >>> 32);
        return xLow ^ xHigh ^ yLow ^ yHigh;
    }

    @Override   // функция клонирования точки
    public FunctionPoint clone() { return new FunctionPoint(this.x, this.y);}
}
