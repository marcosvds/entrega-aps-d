package br.pro.hashi.ensino.desagil.aps.model;

public class OrGate extends Gate {

    private final NandGate nandA;
    private final NandGate nandB;
    private final NandGate nandC;

    public OrGate() {
        super("OR", 2);

        nandA = new NandGate();
        nandB = new NandGate();
        nandC = new NandGate();

    }

    @Override
    public boolean read() {
        return nandC.read();
    }

    @Override
    public void connect(int inputIndex, Emitter emitter) {
        if (inputIndex < 0 || inputIndex > 1) {
            throw new IndexOutOfBoundsException(inputIndex);
        }

        if (inputIndex == 0) {
            nandA.connect(0, emitter);
            nandA.connect(1, emitter);
        }

        if (inputIndex == 1) {
            nandB.connect(0, emitter);
            nandB.connect(1, emitter);
        }

        nandC.connect(0, nandA);
        nandC.connect(1, nandB);

    }
}