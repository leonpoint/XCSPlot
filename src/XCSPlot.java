import com.opencsv.CSVReader;

import javax.swing.*;
import java.awt.*;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;


public class XCSPlot extends JPanel {

    private static int window_size = 1000;

    public static void main(String[] args){

        JFrame window = new JFrame();
        window.setDefaultCloseOperation(EXIT_ON_CLOSE);
        window.setBounds(30, 30, window_size, window_size);
        window.getContentPane().add(new XCSPlot());
        window.setVisible(true);

    }


    public void paint(Graphics g) {



        CSVReader reader = null;
        try {
            reader = new CSVReader(new FileReader("population.csv"), ';','\0', 1);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        String [] nextLine;
        try {
            assert reader != null;
            while ((nextLine = reader.readNext()) != null) {

                float float1;
                float float2;
                float float3;
                float float4;

                // bounds
                //  x_lower, x_upper, y_lower, y_upper
                int x_l = 0, x_u = 0, y_l = 0, y_u = 0;

                int color_r = 0;
                int color_g = 0;
                int color_b = 0;

                double prediction;
                double prediction_error;
                double fitness;
                int numerosity ;
                int experience;

                String whole;



                // START EXTRACTING DATA
                // ---------------------------------------

                // BOX BOUNDS
                // ---------------------------------------
                whole = nextLine[0];

                String re1 = ".*?";    // Non-greedy match on filler
                String re2 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";    // Float 1
                String re3 = ".*?";    // Non-greedy match on filler
                String re4 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";    // Float 2
                String re5 = ".*?";    // Non-greedy match on filler
                String re6 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";    // Float 3
                String re7 = ".*?";    // Non-greedy match on filler
                String re8 = "([+-]?\\d*\\.\\d+)(?![-+0-9\\.])";    // Float 4

                Pattern p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6 + re7 + re8, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                Matcher m = p.matcher(whole);
                if (m.find()) {
                    float1 = Float.valueOf(m.group(1));
                    float2 = Float.valueOf(m.group(2));
                    float3 = Float.valueOf(m.group(3));
                    float4 = Float.valueOf(m.group(4));

                    // normalize to window
                    // --------------------------------------
                    y_l = (int) (float1 * window_size);
                    y_u = (int) (float2 * window_size);
                    x_l = (int) (float3 * window_size);
                    x_u = (int) (float4 * window_size);


                    // flip values if lower bound is bigger than upper bound
                    if (x_l > x_u) {
                        int temp = x_l;
                        x_l = x_u;
                        x_u = temp;
                    }
                    if (y_l > y_u) {
                        int temp = y_l;
                        y_l = y_u;
                        y_u = temp;
                    }

                    System.out.print(x_l + "," + x_u + " ");
                    System.out.println(y_l + "," + y_u);
                }

                    // COLORS
                    // ---------------------------------------
                    whole = nextLine[1];

                    re1 = ".*?";    // Non-greedy match on filler
                    re2 = "(\\d+)";    // Integer Number 1
                    re3 = ".*?";    // Non-greedy match on filler
                    re4 = "(\\d+)";    // Integer Number 2
                    re5 = ".*?";    // Non-greedy match on filler
                    re6 = "(\\d+)";    // Integer Number 3

                    p = Pattern.compile(re1 + re2 + re3 + re4 + re5 + re6, Pattern.CASE_INSENSITIVE | Pattern.DOTALL);
                    m = p.matcher(whole);
                    if (m.find()) {
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


                    // TODO: filter the plotting
                    if(prediction > 100 && prediction_error < 100 && fitness > 0.08 && experience > 100) {

                        g.setColor(new Color(color_r, color_g, color_b));
                        g.drawRect(x_l, y_l, x_u - x_l, y_u - y_l);
                        // MARIO
                    }
                }
            } catch(IOException e){
                e.printStackTrace();
            }
    }
}
