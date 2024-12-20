import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:shared_preferences/shared_preferences.dart';
import 'api_service.dart';

class FirebaseService {
  static void initialize() {
    FirebaseMessaging.onMessage.listen((RemoteMessage message) {
      print('Message received: ${message.notification?.title}, ${message.notification?.body}');
    });

    FirebaseMessaging.onMessageOpenedApp.listen((RemoteMessage message) {
      print('Message opened: ${message.notification?.title}');
    });

    FirebaseMessaging.onBackgroundMessage(_firebaseBackgroundHandler);
  }

  static Future<void> _firebaseBackgroundHandler(RemoteMessage message) async {
    print('Background message received: ${message.notification?.title}');
  }

  static void sendTokenToBackend(String token) {
    ApiService.saveToken(token);
  }


  static void _getToken() async {
    String? token = await FirebaseMessaging.instance.getToken();
    print('FCM Token: $token');

    // Store the token locally
    if (token != null) {
      SharedPreferences prefs = await SharedPreferences.getInstance();
      await prefs.setString('fcmToken', token);
      print('Token stored locally');
    }

    // Send token to backend
    FirebaseService.sendTokenToBackend(token!);
  }

  // To retrieve the token later
  static Future<String?> _retrieveToken() async {
    SharedPreferences prefs = await SharedPreferences.getInstance();
    return prefs.getString('fcmToken');
  }
}
