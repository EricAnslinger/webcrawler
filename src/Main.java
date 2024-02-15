import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

public class Main {
    public static void main(String[] args) {

        URL getUrl = getURLInput(); //Website Input with Window (Call method getURLInput())

        if (getUrl != null) {  //getUrl should contain something to start crawling
            crawl(getUrl);  //Start crawling
        } else {
            System.exit(0);
        }
    }

    public static URL getURLInput() {
        URL userURL = null;

        while (userURL == null) {
            try {
                // UserInput with JOptionPane Window
                String getUserInput = JOptionPane.showInputDialog(null, "Please enter a URL!");

                if(getUserInput == null){ //Exit programm, if getUserInput is null
                    System.exit(0);
                }

                userURL = new URL(getUserInput); // set userURL to common URL
            } catch (MalformedURLException e) {
                // No valid URL? Then programm asks again
                JOptionPane.showMessageDialog(null, "Please enter a valid URL", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        return userURL; //return userURL to getURL
    }
    public static void crawl(URL url) {
        try {
            String urlStr = url.toString(); //URL Object to String
            Document doc = Jsoup.connect(urlStr).get(); //Connect Website with Jsoup

            if(doc != null){
                ArrayList<String> linksList = new ArrayList<>();
                for(Element link : doc.select("a[href]")){ //Find all Link Elements , safe found link in link
                    String links = link.absUrl("href"); //extract links, if relative url it will automatically create absolute
                    //String links = link.absUrl("href"); Choose this, if you only want absolute links from the website
                    linksList.add(links);
                }
                StringBuilder formattedLinks = new StringBuilder();
                for (String link : linksList) {                            //Add links to Stringbuilder for Output
                    formattedLinks.append(link).append("\n");
                }

                JFrame frame = new JFrame("Links from " + urlStr); //Create JFrame
                JTextArea textArea = new JTextArea(formattedLinks.toString()); //StringBuilder Output
                textArea.setFont(new Font("Monospaced", Font.PLAIN, 13));
                JScrollPane scrollPane = new JScrollPane(textArea); //Scrolling within the JFRame

                // Settings JFRAME
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.getContentPane().add(scrollPane);
                frame.setSize(800, 600);  // Size
                frame.setLocationRelativeTo(null); //JFRAME in middle of screen
                frame.setVisible(true);
            }
            // Catch when Error
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }

}