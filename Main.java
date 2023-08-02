import java.io.*;
import java.util.*;
import java.time.*;
import java.net.HttpURLConnection;
import java.net.URL;
import com.google.gson.Gson;
// import com.google.gson.Gson;
// import com.github.cliftonlabs.json_simple.*;



class Main {
    public static void main(String[] args) {
        
    System.out.println("Welcome to Hourly weather forecast program, please input");
    Scanner input = new Scanner(System.in);
    int userInput;
    String dateFormatted;
    String apiUrl = "https://samples.openweathermap.org/data/2.5/forecast/hourly?q=London,us&appid=b6907d289e10d714a6e88b30761fae22"; // Replace with the API endpoint URL
    int flag=0;
    do{
        System.out.println("Enter 1 for to get Weather");
        System.out.println("Enter 2 for to get Wind speed");
        System.out.println("Enter 3 for to get Pressure");
        System.out.println("Enter 0 for Exit");
        userInput= input.nextInt();
        input.nextLine();
        if(userInput!=0)
        {
            System.out.println("Enter date as formatted string (2019-03-27 20:00:00)");
            dateFormatted = input.nextLine();
            flag=0;
            // LocalDate date = LocalDate.now();
            try {
                URL url = new URL(apiUrl);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                int responseCode = connection.getResponseCode();
                // System.out.println("Response Code: " + responseCode);

                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();

                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    
                    Gson gson = new Gson();
                    WeatherData w =  gson.fromJson(response.toString(), WeatherData.class);
                    
                    for (WeatherDateBasedData element : w.list){
                        if(element.dt_txt.equals(dateFormatted)){
                            flag=1;
                            switch(userInput){
                                case 1:
                                    System.out.println("Temp. at the given date is "+element.main.temp);
                                break;
                                case 2:
                                    System.out.println("Wind speed at the given date is "+element.wind.speed);
                                break;
                                case 3:
                                    System.out.println("pressure at the given date is "+element.main.pressure);
                                break;
                            }
                        }

                    }
                    if(flag==0) {
                        System.out.println("Date not found, please enter valid date");
                    }
                } else {
                    System.out.println("GET request failed.");
                }

                connection.disconnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }while(userInput!=0);
        
        
    }
}



class WeatherData {
    public String cod;
    public float message;
    public int cnt;
    public WeatherDateBasedData[] list;
    public CityBean city;
    
    
}
class WeatherDateBasedData {
    public int dt;
    public String dt_txt;
    public MainWeatherDateBasedData main;
    public WindDateBasedData wind;
    
}
class MainWeatherDateBasedData {
    public float temp;
    public float temp_min;
    public float temp_max;
    public float pressure;
    public float sea_level;
    public float grnd_level;
    public float humidity;
    public float temp_kf;
}

class WindDateBasedData {
    public float speed;
    public float deg;
}
class CoordsBean {
    public float lat;
    public float lon;
}

class CityBean {

    public int id;
    public String name;
    public CoordsBean coord;
    public String country;
    public String population;
}

