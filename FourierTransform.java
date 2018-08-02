package fouriertransform;

public class FourierTransform {

    public static void main(String[] args) {
        FourierTransform obj = new FourierTransform();
        obj.start();
    }
    private void start() {
        OpenFile graphics = new OpenFile("FourierTransform");
        graphics.create();
    }
}
