import com.opencsv.CSVReader;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class XCSPlot extends JPanel  implements ActionListener, ItemListener {

    private static final long serialVersionUID = 1L;
    private TextField xt, yt, einst, zweit;
    private List liste;
    private Checkbox cbr, cbl;

    public static void main(String[] args){

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //window.setBounds(30, 30, 1000, 1000);
        //window.getContentPane().add(new XCSPlot());
        //window.setVisible(true);


        List myEntries = null;





        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("population.csv"), ';','\0', 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String [] nextLine;
        try {
            while ((nextLine = reader.readNext()) != null) {
                // nextLine[] is an array of values from the line

                float float1 = 0;
                float float2 = 0;
                float float3 = 0;
                float float4 = 0;

                int color_r = 0;
                int color_g = 0;
                int color_b = 0;

                double prediction = 0;

                double prediction_error = 0;

                double fitness = 0;

                int numerosity = 0;

                int experience = 0;

                String whole = "";

                // BOX BOUNDS
                // ---------------------------------------
                whole = nextLine[0];

                String re1=".*?";	// Non-greedy match on filler
                String re2="([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";	// Float 1
                String re3=".*?";	// Non-greedy match on filler
                String re4="([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";	// Float 2
                String re5=".*?";	// Non-greedy match on filler
                String re6="([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";	// Float 3
                String re7=".*?";	// Non-greedy match on filler
                String re8="([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";	// Float 4

                Pattern p = Pattern.compile(re1+re2+re3+re4+re5+re6+re7+re8,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                Matcher m = p.matcher(whole);
                if (m.find())
                {
                    float1 = Float.valueOf(m.group(1));
                    float2 = Float.valueOf(m.group(2));
                    float3 = Float.valueOf(m.group(3));
                    float4 = Float.valueOf(m.group(4));
                }

                // COLORS
                // ---------------------------------------
                whole = nextLine[1];

                re1 = ".*?";	// Non-greedy match on filler
                re2 = "(\\d+)";	// Integer Number 1
                re3 = ".*?";	// Non-greedy match on filler
                re4 = "(\\d+)";	// Integer Number 2
                re5 = ".*?";	// Non-greedy match on filler
                re6 = "(\\d+)";	// Integer Number 3

                p = Pattern.compile(re1+re2+re3+re4+re5+re6,Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                m = p.matcher(whole);
                if (m.find())
                {
                    color_r = Integer.valueOf(m.group(1));
                    color_g = Integer.valueOf(m.group(2));
                    color_b = Integer.valueOf(m.group(3));
                }

                // PREDICTION
                // ---------------------------------------
                prediction = Double.valueOf(nextLine[2]);

                // PREDICTION ERROR
                // ---------------------------------------
                prediction_error = Double.valueOf(nextLine[3]);

                // FITNESS
                // ---------------------------------------
                fitness = Double.valueOf(nextLine[4]);

                // NUMEROSITY
                // ---------------------------------------
                numerosity = Integer.valueOf(nextLine[5]);

                // EXPERIENCE
                // ---------------------------------------
                experience = Integer.valueOf(nextLine[6]);


                System.out.println("Prediction:" + prediction);
                System.out.println("Prediction error:" + prediction_error);
                System.out.println("Fitness:" + fitness);
                System.out.println("Numerosity:" + numerosity);
                System.out.println("Experience:" + experience + "\n");

            }
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void paint(Graphics g) {

        //g.setColor(new Color(229,178,146));
        //g.drawRect(20, 20, 20, 20);



    }


    @Override
    public void actionPerformed(ActionEvent e) {

        System.out.println("Clicked");
        if (e.getActionCommand().equals("hinzufuegen")) {
            try {


                xt.setText("");
                yt.setText("");
                einst.setText("");
                zweit.setText("");
                aktualisieren();
            }
            catch (NumberFormatException nfe){
            }


        } else {
            }
        }


    @Override
    public void itemStateChanged(ItemEvent e) {
        xt.getText();
        yt.getText();
        einst.getText();
        zweit.getText();
        xt.setText("");
        yt.setText("");
        einst.setText("");
        zweit.setText("");
        if (e.getSource().equals(cbl))
            zweit.setEnabled(true);
        else
            zweit.setEnabled(false);
    }

    public void aktualisieren() {
    }
}
