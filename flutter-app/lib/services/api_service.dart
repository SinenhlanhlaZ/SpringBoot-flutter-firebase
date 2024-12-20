import 'package:http/http.dart' as http;

class ApiService {
  static const String backendUrl = 'http://localhost:9099/api/users/save-token';

  static Future<void> saveToken(String token) async {
    try {
      final response = await http.post(
        Uri.parse(backendUrl),
        body: {'token': token},
      );

      if (response.statusCode == 200) {
        print('Token sent to backend successfully.');
      } else {
        print('Failed to send token: ${response.statusCode}');
      }
    } catch (e) {
      print('Error sending token to backend: $e');
    }
  }
}
