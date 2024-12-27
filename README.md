# PANDAGPT-Application

PANDAGPT is an AI-powered chat application integrated with OpenAI's GPT-3, offering real-time conversations through a simple and intuitive interface. The app uses OkHttp for API calls to OpenAI, allowing for seamless communication.

## Key Features
- Real-time GPT-3 interactions.
- User-friendly interface for easy chat.
- Chat history view for previous messages.
- OpenAI API integration for dynamic text responses.

## Setup & Usage
1. Clone or download the repository.
2. Open the project in Android Studio.
3. Replace `YOUR_API_KEY` in the `callAPI()` method with your OpenAI API key.
4. Build and run the application on your Android device or emulator.

## File Structure
- **MainActivity.java**: Handles chat functionality and API calls.
- **Message.java**: Data model for chat messages.
- **MessageAdapter.java**: RecyclerView adapter for displaying messages.
- **activity_main.xml**: Layout for the main activity.

## How It Works
1. User sends a message.
2. The app sends the message to OpenAI GPT-3 via an API call.
3. GPT-3’s response is displayed along with the user's message.

## Requirements
- Obtain an OpenAI API key.
- Replace the key in the `callAPI()` function.

## Disclaimer
This project is for educational use. Ensure proper handling of API keys and sensitive data.

## Resources
- [OpenAI API Documentation](https://beta.openai.com)
- [OkHttp Library](https://square.github.io/okhttp/)
