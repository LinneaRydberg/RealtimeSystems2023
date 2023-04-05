import se.lth.control.realtime.AnalogIn;
import se.lth.control.realtime.AnalogOut;

public class RegulStinaLinnea extends Thread {

    private PI pi = new PI();
    //private PID outer = new PID();
    //add later

    private ReferenceGenerator refGen;
    private GUI gui;
        
    private AnalogIn analogInAngle;
    private AnalogIn analogInPosition;
    private AnalogOut analogOut;

    private int priority;
    private boolean shouldRun = true;
    private long startTime;

    private ModeMonitor modeMon; //ERSÄTT MED NY MODE-MOTSVARIGHETEN FRÅN GUI 
    

   //tas från swing 
    //relay parametrar, beror på mode, tas från swing
    private double hyst;
    private double relayAmp;
    private double relayAmp;
    private double hyst; 

    //private double yRef = 0;
    //private double y = 0;
    //private double u = 0;

    public RegulStinaLinnea(int pri, ModeMonitor modeMon) {
        priority = pri;
        setPriority(priority);
        this.modeMon = modeMon;

        try {
            // In order to initialize or interact with the 
            // analog interface, a try catch is needed.
            analogInAngle = new AnalogIn(0);
            analogInPosition = new AnalogIn(1);
            analogOut = new AnalogOut(0);


        } catch (Exception e) {
            System.out.print("Error: IOChannelException: ");
            System.out.println(e.getMessage());
        }
    }

    /** Sets OpCom (called from main) */
    // public void setOpCom(OpCom opCom) {
    this.opCom=opCom;
    // }

    @Override
    public void run(){

        long duration;
        long t = System.currentTimeMillis();
        startTime = t;
        boolean on=false; 

        while (shouldRun) {
            /** Written by you */
            
            double y; double yref; double u; double yold; //to be caluclated (voltage output)
            

            switch (modeMon.getMode()) { //Byt ut mot nya motsvarigheten till mode
               
                case BEAM: {  

                    synchronized (pi) { // To avoid parameter changes in between compute control signal
                    yprev=y;
                    y=readInput(analogInAngle); 
                    yref=refgen.getRef();
                    u = pi.calculateOutput(y, yref);
                    
                    
                    //if fortdarande ostabilt-->Relä 
                            
                    //hur ska vi veta att vi uppnått limitcycle?
                    //jämföra y och yold, noter teckenbyte och spara ner tiden mellan teckenbyten 
                    //tiden används för att beräkana perioden
                    //Om periodtiden är lika 5 gån
                    

                    if(Math.abs(yref-y)<=hyst) {  
                        newstate=false; 
                    }
                    else if (y>yref) {
                        if ()

                    }


                    }

                    else{
                      u=relayAmp;   
                    }

                    //else: PI- mode ska börja verka 

                    u=limit(u);
                    writeOutput(u);
                    inner.updateState(u);
                    /** Written by you */
                    break;
                }
                }


                case UPPERTANK: { // motsvarar inre reguatorn endast 
                  
                }

                case LOWERTANK: { // motsvarar ball and beam uppgift 3
                        
                }

                default: {
                    System.out.println("Error: Illegal mode.");
                    break;
                }
               
                sendDataToGUI(yref, y, u);

            }

             //sleep
            t = t + inner.getHMillis();
            duration = t - System.currentTimeMillis();
            if(duration > 0) {
                try {
                    sleep(duration);
                } catch (InterruptedException x) {}
            } else {
                System.out.println("Lagging behind...");
            }
        }
        writeOutput(0);
        }

    }

    public void Hagglund(){ //

    }
    

    /** Sets ReferenceGenerator (called from main) */
    public void setRefGen(ReferenceGenerator refGen) {
        this.refGen = refGen;
    }

    // Called in every sample in order to send plot data to OpCom
    private void sendDataToGUI(double yRef, double y, double u) {
        double x = (double) (System.currentTimeMillis() - startTime) / 1000.0;
        opCom.putControlData(x, u);
        opCom.putMeasurementData(x, yRef, y);
    }

    // Sets the controller's parameters
    public void setParameters(PIDParameters p) {

        controller.setParameters(p);
    }

    // Gets the controller's parameters
    public PIDParameters getParameters() {

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

    // Private method for implementing the relay with hysteresis
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

//INIT metod 
