# csv-parser

A simple Java application for parsing product data from CSV files with logging and data processing capabilities.

## Features

- **CSV Parsing**: Parse product data from CSV files
- **Command-line arguments**: Process command-line arguments
- **Argument Filtering**: Filter products based on price and top N
- **Data Processing**: Process and manipulate product information
- **Result Logging**: Logging system for collecting result
- **Unit Testing**: Test coverage with JUnit 5

## Project Structure

```
├── README.md                             # Project documentation
├── pom.xml                               # Maven configuration
└── src
    ├── main
    │   └── java
    │       └── parser
    │           ├── ProductViewer.java    # Entry point
    │           ├── model
    │           │   ├── FilterArgs.java   # Command-line filter arguments model
    │           │   └── Product.java      # Product data model
    │           └── util
    │               ├── FileLogger.java   # Logging utility
    │               ├── ParseArgs.java    # Argument parser
    │               ├── ParseCsv.java     # CSV parser
    │               └── Processor.java    # Data processing logic
    ├── sample.csv                        # Sample CSV data file
    └── test
        └── java
            └── parser
                ├── ParseArgsTest.java     # Args Parser tests
                ├── ParseCsvTest.java      # CSV Parser tests
                ├── ProcessorTest.java     # Processor tests
                └── SetupTest.java         # Base configuration
```

## Requirements

- **Java**: 21 or higher
- **Maven**: 3.6+ (for building)

## Dependencies

- **JUnit Jupiter 5.13.4**: Testing framework
- **Jackson Databind 2.19.2**: JSON/CSV data binding and processing

## Getting Started

### Prerequisites

Ensure you have Java 21 and Maven installed on your system:

```bash
java --version
mvn --version
```

### Building the Project

1. Clone or download the project
2. Navigate to the project directory
3. Build the project using Maven:

```bash
mvn clean compile
```

### Running the Application

Execute the main application with command-line arguments (minPrice and topN):
```bash
mvn exec:java -Dexec.mainClass="parser.ProductViewer" -Dexec.args="100 5"
```

### Running Tests

Execute the test suite:

```bash
mvn test
```

## Usage

### Sample Data

The project includes a `sample.csv` file with example product data. You can use this file to test the application functionality.

### Sample Input/Output

#### Input (sample.csv)
```csv
id,name,price,category
1,Laptop,999.99,Electronics
2,Knife,12.50,Kitchen
3,Pad,45.99,Electronics
4,Notebook,5.99,Office
```

#### Output
```
===========All products that are in stock and cost more than 50.00===========
Laptop - 1500.00 - Electronics - In stock: true
Chair - 80.00 - Furniture - In stock: true
Monitor - 220.00 - Electronics - In stock: true
======================Number of products per category=====================
Electronics: 3
Furniture: 1
Home: 1
========Sort by price and print the top 5 most expensive products==========
Laptop - 1500.00 - Electronics - In stock: true
Monitor - 220.00 - Electronics - In stock: true
Chair - 80.00 - Furniture - In stock: true
Keyboard - 50.00 - Electronics - In stock: true
Coffee Mug - 12.00 - Home - In stock: false
============================================================
Processing complete. You may also see the logs for details.
```

### CSV Format

The application expects CSV files with product information. Ensure your CSV files follow the expected format as defined in the `Product.java` model.

### Logging

The application uses `FileLogger.java` to log operations and errors. Log files will be created in the project directory during execution.

## Troubleshooting

### Common Issues

- **Java Version**: Ensure you're using Java 21 or higher
- **CSV Format**: Verify your CSV files match the expected format
- **Build failing**: Perform `mvn clean compile`

### Debug Mode

For additional debugging information, you can modify the logging level in `FileLogger.java` or run with additional Maven debug flags:

```bash
mvn test -X
```
