import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import javax.swing.*;

public class WeatherApp
{
    private static final String API_KEY = "929ddf52683ccc4de24540640085d1c4";
    private static final String BASE_URL = "http://api.openweathermap.org/data/2.5/weather";

    public static void main(String[] args) 
    {
        JFrame frame = createFrame();
        JTextField cityField = new JTextField(15);
        JLabel iconLabel = new JLabel(); // Label for the weather icon
        JLabel temperatureLabel = createLabel(48);
        JLabel descriptionLabel = createLabel(20);
        JLabel detailsLabel = createLabel(16);

        JPanel panel = createInputPanel(cityField);
        frame.add(panel, BorderLayout.NORTH);
        frame.add(createResultPanel(iconLabel, temperatureLabel, descriptionLabel, detailsLabel), BorderLayout.CENTER);

        JButton searchButton = (JButton) panel.getComponent(2);
        searchButton.addActionListener(e -> fetchWeather(cityField.getText(), iconLabel, temperatureLabel, descriptionLabel, detailsLabel));

        frame.setVisible(true);
    }
    private static JFrame createFrame() 
    {
        JFrame frame = new JFrame("Weather Application");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 400);
        frame.setLayout(new BorderLayout());
        frame.getContentPane().setBackground(new Color(173, 216, 230));
        return frame;
    }
    private static JPanel createInputPanel(JTextField cityField) 
    {
        JPanel panel = new JPanel(new FlowLayout());
        panel.setOpaque(false);
        panel.add(new JLabel("Enter City:"));
        cityField.setPreferredSize(new Dimension(200, 30));
        panel.add(cityField);
        panel.add(new JButton("Get Weather"));
        return panel;
    }
    private static JPanel createResultPanel(JLabel iconLabel, JLabel... labels) 
    {
        JPanel resultPanel = new JPanel();
        resultPanel.setLayout(new BoxLayout(resultPanel, BoxLayout.Y_AXIS));
        resultPanel.setOpaque(false);
        iconLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        resultPanel.add(iconLabel);
        for (JLabel label : labels) resultPanel.add(label);
        return resultPanel;
    }
    private static JLabel createLabel(int fontSize) 
    {
        JLabel label = new JLabel("", SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, fontSize));
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        return label;
    }
    private static void fetchWeather(String city, JLabel iconLabel, JLabel tempLabel, JLabel descLabel, JLabel detailLabel) 
    {
        if (city.isEmpty()) 
        {
            tempLabel.setText("");
            descLabel.setText("");
            detailLabel.setText("City name cannot be empty.");
            iconLabel.setIcon(null);
            return;
        }
        String data = getWeatherData(city);
        if (data != null) parseWeatherData(data, iconLabel, tempLabel, descLabel, detailLabel);
        else {
            tempLabel.setText("");
            descLabel.setText("");
            detailLabel.setText("Could not retrieve data.");
            iconLabel.setIcon(null);
        }
    }
    private static String getWeatherData(String city) 
    {
        try 
        {
            URL url = new URL(BASE_URL + "?q=" + city + "&appid=" + API_KEY + "&units=metric");
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) response.append(line);
            in.close();
            return response.toString();
        } catch (Exception e) 
        {
            return null;
        }
    }
    private static void parseWeatherData(String data, JLabel iconLabel, JLabel tempLabel, JLabel descLabel, JLabel detailLabel) {
        try 
        {
            String temp = String.format("%.2f\u00B0C", Double.parseDouble(data.split("\"temp\":")[1].split(",")[0]));
            String desc = data.split("\"description\":\"")[1].split("\"")[0];
            String humidity = data.split("\"humidity\":")[1].split(",")[0] + "%";
            String wind = data.split("\"speed\":")[1].split(",")[0] + " m/s";

            // Set temperature and descriptions
            tempLabel.setText(temp);
            descLabel.setText(desc.substring(0, 1).toUpperCase() + desc.substring(1));
            detailLabel.setText("Humidity: " + humidity + " | Wind Speed: " + wind);

            // Set weather icon
            String iconCode = data.split("\"icon\":\"")[1].split("\"")[0];
            ImageIcon icon = new ImageIcon(new URL("http://openweathermap.org/img/wn/" + iconCode + "@2x.png"));
            iconLabel.setIcon(icon);
        } 
        catch (Exception e) 
        {
            tempLabel.setText("");
            descLabel.setText("");
            detailLabel.setText("Error parsing data.");
            iconLabel.setIcon(null);
        }
    }
}
