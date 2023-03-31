import se.lth.control.realtime.AnalogIn;
import se.lth.control.realtime.AnalogOut;
import se.lth.control.realtime.IOChannelException;

public class Regul extends Thread {
    
    private PID controller = new PID();
    private ReferenceGenerator refGen;
    private OpCom opCom;

    private AnalogIn analogInAngle;
    private AnalogIn analogInPosition;
    private AnalogOut analogOut;

    private int priority;
    private boolean shouldRun = true;
    private long startTime;

    private ModeMonitor modeMon;

    private double yRef = 0;
    private double y = 0;
    private double u = 0.0;


    public Regul(int pri, ModeMonitor modeMon) {
        priority = pri;
        setPriority(priority);
        this.modeMon = modeMon;

        try {
            // In order to initialize or interact with the 
            // analog interface, a try catch is needed.
            analogInAngle=new AnalogIn(0);
            analogInPosition=new AnalogIn(1);
            analogOut = new AnalogOut(0);

        } catch (Exception e) {
            System.out.print("Error: IOChannelException: ");
            System.out.println(e.getMessage());
        }
    }

        /** Sets OpCom (called from main) */
    public void setOpCom(OpCom opCom) {
        this.opCom = opCom;
    }

     /** Sets ReferenceGenerator (called from main) */
    public void setRefGen(ReferenceGenerator refGen) {
        this.refGen = refGen;
    }

    // Called in every sample in order to send plot data to OpCom
    private void sendDataToOpCom(double yRef, double y, double u) {
        double x = (double) (System.currentTimeMillis() - startTime) / 1000.0;
        opCom.putControlData(x, u);
        opCom.putMeasurementData(x, yRef, y);
    }

    // Sets the  controller's parameters
    public void setParameters(PIDParameters p) {
        
        controller.setParameters(p);
    }

    // Gets the controller's parameters
    public PIDParameters getParameters(){
        
        return controller.getParameters();
    }

     // Called from OpCom when shutting down
    public void shutDown() {
        shouldRun = false;
    }

    // Saturation function
    private double limit(double v) {
        return limit(v, -10, 10);
    }


//  Private method for implementing the relay with hysteresis
private class Relay {

        private double reference; 
        private double hysteresis;    
        private double control;
        private boolean relayState;

        public HysteresisController(double reference, double hysteresis) {
            this.reference = reference;
            this.hysteresis = hysteresis;
        }

        public boolean control(double input) {
            if (input > reference + hysteresis && !relayState) {
                relayState = true;
            } else if (input < reference - hysteresis && relayState) {
                relayState = false;
            }
            return relayState; // To indicate on or off signal
        }

    }
}