

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {
    static String[] items = {
            "Москва",
            "Санкт-Петербург",
            "Новосибирск",
            "Екатеринбург",
            "Казань",
            "Нижний Новгород",
            "Челябинск",
            "Самара",
            "Омск",
            "Ростов-на-Дону",
            "Уфа",
            "Красноярск",
            "Воронеж",
            "Пермь",
            "Волгоград"
    };

    static String last_item_selected1 = items[0];
    static JComboBox<String> combo_tarif;

    static String last_item_selected2 = items[1];
    static JComboBox<String> combo_tarif2;

    static TextField weight;

    static TextField volume;

    static JLabel labelOutput;

    public static void main(String[] args){
        JFrame main_GUI = new JFrame("Victory");
        main_GUI.setTitle ("Calc_GI");
        main_GUI.setBounds(100,100,1150,600);
        main_GUI.setResizable(false);

        JPanel main_panel = new JPanel();
        main_panel.setLayout(null);
        main_panel.setBackground(new Color(224, 230, 245));
        main_GUI.add(main_panel);

        JLabel label1 = new JLabel("Откуда");
        label1.setBounds(150,210,100,30);
        label1.setFont(new Font("Times New Roman", Font.BOLD, 18));
        main_panel.add(label1);

        JLabel label2 = new JLabel("Куда");
        label2.setBounds(350,210,100,30);
        label2.setFont(new Font("Times New Roman", Font.BOLD, 18));
        main_panel.add(label2);

        JLabel label3 = new JLabel("Вес, кг");
        label3.setBounds(550,210,100,30);
        label3.setFont(new Font("Times New Roman", Font.BOLD, 18));
        main_panel.add(label3);

        JLabel label4 = new JLabel("Объём, м3");
        label4.setBounds(750,210,120,30);
        label4.setFont(new Font("Times New Roman", Font.BOLD, 18));
        main_panel.add(label4);

        JLabel label6 = new JLabel("[Длина Ширина Высота] (через пробел)");
        label6.setBounds(750,270,300,30);
        label6.setFont(new Font("Times New Roman", Font.PLAIN, 14));
        main_panel.add(label6);

        JLabel label5 = new JLabel("ПРОСТОЙ КАЛЬКУЛЯТОР ДЛЯ РАСЧЕТА СТОИМОСТИ ПЕРЕВОЗКИ ГРУЗА");
        label5.setBounds(150,170,900,30);
        label5.setAlignmentX(Component.CENTER_ALIGNMENT);
        label5.setFont(new Font("Times New Roman", Font.PLAIN, 22));
        main_panel.add(label5);


        combo_tarif = new JComboBox<>(items);
        combo_tarif.setSelectedItem(last_item_selected1);
        combo_tarif.setBounds(150,240,150,30);
        combo_tarif.setFont(new Font("Times New Roman", Font.ITALIC, 18));
        ActionListener actionListener = e -> {
            String selected_item1 = (String) combo_tarif.getSelectedItem();
            String selected_item2 = (String) combo_tarif2.getSelectedItem();

            if (selected_item1.equals(selected_item2)){
                JOptionPane.showMessageDialog(null,
                        "Доставка в тот же город не производится",
                        "Неверно указан город!",
                        JOptionPane.WARNING_MESSAGE);

                combo_tarif.setSelectedItem(last_item_selected1);
                combo_tarif2.setSelectedItem(last_item_selected2);
            }
            else{
                last_item_selected1 = selected_item1;
                last_item_selected2 = selected_item2;
            }
        };

        combo_tarif.setBackground(new Color(255, 255, 255));
        combo_tarif.addActionListener(actionListener);
        main_panel.add(combo_tarif);

        //Выпадающий список 2
        {
            combo_tarif2 = new JComboBox<>(items);
            combo_tarif2.setSelectedItem(last_item_selected2);
            combo_tarif2.setBounds(350, 240, 150, 30);
            combo_tarif2.setFont(new Font("Times New Roman", Font.ITALIC, 18));

            combo_tarif2.setBackground(new Color(255, 255, 255));
            combo_tarif2.addActionListener(actionListener);
            main_panel.add(combo_tarif2);
        }

        //Вывод результата
        {
            labelOutput = new JLabel("Результат:");
            labelOutput.setBounds(250, 380, 750, 50);
            labelOutput.setVisible(false);
            labelOutput.setFont(new Font("Times New Roman", Font.ITALIC, 32));
            main_panel.add(labelOutput);
        }

        //Текстовое поле "Вес"
        {
            weight = new TextField("");
            weight.setBounds(550, 240, 150, 30);
            weight.setVisible(true);
            weight.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            main_panel.add(weight);
        }

        //Текстовое поле "Объём"
        {
            volume = new TextField("");
            volume.setBounds(750, 240, 240, 30);
            volume.setVisible(true);
            volume.setFont(new Font("Times New Roman", Font.PLAIN, 16));
            main_panel.add(volume);
        }

        JButton button_create = new JButton("РАССЧИТАТЬ СТОИМОСТЬ");
        button_create.setBounds(350,290,350,50);
        button_create.setBackground(new Color(255, 147, 3));
        button_create.setForeground(Color.white);
        button_create.setFont(new Font("Times New Roman", Font.BOLD, 20));
        ActionListener actionListener3 = e -> {
            String regex ="\\d{1,4}\\s\\d{1,3}\\s\\d{1,3}";
            Pattern pattern = Pattern.compile(regex);
            Matcher matcher = pattern.matcher(volume.getText());
            if (matcher.matches()){
                String[] nums = volume.getText().split(" ");
                int[] edges = {1572, 276, 326};

                float triple = 1;

                for (int i = 0; i < 3; i++){
                    int value = Integer.parseInt(nums[i]);

                    if (value > edges[i] || value == 0) {
                        JOptionPane.showMessageDialog(null,
                                "Длина до 1573 см \n Ширина до 277 см \n Высота до 327 см ",
                                "Неверно введены значения!",
                                JOptionPane.WARNING_MESSAGE);
                    }
                    else{
                        triple*=value;
                    }

                }

                triple/=1000000;
                volume.setText(String.format("%.2f",triple));
                if (triple>140.0){
                    JOptionPane.showMessageDialog(null,
                            "Вагон не вмещает груз",
                            "Неверно введены значения!",
                            JOptionPane.WARNING_MESSAGE);
                }
                else if(triple<1.0 ){
                    JOptionPane.showMessageDialog(null,
                            "Посылки менее 1 кубического метра\nОтправляются по почте",
                            "Неверно введены значения!",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            else{
                JOptionPane.showMessageDialog(null,
                        "Длина до 1573 см \nШирина до 277 см \nВысота до 327 см ",
                        "Неверно введены значения!",
                        JOptionPane.WARNING_MESSAGE);
            }

            Double[] koef = {1.0,1.4,3.0,1.8,1.3,1.2,1.9,1.2,2.5,1.7,1.7,4.0,1.1,1.6,1.8};

            int item_selected1 = Arrays.asList(items).indexOf(last_item_selected1);
            int item_selected2 = Arrays.asList(items).indexOf(last_item_selected2);
            Double Sum=koef[item_selected1]+koef[item_selected2];
            if(Sum>2.0 &&Sum<=4.0){
                Sum/=2;
            }
            else if(Sum>4.0 &&Sum<=5.0){
                Sum/=2.5;
            }
            else if(Sum>5.0) {
                Sum /= 3.5;
            }

            regex ="\\d{1,5}";
            pattern = Pattern.compile(regex);
            matcher = pattern.matcher(weight.getText());
            if (matcher.matches()){
                int value = Integer.parseInt(weight.getText());
                if (value > 68000 || value == 0) {
                    JOptionPane.showMessageDialog(null,
                            "Введите целое число от 1 до 68000 включительно",
                            "Неверно введены значения!",
                            JOptionPane.WARNING_MESSAGE);
                    return;
                }
                if(value>1.0 &&value<=500.0){
                    value*=24;
                }
                else if(value>500.0 &&value<=1000.0){
                    value*=23;
                }
                else if(value>1000.0 &&value<=2000.0){
                    value*=22;
                }
                else if(value>2000.0) {
                    value *= 21;
                }

                value*=Sum;
                labelOutput.setText("Итоговая стоимость: "+ String.valueOf(value) +" рублей");
                labelOutput.setVisible(true);
            }
            else{
                JOptionPane.showMessageDialog(null,
                        "Введите целое число от 1 до 68000 включительно",
                        "Неверно введены значения!",
                        JOptionPane.WARNING_MESSAGE);
            }

        };
        button_create.addActionListener(actionListener3);
        main_panel.add(button_create);

        main_GUI.setVisible(true);
        main_GUI.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}
