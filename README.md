Real-Time Currency Converter
A simple **Java Swing** application that uses the **APILayer Exchange Rates Data API** to convert currencies in real time. This project is built with **Maven** and uses **org.json** for JSON parsing.

![Currency Converter Screenshot](./screenshots/currency-converter.png)

Features
- **Real-Time Conversion** – Fetches the latest exchange rates from [APILayer](https://apilayer.com/marketplace/exchangerates_data-api).
- **Swing UI** – Provides a graphical interface for selecting source and target currencies.
- **Robust JSON Parsing** – Uses `org.json` to parse the API response.
Prerequisites
1. **Java 17+**
   - Check with `java -version`
2. **Maven 3+**
   - Check with `mvn -version`
3. **APILayer API Key**
   - Sign up at [APILayer](https://apilayer.com/) to get your free/paid API key.
Getting Started
1. **Clone the Repository**
   ```bash
   git clone https://github.com/your-username/currency-converter.git
   cd currency-converter
   ```
2. **Configure Your API Key**
   - The key is currently hardcoded in `CurrencyConverter.java` in the `API_KEY` field.
   - If you prefer to **keep the key private**, you can load it from an environment variable or an external config file.
3. **Build the Project**
   ```bash
   mvn clean compile
   ```
   This will download dependencies (including `org.json`) and compile the code.
4. **Run the Application**
   ```bash
   mvn exec:java -Dexec.mainClass="com.example.CurrencyConverter"
   ```
   A Swing window will open. Enter an amount, select the “From” and “To” currencies, then click **Convert**.
Project Structure
```css
currency-converter
├── pom.xml                      # Maven configuration
└── src
    └── main
        └── java
            └── com
                └── example
                    └── CurrencyConverter.java
```
- **`pom.xml`** – Manages dependencies and build plugins (including `org.json` and the Maven Exec Plugin).
- **`CurrencyConverter.java`** – The main Swing application.
Troubleshooting
- **No Response or “Error retrieving conversion rate”**
  - Check the console logs for “HTTP Response Code” and “API Response.”
  - A `524` error usually indicates a timeout.
  - A `401` or `403` error might indicate an invalid API key or insufficient plan.
- **ClassNotFoundException**
  - Ensure your package name and directory structure match (`com.example.CurrencyConverter` under `src/main/java/com/example/`).
  - Verify Maven dependencies are downloaded (run `mvn clean compile` again).
License
This project is distributed under the **MIT License**. See the [LICENSE](./LICENSE) file for details.
Contributing
1. Fork this repository.
2. Create a feature branch (`git checkout -b feature/new-feature`).
3. Commit your changes (`git commit -m 'Add new feature'`).
4. Push to the branch (`git push origin feature/new-feature`).
5. Create a new Pull Request on GitHub.
