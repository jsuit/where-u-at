package gitmad.app.WhereUAt;

/**
 * A simple class for representing a 3 value vector, used for accelerometer data processing.
 * 
 * NOTE: all operations create a new ThreeAxisValue object, and will not change the value of "this".
 * They are, therefore, safe to chain together, e.g. 
 *         gravity = gravity.scale(alpha).add(gravity.scale(1 - alpha));
 *
 * @author Jesse Rosalia
 *
 */
class ThreeAxisValue implements Cloneable {
    float x = 0;
    float y = 0;
    float z = 0;
    
    public ThreeAxisValue() {
    }
    
    public ThreeAxisValue(float[] values) {
        x = values[0];
        y = values[1];
        z = values[2];
    }

    @Override
    protected ThreeAxisValue clone() {
        ThreeAxisValue clone = new ThreeAxisValue();
        clone.x = this.x;
        clone.y = this.y;
        clone.z = this.z;
        return clone;
    }
    
    public ThreeAxisValue add(ThreeAxisValue value) {
        ThreeAxisValue newVal = new ThreeAxisValue();
        newVal.x = this.x + value.x;
        newVal.y = this.y + value.y;
        newVal.z = this.z + value.z;
        return newVal;
        
    }

    public ThreeAxisValue subtract(ThreeAxisValue value) {
        ThreeAxisValue newVal = new ThreeAxisValue();
        newVal.x = this.x - value.x;
        newVal.y = this.y - value.y;
        newVal.z = this.z - value.z;
        return newVal;
    }

    public ThreeAxisValue element_mult(ThreeAxisValue value) {
        ThreeAxisValue newVal = new ThreeAxisValue();
        newVal.x = this.x * value.x;
        newVal.y = this.y * value.y;
        newVal.z = this.z * value.z;
        return newVal;
    }

    public ThreeAxisValue element_div(ThreeAxisValue value) {
        ThreeAxisValue newVal = new ThreeAxisValue();
        newVal.x = this.x / value.x;
        newVal.y = this.y / value.y;
        newVal.z = this.z / value.z;
        return newVal;
    }
    
    public ThreeAxisValue scale(float scalar) {
        ThreeAxisValue newVal = new ThreeAxisValue();
        newVal.x = scalar * this.x;
        newVal.y = scalar * this.y;
        newVal.z = scalar * this.z;
        return newVal;
    }
}