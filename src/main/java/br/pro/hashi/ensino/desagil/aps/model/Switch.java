package br.pro.hashi.ensino.desagil.aps.model;

public class Switch implements Emitter {
    private boolean signal;

    public Switch() {
        signal = false;
    }

    public void turnOn() {
        signal = true;
    }

    @SuppressWarnings("unused")
    public void turnOff() {
        signal = false;
    }

    @Override
    public boolean read() {
        return signal;
    }
}