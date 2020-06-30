package br.pro.hashi.ensino.desagil.aps.model;

public class XorGate extends Gate {

    private final NandGate nandA;
    private final NandGate nandB;
    private final NandGate nandC;
    private final NandGate nandD;
    private final NandGate nandE;

    public XorGate() {
        super("XOR", 2);

        nandA = new NandGate();
        nandB = new NandGate();
        nandC = new NandGate();
        nandD = new NandGate();
        nandE = new NandGate();

        nandC.connect(1, nandA);
        nandD.connect(1, nandB);

        nandE.connect(0, nandC);
        nandE.connect(1, nandD);
    }

    @Override
    public boolean read() {
        return nandE.read();
    }

    @Override
    public void connect(int inputIndex, Emitter emitter) {
        if (inputIndex < 0 || inputIndex > 1) {
            throw new IndexOutOfBoundsException(inputIndex);
        }

        if (inputIndex == 0) {
            nandA.connect(0, emitter);
            nandB.connect(1, emitter);
            nandC.connect(0, emitter);
        }

        if (inputIndex == 1) {
            nandB.connect(0, emitter);
            nandA.connect(1, emitter);
            nandD.connect(0, emitter);
        }
    }
}