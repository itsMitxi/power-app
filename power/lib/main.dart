import 'package:flutter/material.dart';
import 'package:power/screens/auth_screen.dart';

void main() {
  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Flutter Demo',
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
      home: const AuthScreen(),
      // routes: {
      //   '/register': (context) => RegisterScreen(),
      //   '/login': (context) => LoginScreen(),
      // },
    );
  }
}