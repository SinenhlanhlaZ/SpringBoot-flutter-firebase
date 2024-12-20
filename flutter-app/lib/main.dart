import 'package:firebase_core/firebase_core.dart';
import 'package:firebase_messaging/firebase_messaging.dart';
import 'package:flutter/material.dart';
import 'package:flutter_app/firebase_options.dart';
import 'package:flutter_app/services/firebase_service.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  print('initializing firebase...');
  await Firebase.initializeApp(
    options: DefaultFirebaseOptions.currentPlatform,
  );
  print('Firebase initialized successfully');
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.deepPurple),
        useMaterial3: true,
      ),
      home: const MyHomePage(title: 'Flutter Firebase Testing'),
    );
  }
}

class MyHomePage extends StatefulWidget {
  const MyHomePage({super.key, required this.title});


  final String title;

  @override
  State<MyHomePage> createState() => _MyHomePageState();
}

class _MyHomePageState extends State<MyHomePage> {
  int _counter = 0;
  String? _token;

  void _incrementCounter() {
    _getToken();
    setState(() {
      _counter++;
    });
  }

  @override
  void initState() {
    FirebaseService.initialize();
    requestPermissions();
    _getToken();
    super.initState();
  }

  void _getToken() async {
    try {
      String? token = await FirebaseMessaging.instance.getToken();
      print('FCM Token: $token');
    } catch (e) {
      print('Error fetching FCM token -> : $e');
    }
    print('FCM Token: $_token');
    // Send token to backend
    FirebaseService.sendTokenToBackend(_token!);
  }

  void requestPermissions() async {
    FirebaseMessaging messaging = FirebaseMessaging.instance;

    NotificationSettings settings = await messaging.requestPermission(
      alert: true,
      badge: true,
      sound: true,
    );

    print('Notification permission status: ${settings.authorizationStatus}');
  }

  // void checkToken() async {
  //   String? token = await _retrieveToken();
  //   if (token != null) {
  //     print('Retrieved token from local storage: $token');
  //   } else {
  //     print('No token found in local storage.');
  //   }
  // }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        backgroundColor: Theme.of(context).colorScheme.inversePrimary,
        title: Text(widget.title),
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: <Widget>[
            const Text(
              'You have pushed the button this many times:',
            ),
            Text(
              '$_counter',
              style: Theme.of(context).textTheme.headlineMedium,
            ),
            Text(
              _token != null ? 'FCM Token: $_token' : 'Fetching FCM Token...',
              textAlign: TextAlign.center,
            ),
          ],
        ),
      ),
      floatingActionButton: FloatingActionButton(
        onPressed: _incrementCounter,
        tooltip: 'Increment',
        child: const Icon(Icons.add),
      ), // This trailing comma makes auto-formatting nicer for build methods.
    );
  }
}
