import java.awt.Cursor;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.*;

import org.json.simple.JSONObject;

public class WeatherAppGUI extends JFrame{
	private JSONObject weatherData;
	public WeatherAppGUI() {
		super("Weather App");
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setSize(450, 650);
		
		setLocationRelativeTo(null);
		
		setLayout(null);
		
		setResizable(false);
		
		addGuiComponents();
	}
	
	private void addGuiComponents() {
		
		//arama çubuğu
		JTextField searchTextField = new JTextField();
		searchTextField.setBounds(15, 15, 351, 45);
		searchTextField.setFont(new Font("Dialog", Font.PLAIN, 24));
		add(searchTextField);
		 
		//hava durumu fotoğrafı ilk açıldığında çıkan
		JLabel weatherConditionImage = new JLabel(loadImage("assets/cloudy.png"));
		weatherConditionImage.setBounds(0, 125, 450, 217);
		add(weatherConditionImage);
		
		//sıcaklık için text
		JLabel tempatureText = new JLabel("10°C");
		tempatureText.setBounds(0, 350, 450, 54);
		tempatureText.setFont(new Font("Dialog", Font.BOLD, 48));
		tempatureText.setHorizontalAlignment(SwingConstants.CENTER);
		add(tempatureText);
		
		//hava durumu açıklaması
		JLabel weatherConditionDesc = new JLabel("Cloudy");
		weatherConditionDesc.setBounds(0, 405, 450, 36);
		weatherConditionDesc.setFont(new Font("Dialog", Font.PLAIN, 32));
		weatherConditionDesc.setHorizontalAlignment(SwingConstants.CENTER);
		add(weatherConditionDesc);
		
		//nem oranı için resim
		JLabel humidityImage = new JLabel(loadImage("assets/humidity.png"));
		humidityImage.setBounds(15, 500, 74, 66);
		add(humidityImage);
		
		//nem oranı için text
		JLabel humidityText = new JLabel("<html><b>Humidity</b> 100% </html>");
		humidityText.setBounds(90, 500, 85, 55);
		humidityText.setFont(new Font("Dialog", Font.PLAIN, 16));
		//tempatureText.setHorizontalAlignment(SwingConstants.CENTER);
		add(humidityText);
		
		//rüzgar hızı için resim
		JLabel windspeedImage = new JLabel(loadImage("assets/windspeed.png"));
		windspeedImage.setBounds(200, 500, 74, 66);
		add(windspeedImage);
		
		//rüzgar hızı için text
		JLabel windspeedText = new JLabel("<html><b>Windspeed</b> 15km/h </html>");
		windspeedText.setBounds(310, 500, 85, 55);
		windspeedText.setFont(new Font("Dialog", Font.PLAIN, 16));
		//tempatureText.setHorizontalAlignment(SwingConstants.CENTER);
		add(windspeedText);
		
		//arama butonu
		JButton searchButton = new JButton(loadImage("assets/search.png"));
		searchButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		searchButton.setBounds(375, 13, 47, 45);
		searchButton.addActionListener(new ActionListener() {
			@Override
            public void actionPerformed(ActionEvent e) {
                // get location from user
                String userInput = searchTextField.getText();

                // validate input - remove whitespace to ensure non-empty text
                if(userInput.replaceAll("\\s", "").length() <= 0){
                    return;
                }

                // retrieve weather data
                weatherData = WeatherApp.getWeatherData(userInput);

                // update gui

                // update weather image
                String weatherCondition = (String) weatherData.get("weather_condition");

                // depending on the condition, we will update the weather image that corresponds with the condition
                switch(weatherCondition){
                    case "Clear":
                        weatherConditionImage.setIcon(loadImage("assets/clear.png"));
                        break;
                    case "Cloudy":
                        weatherConditionImage.setIcon(loadImage("assets/cloudy.png"));
                        break;
                    case "Rain":
                        weatherConditionImage.setIcon(loadImage("assets/rain.png"));
                        break;
                    case "Snow":
                        weatherConditionImage.setIcon(loadImage("assets/snow.pngImage"));
                        break;
                }

                // update temperature text
                double temperature = (double) weatherData.get("temperature");
                tempatureText.setText(temperature + " C");

                // update weather condition text
                weatherConditionDesc.setText(weatherCondition);

                // update humidity text
                long humidity = (long) weatherData.get("humidity");
                humidityText.setText("<html><b>Humidity</b> " + humidity + "%</html>");

                // update windspeed text
                double windspeed = (double) weatherData.get("windspeed");
                windspeedText.setText("<html><b>Windspeed</b> " + windspeed + "km/h</html>");
            }
		});
		add(searchButton);
	}
	
	//butona fotoğraf eklemek için dönen fonksiyon
	private ImageIcon loadImage (String resourcePath) {
		try {
			BufferedImage image = ImageIO.read(new File(resourcePath));
			return new ImageIcon(image);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println("Resim bulunamadı.");
		return null;
	}
}
