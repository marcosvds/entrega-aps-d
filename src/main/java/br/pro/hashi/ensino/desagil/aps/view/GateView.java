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

    private final Image image;
    //private final JCheckBox outBox;


    private final int lightx;
    private final int lighty;
    private final int lightr;


    public GateView(Gate gate) {
        super(180, 120);

        this.gate = gate;
        this.light = new Light(255, 0, 0);

        int h = 2;

        //posição da luz
        lightx = 142;
        lighty = 50;
        lightr = 25;

        inBox1 = new JCheckBox();
        inBox2 = new JCheckBox();
        //outBox = new JCheckBox();

        //JLabel inLabel = new JLabel("Entrada:");
        //JLabel outLabel = new JLabel("Saida:");

        //add(inLabel, 8, h, 75, 25);
        //add(inBox1, 10, h+30, 17, 20);
        //add(outLabel, 8, h+62, 25, 23);
        //add(outBox, 8, h+82, 75, 25);


        // Usamos esse carregamento nos Desafios, vocês lembram?
        String name = gate.toString() + ".jpg";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);


        if (gate.getInputSize() > 1) {
            add(inBox1, 14, h + 32, 17, 20);
            add(inBox2, 14, h + 68, 17, 20);
            inBox2.addActionListener(this);

        } else {
            add(inBox1, 14, h + 50, 17, 20);
        }


        //outBox.setEnabled(false);
        inBox1.addActionListener(this);


        addMouseListener(this);

        update();
    }

    private void update() {
        System.out.println("updatou");

        Switch in1 = new Switch();

        if (inBox1.isSelected()) {
            in1.turnOn();
        }

        gate.connect(0, in1);

        if (gate.getInputSize() > 1) {
            Switch in2 = new Switch();
            if (inBox2.isSelected()) {
                in2.turnOn();
            }
            gate.connect(1, in2);
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

    @Override
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        int dist = Math.abs(x - (lightx + 12)) + Math.abs(y - (lighty + 12));
        System.out.println(dist);
        System.out.println("r" + lightr);


        // Se o clique foi dentro do circulo colorido...
        if (dist <= 16) {

            // ...então abrimos a janela seletora de cor...
            light.setColor(JColorChooser.showDialog(this, null, Color.RED));

            // ...e chamamos repaint para atualizar a tela.
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
        g.drawImage(image, 0, 0, 180, 160, this);

        // Desenha um quadrado cheio.
        g.setColor(light.getColor());
        g.fillOval(lightx, lighty, lightr, lightr);

        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}
