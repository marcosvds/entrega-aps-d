package br.pro.hashi.ensino.desagil.aps.view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ActionListener, MouseListener {
    private final Gate gate;
    private final Light light;
    private final JCheckBox inBox1;
    private final JCheckBox inBox2;
    private final JCheckBox inBox3;
    private final Image image;
    private final int lightx;
    private final int lighty;
    private final int lightr;


    public GateView(Gate gate) {
        super(180, 120);

        this.gate = gate;
        this.light = new Light(255, 0, 0);

        //posição da luz
        lightx = 70;
        lighty = 80;
        lightr = 20;

        inBox1 = new JCheckBox();
        inBox2 = new JCheckBox();
        inBox3 = new JCheckBox();

        // Usamos esse carregamento nos Desafios, vocês lembram?
        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        if (gate.getInputSize() > 2) {
            add(inBox1, 40, 15, 17, 20);
            add(inBox2, 70, 15, 17, 20);
            add(inBox3, 100, 15, 17, 20);
            inBox1.addActionListener(this);
            inBox2.addActionListener(this);
            inBox3.addActionListener(this);

        } else if (gate.getInputSize() > 1) {
            add(inBox1, 55, 15, 17, 20);
            add(inBox2, 85, 15, 17, 20);
            inBox1.addActionListener(this);
            inBox2.addActionListener(this);

        } else {
            add(inBox1, 70, 15, 17, 20);
            inBox1.addActionListener(this);
        }

        addMouseListener(this);

        update();
    }

    private void update() {

        if (gate.getInputSize() > 2) {
            Switch in1 = new Switch();
            Switch in2 = new Switch();
            Switch in3 = new Switch();
            if (inBox1.isSelected()) {
                in1.turnOn();
            }
            if (inBox2.isSelected()) {
                in2.turnOn();
            }
            if (inBox3.isSelected()) {
                in3.turnOn();
            }
            gate.connect(0, in1);
            gate.connect(1, in2);
            gate.connect(2, in3);

        } else if (gate.getInputSize() > 1) {
            Switch in1 = new Switch();
            Switch in3 = new Switch();
            if (inBox1.isSelected()) {
                in1.turnOn();
            }
            if (inBox3.isSelected()) {
                in3.turnOn();
            }
            gate.connect(0, in1);
            gate.connect(1, in3);

        } else {
            Switch in1 = new Switch();
            if (inBox1.isSelected()) {
                in1.turnOn();
            }
            gate.connect(0, in1);
        }

        System.out.println(gate.read());

        //outBox.setEnabled(true);
        light.connect(0, gate);
        //outBox.setEnabled(false);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }

    private boolean clickCircleTrueOrFalse(int x, int y) {
        return Math.pow(x - (lightx + lightr / 2.0), 2) + Math.pow(y - (lighty + lightr / 2.0), 2) <= Math.pow(10, 2);
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        System.out.println("line 87 [debug purpose]: (x,y): (" + x + "," + y + ")");
        System.out.println("line 88 [debug purpose]: inside circle: " + clickCircleTrueOrFalse(x, y));

        // Se o clique foi dentro do circulo colorido/preto...
        if (clickCircleTrueOrFalse(x, y)) {
            /*
             * (x-x0)² + (y-y0)² <= r²
             * ...então abrimos a janela seletora de cor...
             */
//            this.trueColor = JColorChooser.showDialog(this, null, this.color);
            Color oldColor = light.getColor();
            Color newColor = JColorChooser.showDialog(this, null, oldColor);

            if (newColor != null && gate.read()) { /////
                light.setColor(newColor);
            }
            // ...e chamamos repaint para atualizar a tela.
            update();
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de pressionar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de soltar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // entrar no painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // sair do painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void paintComponent(Graphics g) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        //g.drawImage(image, 10, 80, 221, 221, this);

        // Desenha a imagem, passando sua posição e seu tamanho.
        g.drawImage(image, 25, 25, 110, 55, this);

        // Desenha um quadrado cheio.
        g.setColor(light.getColor());
        g.fillOval(lightx, lighty, lightr, lightr);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}