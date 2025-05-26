![Project Logo](/src/main/resources/static/sportradar_logo.png)

# Sportradar Wordle Game

**Sportradar Wordle Game** is a Spring Boot command-line version of the popular word puzzle game "Wordle", implemented in Java using Spring Boot. The objective is simple: guess the secret 5-letter word within a limited number of attempts.

---

## Features

- Play Wordle in the terminal with a randomly selected 5-letter word.
- User input validation and feedback after each guess.
- Color-coded feedback for each letter (GREEN, YELLOW, GRAY).
- Configurable word list via a text file.
- Comprehensive test coverage with JUnit.

---

## Technologies Used

- **Java:** Version 21
- **Spring Boot:** Version 3.5.0
- **JUnit:** For unit and integration testing
- **Gradle:** Build and dependency management tool
- **IntelliJ IDEA:** Recommended IDE

---

## Getting Started

### Prerequisites

- Java 21 installed
- Gradle installed (or use the Gradle wrapper)
- IntelliJ IDEA (or any Java IDE)

### Installation
1. **Clone the Repository:**
```bash
git clone https://github.com/sahan89/SportradarWordleGame.git
```

2. **Navigate to the Project Directory using CMD or Terminal:**
```bash 
cd SportradarWordleGame 
```
   
3. **Build the Project:**
```bash 
./gradlew clean build
```

4. **Run the jar file:**
```bash
java -jar build/libs/SportradarWordleGame.jar
```

## Outputs
**Guess the Correct word**
![Project Logo](/src/main/resources/static/guess_word_01.png)

**Guess the incorrect word**
![Project Logo](/src/main/resources/static/guess_word_02.png)

