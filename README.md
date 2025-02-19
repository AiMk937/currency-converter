Real-Time Currency Converter 💱
![Java](https://img.shields.io/badge/Java-17+-blue.svg?style=flat&logo=java) ![Maven](https://img.shields.io/badge/Maven-3+-blue.svg?style=flat&logo=apache-maven) ![License](https://img.shields.io/badge/license-MIT-green)

A simple **Java Swing** application that uses the **APILayer Exchange Rates Data API** to convert currencies in real time. This project is built with **Maven** and uses **org.json** for JSON parsing.
![Currency Converter Screenshot](./screenshots/currency-converter.png)
Features 🌟
- **Real-Time Conversion** – Fetches the latest exchange rates from APILayer.
- **Swing UI** – Provides a graphical interface for selecting source and target currencies.
- **Robust JSON Parsing** – Uses `org.json` to parse the API response.
Prerequisites 📋
- **Java 17+**
   Check with: `java -version`
- **Maven 3+**
   Check with: `mvn -version`
- **APILayer API Key**
   Sign up at [APILayer](https://apilayer.com/) to get your free/paid API key.
Getting Started 🚀
```bash
# Clone the repository
git clone https://github.com/your-username/currency-converter.git
cd currency-converter

# Build the project
mvn clean compile

# Run the application
mvn exec:java -Dexec.mainClass="com.example.CurrencyConverter"
```
A Swing window will open. Enter an amount, select the “From” and “To” currencies, then click **Convert**.
Project Structure 📂
```
currency-converter/
├── pom.xml                      # Maven configuration
└── src/
    └── main/
        └── java/
            └── com/
                └── example/
                    └── CurrencyConverter.java
```
Troubleshooting 🔧
- **No Response or “Error retrieving conversion rate”**
  - Check the console logs for “HTTP Response Code” and “API Response.”
  - A `524` error usually indicates a timeout.
  - A `401` or `403` error might indicate an invalid API key or insufficient plan.

- **ClassNotFoundException**
  - Ensure your package name and directory structure match (`com.example.CurrencyConverter` under `src/main/java/com/example/`).
  - Verify Maven dependencies are downloaded (run `mvn clean compile` again).
License 📄
This project is distributed under the MIT License - see the [LICENSE](LICENSE) file for details.
Contributing 🤝
1. Fork this repository.
2. Create a feature branch (`git checkout -b feature/new-feature`).
3. Commit your changes (`git commit -am 'Add some feature'`).
4. Push to the branch (`git push origin feature/new-feature`).
5. Create a new Pull Request.
